package sql;

import localisation.Strings;

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

	public static User loadUser(long discordid) {
		if(loadedUsers.containsKey(discordid)) {
			return loadedUsers.get(discordid);
		} else {
			try {
				ResultSet set = Database.loadUser(discordid);
				User user = new User(discordid, Strings.parseLang(set.getString("language")));
				loadedUsers.put(discordid, user);
				return user;
			}catch(SQLException e) {
				throw new RuntimeException(e);
			}
		}
	}

	static void registerHook() {
		Runtime.getRuntime().addShutdownHook(new Thread(User::shutdown));
	}

	private static void shutdown() {
		loadedUsers.forEach((aLong, user) -> Database.saveUser(user));
	}

	public long getDiscordid() {
		return discordid;
	}

	public User setDiscordid(long discordid) {
		this.discordid = discordid;
		return this;
	}

	public Strings.Lang getLanguage() {
		return language;
	}

	public User setLanguage(Strings.Lang language) {
		this.language = language;
		return this;
	}
}
