package sql;

import localisation.Strings;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.*;

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
			PreparedStatement statement = sql.prepareStatement("UPDATE users SET language=? WHERE 'discord-id'=?");
			statement.setString(1, Strings.parseLang(user.getLanguage()));
			statement.setLong(2,user.getDiscordid());
			statement.executeUpdate();
		}catch(SQLException e) {
			throw new RuntimeException(e);
		}
	}

	public static void init() {
		User.registerHook();
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
		String sqliteCommand = String.join("\n", Files.readAllLines(Paths.get(Database.class.getResource("/sqlite" +
				"-schema.sql").toURI())));

		Statement statement = sql.createStatement();
		statement.execute(sqliteCommand);

	}
}
