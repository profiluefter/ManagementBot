package util.sql;

import util.Strings;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.*;

//TODO: Documentation
public class Database {
	private static Connection sql;

	static ResultSet loadUser(long discordid) {
		if(sql == null) {
			throw new RuntimeException("SQL not connected");
		}
		try {
			PreparedStatement statement = sql.prepareStatement("SELECT * FROM users where 'discord-id'=?");
			statement.setLong(1, discordid);
			return statement.executeQuery();
		}catch(SQLException e) {
			throw new RuntimeException(e);
		}
	}

	static void saveUser(User user) {
		if(sql == null) {
			throw new RuntimeException("SQL not connected");
		}
		try {
			PreparedStatement statement = sql.prepareStatement("INSERT OR REPLACE INTO users ('discord-id', language) VALUES (?,?)");
			statement.setLong(1,user.getDiscordid());
			statement.setString(2, Strings.parseLang(user.getLanguage()));
			int affectedRows = statement.executeUpdate();
			LoggerFactory.getLogger(Database.class).info("Successfully saved user with id " + user.getDiscordid() + "! Affected " + affectedRows + " rows!");
		}catch(SQLException e) {
			throw new RuntimeException(e);
		}
	}

	public static void init() {
		try {
			sql = DriverManager.getConnection("jdbc:sqlite:db.sqlite");
			runDefaultSQL();
		}catch(SQLException | URISyntaxException | IOException e) {
			throw new RuntimeException(e);
		}
	}

	private static void runDefaultSQL() throws URISyntaxException, IOException, SQLException {
		if(sql == null) {
			throw new RuntimeException("SQL not connected");
		}
		String sqliteCommand = String.join("\n",
				Files.readAllLines(Paths.get(Database.class.getResource("/sqlite-schema.sql").toURI()))
		);

		LoggerFactory.getLogger(Database.class).info("Running default SQL...");
		Statement statement = sql.createStatement();
		statement.execute(sqliteCommand);
		LoggerFactory.getLogger(Database.class).info("Successfully ran default SQL!");
	}
}
