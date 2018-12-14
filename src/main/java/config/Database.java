package config;

import org.slf4j.LoggerFactory;
import util.SQLScriptRunner;
import util.Strings;

import java.io.InputStreamReader;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("Duplicates")
public class Database {
	private static Connection sql;
	private static PreparedStatement loadUserStatement;
	private static PreparedStatement loadPermissionsStatement;
	private static PreparedStatement removePermissionStatement;
	private static PreparedStatement saveUserStatement;
	private static PreparedStatement savePermissionsStatement;
	private static PreparedStatement addPermissionStatement;
	private static PreparedStatement setLanguageStatement;
	private static PreparedStatement deletePermissionsStatement;
	private static PreparedStatement deleteUserStatement;

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

		loadUserStatement.setLong(1, discordID);
		return loadUserStatement.executeQuery();
	}

	static List<String> loadPermissions(long discordID) throws SQLException {
		if (sql == null) {
			throw new RuntimeException("SQL not connected");
		}

		loadPermissionsStatement.setLong(1, discordID);
		ResultSet resultSet = loadPermissionsStatement.executeQuery();
		List<String> permissions = new ArrayList<>();

		while (resultSet.next())
			permissions.add(resultSet.getString(2));

		return permissions;
	}

	static void removePermission(long discordID, String permission) throws SQLException {
		if (sql == null) {
			throw new RuntimeException("SQL not connected");
		}

		removePermissionStatement.setLong(1, discordID);
		removePermissionStatement.setString(2, permission);
		removePermissionStatement.executeUpdate();
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

		saveUserStatement.setLong(1, user.getDiscordID());
		saveUserStatement.setString(2, Strings.parseLang(user.getLanguage()));
		int affectedRows = saveUserStatement.executeUpdate();

		savePermissionsStatement.setLong(1, user.getDiscordID());
		for (String permission : user.getPermissions()) {
			savePermissionsStatement.setString(2, permission);
			affectedRows += savePermissionsStatement.executeUpdate();
		}

		sql.commit();
		LoggerFactory.getLogger(Database.class).info("Successfully saved user with id " + user.getDiscordID() + "! Affected " + affectedRows + " rows!");
	}

	static void deleteUser(User user) throws SQLException {
		if (sql == null) {
			throw new RuntimeException("SQL not connected");
		}

		deleteUserStatement.setLong(1,user.getDiscordID());
		int affectedRows = deleteUserStatement.executeUpdate();

		deletePermissionsStatement.setLong(1,user.getDiscordID());
		affectedRows += deletePermissionsStatement.executeUpdate();

		sql.commit();
		LoggerFactory.getLogger(Database.class).info("Successfully deleted " + affectedRows + " entries from user " + user.getDiscordID() + "!");
	}

	/**
	 * Initializes the connection to the database and ensures that the required data schema is created
	 */
	public static void init() {
		try {
			sql = DriverManager.getConnection("jdbc:sqlite:db.sqlite");
			runDefaultSQL();
			prepareStatements();
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
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

	private static void prepareStatements() throws SQLException {
		loadUserStatement = sql.prepareStatement("SELECT * FROM users WHERE discordID=?");
		loadPermissionsStatement = sql.prepareStatement("SELECT * FROM permissions WHERE discordID=?");
		removePermissionStatement = sql.prepareStatement("DELETE FROM permissions WHERE discordID=? AND permission=?");
		saveUserStatement = sql.prepareStatement("INSERT OR REPLACE INTO users (discordID, language) VALUES (?,?)");
		savePermissionsStatement = sql.prepareStatement("INSERT OR REPLACE INTO permissions (discordID, permission) VALUES (?,?)");
		addPermissionStatement = sql.prepareStatement("INSERT OR REPLACE INTO permissions (discordID, permission) VALUES (?,?)");
		setLanguageStatement = sql.prepareStatement("UPDATE users SET language=? WHERE discordID=?");
		deleteUserStatement = sql.prepareStatement("DELETE FROM users WHERE discordID=?");
		deletePermissionsStatement = sql.prepareStatement("DELETE FROM permissions WHERE discordID=?");
	}

	static void addPermission(long discordID, String permission) throws SQLException {
		if (sql == null) {
			throw new RuntimeException("SQL not connected");
		}
		addPermissionStatement.setLong(1, discordID);
		addPermissionStatement.setString(2, permission);
		addPermissionStatement.executeUpdate();
		sql.commit();
	}

	static void setLanguage(long discordID, String lang) throws SQLException {
		if (sql == null) {
			throw new RuntimeException("SQL not connected");
		}
		LoggerFactory.getLogger(Database.class).info("setLang " + lang);
		setLanguageStatement.setString(1,lang);
		setLanguageStatement.setLong(2,discordID);
		setLanguageStatement.executeUpdate();
		sql.commit();
	}
}
