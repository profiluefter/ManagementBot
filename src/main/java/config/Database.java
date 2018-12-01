package config;

import org.slf4j.LoggerFactory;
import util.SQLScriptRunner;
import util.Strings;

import java.io.InputStreamReader;
import java.sql.*;

public class Database {
	private static Connection sql;

	/**
	 * Loads userdata from the database
	 * @param discordID The discord-id of the requested user
	 * @return The userdata as a result set
	 */
	static ResultSet loadUser(long discordID) {
		if(sql == null) {
			throw new RuntimeException("SQL not connected");
		}
		try {
			PreparedStatement statement = sql.prepareStatement("SELECT * FROM users where 'discord-id'=?");
			statement.setLong(1, discordID);
			return statement.executeQuery();
		}catch(SQLException e) {
			throw new RuntimeException(e);
		}
	}

	static ResultSet loadPermissions(long discordID) {
		if(sql == null) {
			throw new RuntimeException("SQL not connected");
		}
		try {
			PreparedStatement statement = sql.prepareStatement("SELECT permission FROM permissions WHERE 'discord-id'=?");
			statement.setLong(1,discordID);
			return statement.executeQuery();
		} catch (SQLException e) {
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
			PreparedStatement userStatement = sql.prepareStatement("INSERT OR REPLACE INTO users ('discord-id', language) VALUES (?,?)");
			userStatement.setLong(1,user.getDiscordId());
			userStatement.setString(2, Strings.parseLang(user.getLanguage()));
			int affectedRows = userStatement.executeUpdate();

			PreparedStatement permissionStatement = sql.prepareStatement("INSERT INTO permissions ('discord-id', permission) VALUES (?,?)");
			for (String permission : user.getPermissions()) {
				permissionStatement.setLong(1,user.getDiscordId());
				permissionStatement.setString(2,permission);
				affectedRows += permissionStatement.executeUpdate();
			}
			LoggerFactory.getLogger(Database.class).info("Successfully saved user with id " + user.getDiscordId() + "! Affected " + affectedRows + " rows!");
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
		}catch(SQLException e) {
			throw new RuntimeException(e);
		}
	}

	private static void runDefaultSQL() {
		if(sql == null) {
			throw new RuntimeException("SQL not connected");
		}
		LoggerFactory.getLogger(Database.class).info("Running default SQL...");
		SQLScriptRunner runner = new SQLScriptRunner(sql);
		runner.runScript(new InputStreamReader(Database.class.getResourceAsStream("/sqlite-schema.sql")));
		LoggerFactory.getLogger(Database.class).info("Successfully ran default SQL!");
	}
}
