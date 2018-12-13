package config;

import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import org.slf4j.LoggerFactory;
import util.Strings;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class User {
	private static Map<Long, User> loadedUsers = new HashMap<>();

	private long discordID;
	private Strings.Lang language;
	private List<String> permissions;

	private User(long discordID, Strings.Lang language, List<String> permissions) {
		this.discordID = discordID;
		this.language = language;
		this.permissions = permissions;
	}

	/**
	 * Loads userdata or returns the cached version of it.
	 *
	 * @param discordID The discord-id of the requested user.
	 * @return The requested user.
	 */
	public static User loadUser(long discordID) {
		if (loadedUsers.containsKey(discordID)) {
			return loadedUsers.get(discordID);
		} else {
			try {
				LoggerFactory.getLogger(User.class).info("Loading user " + discordID + "...");

				ResultSet userSet = Database.loadUser(discordID);
				List<String> permissions = Database.loadPermissions(discordID);

				User user = new User(
						discordID,
						userSet.isClosed() ? Strings.Lang.EN : Strings.parseLang(userSet.getString("language")),
						permissions
				);
				loadedUsers.put(discordID, user);
				LoggerFactory.getLogger(User.class).info("Loaded user " + discordID + "!");
				return user;
			} catch (SQLException e) {
				throw new RuntimeException(e);
			}
		}
	}

	public static User loadUser(MessageReceivedEvent event) {
		return loadUser(event.getAuthor().getIdLong());
	}

	public static void deleteUser(User user) {
		loadedUsers.remove(user.getDiscordID());
		Database.deleteUser(user);
	}

	/**
	 * @return The discord-id of the user.
	 */
	public long getDiscordID() {
		return discordID;
	}

	public List<String> getPermissions() {
		return permissions;
	}

	public void removePermission(String permission) {
		if (permissions.contains(permission)) {
			permissions.remove(permission);
			Database.removePermission(getDiscordID(), permission);
		}

	}

	public void addPermission(String permission) {
		if (!permissions.contains(permission)) {
			permissions.add(permission);
			Database.saveUser(this);
		}
	}

	/**
	 * @return The language preferred by the user. Can be converted from/to String with util.Strings.parseLang()
	 */
	public Strings.Lang getLanguage() {
		return language;
	}

	/**
	 * Sets the language of the user and saves it in the database
	 *
	 * @param language The preferred language
	 */
	public void setLanguage(Strings.Lang language) {
		this.language = language;
		Database.saveUser(this);
	}
}
