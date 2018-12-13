package config;

import org.slf4j.LoggerFactory;
import util.SQLScriptRunner;
import util.Strings;

import java.io.InputStreamReader;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

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
			PreparedStatement preparedStatement = sql.prepareStatement("SELECT * FROM users WHERE discordID=?");
			preparedStatement.setLong(1, discordID);
			return preparedStatement.executeQuery();
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	static List<String> loadPermissions(long discordID) {
		if (sql == null) {
			throw new RuntimeException("SQL not connected");
		}
		try {
			PreparedStatement preparedStatement = sql.prepareStatement("SELECT * FROM permissions WHERE discordID=?");
			preparedStatement.setLong(1, discordID);
			ResultSet resultSet = preparedStatement.executeQuery();
			List<String> permissions = new ArrayList<>();

			while (resultSet.next())
				permissions.add(resultSet.getString(2));

			return permissions;
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
	
	static void removePermission(long discordID, String permission) {
		if (sql == null) {
			throw new RuntimeException("SQL not connected");
		}
		try {
		    PreparedStatement preparedStatement = sql.prepareStatement("DELETE FROM permissions WHERE discordID=? AND permission=?");
		    
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
			PreparedStatement userStatement = sql.prepareStatement("INSERT OR REPLACE INTO users (discordID, language) VALUES (?,?)");
			userStatement.setLong(1, user.getDiscordId());
			userStatement.setString(2, Strings.parseLang(user.getLanguage()));
			int affectedRows = userStatement.executeUpdate();

			//TODO: Remove old permissions
			PreparedStatement permissionStatement = sql.prepareStatement("INSERT OR REPLACE INTO permissions (discordID, permission) VALUES (?,?)");
			permissionStatement.setLong(1, user.getDiscordId());
			for (String permission : user.getPermissions()) {
				permissionStatement.setString(2, permission);
				affectedRows += permissionStatement.executeUpdate();
			}

			sql.commit();
			LoggerFactory.getLogger(Database.class).info("Successfully saved user with id " + user.getDiscordId() + "! Affected " + affectedRows + " rows!");
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	static void deleteUser(User user) {
		if (sql == null) {
			throw new RuntimeException("SQL not connected");
		}
		try {
			Statement userStatement = sql.createStatement();
			int affectedRows = userStatement.executeUpdate("DELETE FROM users WHERE discordID=" + user.getDiscordId() + ";");

			Statement permissionStatement = sql.createStatement();
			affectedRows += permissionStatement.executeUpdate("DELETE FROM permissions WHERE discordID=" + user.getDiscordId() + ";");

			sql.commit();
			LoggerFactory.getLogger(Database.class).info("Successfully deleted " + affectedRows + " entries from user " + user.getDiscordId() + "!");
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

	public static void cleanUp() {
		try {
			sql.commit();
			sql.close();
			sql = null;
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
