package config;

import org.slf4j.LoggerFactory;
import util.SQLScriptRunner;
import util.Strings;

import java.io.InputStreamReader;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

//TODO: Only change what is needed (permissions)
public class Database {
	private static Connection sql;

	/**
	 * Loads userdata from the database
	 *
	 * @param discordID The discord-id of the requested user
	 * @return The userdata as a result set
	 */
	static ResultSet loadUser(long discordID) throws SQLException {
		if (sql == null) {
			throw new RuntimeException("SQL not connected");
		}

		PreparedStatement preparedStatement = sql.prepareStatement("SELECT * FROM users WHERE discordID=?");
		preparedStatement.setLong(1, discordID);
		return preparedStatement.executeQuery();
	}

	static List<String> loadPermissions(long discordID) throws SQLException {
		if (sql == null) {
			throw new RuntimeException("SQL not connected");
		}

		PreparedStatement preparedStatement = sql.prepareStatement("SELECT * FROM permissions WHERE discordID=?");
		preparedStatement.setLong(1, discordID);
		ResultSet resultSet = preparedStatement.executeQuery();
		List<String> permissions = new ArrayList<>();

		while (resultSet.next())
			permissions.add(resultSet.getString(2));

		return permissions;
	}

	static void removePermission(long discordID, String permission) throws SQLException {
		if (sql == null) {
			throw new RuntimeException("SQL not connected");
		}

		PreparedStatement preparedStatement = sql.prepareStatement("DELETE FROM permissions WHERE discordID=? AND permission=?");
		preparedStatement.setLong(1, discordID);
		preparedStatement.setString(2, permission);
		preparedStatement.executeUpdate();
	}

	/**
	 * Saves userdata to the database
	 *
	 * @param user The User object to save
	 */
	static void saveUser(User user) throws SQLException {
		if (sql == null) {
			throw new RuntimeException("SQL not connected");
		}

		PreparedStatement userStatement = sql.prepareStatement("INSERT OR REPLACE INTO users (discordID, language) VALUES (?,?)");
		userStatement.setLong(1, user.getDiscordID());
		userStatement.setString(2, Strings.parseLang(user.getLanguage()));
		int affectedRows = userStatement.executeUpdate();

		PreparedStatement permissionStatement = sql.prepareStatement("INSERT OR REPLACE INTO permissions (discordID, permission) VALUES (?,?)");
		permissionStatement.setLong(1, user.getDiscordID());
		for (String permission : user.getPermissions()) {
			permissionStatement.setString(2, permission);
			affectedRows += permissionStatement.executeUpdate();
		}

		sql.commit();
		LoggerFactory.getLogger(Database.class).info("Successfully saved user with id " + user.getDiscordID() + "! Affected " + affectedRows + " rows!");
	}

	static void deleteUser(User user) throws SQLException {
		if (sql == null) {
			throw new RuntimeException("SQL not connected");
		}

		Statement userStatement = sql.createStatement();
		int affectedRows = userStatement.executeUpdate("DELETE FROM users WHERE discordID=" + user.getDiscordID() + ";");

		Statement permissionStatement = sql.createStatement();
		affectedRows += permissionStatement.executeUpdate("DELETE FROM permissions WHERE discordID=" + user.getDiscordID() + ";");

		sql.commit();
		LoggerFactory.getLogger(Database.class).info("Successfully deleted " + affectedRows + " entries from user " + user.getDiscordID() + "!");
	}

	/**
	 * Initializes the connection to the database and ensures that the required data schema is created
	 */
	public static void init() {
		try {
			sql = DriverManager.getConnection("jdbc:sqlite:db.sqlite");
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
		runDefaultSQL();
	}

	/**
	 * Transmits all changes and disconnects.
	 */
	public static void cleanUp() {
		try {
			sql.commit();
			sql.close();
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
		sql = null;
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

	static void addPermission(long discordID, String permission) throws SQLException {
		if (sql == null) {
			throw new RuntimeException("SQL not connected");
		}
		PreparedStatement statement = sql.prepareStatement("INSERT OR REPLACE INTO permissions (discordID, permission) VALUES (?,?)");
		statement.setLong(1, discordID);
		statement.setString(2, permission);
		statement.executeUpdate();
	}

	static void setLanguage(long discordID, String lang) throws SQLException {
		if (sql == null) {
			throw new RuntimeException("SQL not connected");
		}
		PreparedStatement statement = sql.prepareStatement("UPDATE users SET language=? WHERE discordID=?");
		statement.setLong(1,discordID);
		statement.setString(2,lang);
		statement.executeUpdate();
	}
}
