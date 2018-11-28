package config;

import util.Strings;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.*;

public class Database {
	private static Connection sql;

	/**
	 * Loads userdata from the database
	 * @param discordId The discord-id of the requested user
	 * @return The userdata as a result set
	 */
	static ResultSet loadUser(long discordId) {
		if(sql == null) {
			throw new RuntimeException("SQL not connected");
		}
		try {
			PreparedStatement statement = sql.prepareStatement("SELECT * FROM users where 'discord-id'=?");
			statement.setLong(1, discordId);
			return statement.executeQuery();
		}catch(SQLException e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * Saves userdata to the database
	 * @param user The User object to save
	 */
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

	/**
	 * Initializes the connection to the database and ensures that the required data schema is created
	 */
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
