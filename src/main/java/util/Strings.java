package util;

import java.io.IOException;
import java.util.Properties;

public class Strings {
	private static Properties enLocale = new Properties();
	private static Properties deLocale = new Properties();

	/**
	 * Loads the strings from the files
	 */
	public static void init() {
		try {
			enLocale.load(Strings.class.getResourceAsStream("/strings_en.properties"));
			deLocale.load(Strings.class.getResourceAsStream("/strings_de.properties"));
		}catch(IOException e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * @param key      The key of the String to return
	 * @param language The language of the desired String
	 * @return The requested String
	 */
	public static String getString(String key, Lang language) {
		return (language == Lang.EN ? enLocale : deLocale).getProperty(key);
	}

	public static String parseLang(Lang lang) {
		switch(lang) {
			case EN:
				return "EN";
			case DE:
				return "DE";
			default:
				throw new IllegalStateException();
		}
	}

	public static Lang parseLang(String string) {
		switch(string) {
			case "EN":
				return Lang.EN;
			case "DE":
				return Lang.DE;
			default:
				return null;
		}
	}

	public enum Lang {
		EN, DE
	}
}
