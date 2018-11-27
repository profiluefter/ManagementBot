package util.sql;

import util.Strings;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;

//TODO: Documentation
public class User {
	private static HashMap<Long,User> loadedUsers = new HashMap<>();

	private long discordid;
	private Strings.Lang language;

	private User(long discordid, Strings.Lang language) {
		this.discordid = discordid;
		this.language = language;
	}

	@Override
	public String toString() {
		return "ID=" + discordid + " Lang="+language;
	}

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

	public long getDiscordid() {
		return discordid;
	}

	public Strings.Lang getLanguage() {
		return language;
	}

	public User setLanguage(Strings.Lang language) {
		this.language = language;
		Database.saveUser(this);
		return this;
	}
}
