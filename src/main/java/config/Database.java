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
	 *
	 * @param discordID The discord-id of the requested user
	 * @return The userdata as a result set
	 */
	static ResultSet loadUser(long discordID) {
		if (sql == null) {
			throw new RuntimeException("SQL not connected");
		}
		try {
			return sql.createStatement().executeQuery("SELECT * FROM users WHERE discordID=" + discordID);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	static ResultSet loadPermissions(long discordID) {
		if (sql == null) {
			throw new RuntimeException("SQL not connected");
		}
		try {
			return sql.createStatement().executeQuery("SELECT * FROM permissions WHERE discordID=" + discordID);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * Saves userdata to the database
	 *
	 * @param user The User object to save
	 */
	static void saveUser(User user) {
		if (sql == null) {
			throw new RuntimeException("SQL not connected");
		}
		try {
			Statement userStatement = sql.createStatement();
			int affectedRows = userStatement.executeUpdate("INSERT OR REPLACE INTO users (discordID, language) VALUES (" + user.getDiscordId() + ",'" + Strings.parseLang(user.getLanguage()) + "')");

			Statement permissionStatement = sql.createStatement();
			for (String permission : user.getPermissions()) {
				affectedRows += permissionStatement.executeUpdate("INSERT OR REPLACE INTO permissions (discordID, permission) VALUES (" + user.getDiscordId() + ",'" + permission + "')");
			}

			sql.commit();
			LoggerFactory.getLogger(Database.class).info("Successfully saved user with id " + user.getDiscordId() + "! Affected " + affectedRows + " rows!");
		} catch (SQLException e) {
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
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	private static void runDefaultSQL() {
		if (sql == null) {
			throw new RuntimeException("SQL not connected");
		}
		LoggerFactory.getLogger(Database.class).info("Running default SQL...");
		SQLScriptRunner runner = new SQLScriptRunner(sql);
		runner.runScript(new InputStreamReader(Database.class.getResourceAsStream("/sqlite-schema.sql")));
		LoggerFactory.getLogger(Database.class).info("Successfully ran default SQL!");
	}
}
