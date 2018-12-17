package util;

import config.User;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.Properties;

public class Strings {
	private static Properties enLocale = new Properties();
	private static Properties deLocale = new Properties();

	/**
	 * Loads the strings from the files
	 */
	public static void init() {
		try {
			enLocale.load(new InputStreamReader(Strings.class.getResourceAsStream("/strings_en.properties"), Charset.forName("UTF-8")));
			deLocale.load(new InputStreamReader(Strings.class.getResourceAsStream("/strings_de.properties"), Charset.forName("UTF-8")));
		} catch(IOException e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * Reads an entry of the strings file.
	 *
	 * @param key      The key of the string to return.
	 * @param language The language of the desired string.
	 * @return The requested string.
	 */
	private static String getString(String key, Lang language) {
		return (language == Lang.EN ? enLocale : deLocale).getProperty(key);
	}

	/**
	 * Reads an entry of the strings file.
	 *
	 * @param key  The key of the string to return.
	 * @param user The user to read the desired language from.
	 * @return The requested string.
	 */
	public static String getString(String key, @NotNull User user) {
		return getString(key, user.getLanguage());
	}

	/**
	 * Reads an entry of the strings file.
	 *
	 * @param key    The key of the string to return.
	 * @param userId The userID of the user to read the language from.
	 * @return The requested string.
	 */
	public static String getString(String key, long userId) {
		return getString(key, User.loadUser(userId));
	}

	/**
	 * Reads an entry of the strings file.
	 *
	 * @param key   The key of the string to return.
	 * @param event The event to read the user and the preferred language of the user.
	 * @return The requested string.
	 */
	public static String getString(String key, @NotNull MessageReceivedEvent event) {
		return getString(key, event.getAuthor().getIdLong());
	}

	/**
	 * Converts the enum {@link Lang} to a string.
	 *
	 * @param lang The language.
	 * @return The two letter code of that language.
	 */
	@NotNull
	@Contract(pure = true)
	public static String parseLang(@NotNull Lang lang) {
		switch(lang) {
			case EN:
				return "EN";
			case DE:
				return "DE";
			default:
				throw new IllegalStateException();
		}
	}

	/**
	 * Converts a string to an enum of type {@link Lang}.
	 *
	 * @param string The two letter code of that language.
	 * @return The corresponding enum entry or <code>null</code> if the language was not found.
	 */
	@Nullable
	@Contract(pure = true)
	public static Lang parseLang(@NotNull String string) {
		switch(string) {
			case "EN":
				return Lang.EN;
			case "DE":
				return Lang.DE;
			default:
				return null;
		}
	}

	/**
	 * An enum with all supported languages.
	 */
	public enum Lang {
		/**
		 * English
		 */
		EN,
		/**
		 * German
		 */
		DE
	}
}
