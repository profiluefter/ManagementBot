package config;

import util.Strings;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;

public class User {
	private static HashMap<Long,User> loadedUsers = new HashMap<>();

	private long discordid;
	private Strings.Lang language;

	private User(long discordid, Strings.Lang language) {
		this.discordid = discordid;
		this.language = language;
	}

	/**
	 * Loads userdata or returns the cached version of it.
	 * @param discordid The discord-id of the requested user.
	 * @return The requested user.
	 */
	public static User loadUser(long discordid) {
		if(loadedUsers.containsKey(discordid)) {
			return loadedUsers.get(discordid);
		} else {
			try {
				ResultSet set = Database.loadUser(discordid);
				User user = new User(discordid, Strings.parseLang(set.getFetchSize() == 1 ? set.getString("language") : "EN"));
				loadedUsers.put(discordid, user);
				return user;
			}catch(SQLException e) {
				throw new RuntimeException(e);
			}
		}
	}

	/**
	 * @return The discord-id of the user.
	 */
	public long getDiscordid() {
		return discordid;
	}

	/**
	 * @return The language preferred by the user. Can be converted from/to String with util.Strings.parseLang()
	 */
	public Strings.Lang getLanguage() {
		return language;
	}

	/**
	 * Sets the language of the user and saves it in the database
	 * @param language The preferred language
	 * @return The object this method was called on. Usefull for chaining
	 */
	public User setLanguage(Strings.Lang language) {
		this.language = language;
		Database.saveUser(this);
		return this;
	}
}
