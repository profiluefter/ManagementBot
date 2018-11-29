package config;

import util.Strings;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
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
				ResultSet set = Database.loadUser(discordID);
				List<String> permissions = new ArrayList<>();
				User user = new User(
						discordID,
						Strings.parseLang(set.getFetchSize() == 1 ? set.getString("language") : "EN"),
						permissions
				);
				loadedUsers.put(discordID, user);
				return user;
			} catch (SQLException e) {
				throw new RuntimeException(e);
			}
		}
	}

	/**
	 * @return The discord-id of the user.
	 */
	public long getDiscordId() {
		return discordID;
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