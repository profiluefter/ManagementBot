package config;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

public class Config {
	private static Properties properties;
	private static final File configFile = new File("config.properties");

	/**
	 * Loads the default configuration from the default-config file an copies it to the real configuration file.
	 */
	public static void init() {
		Properties defaultConfig = new Properties();
		try {
			defaultConfig.load(Config.class.getResourceAsStream("/default-config.properties"));
		} catch(IOException e) {
			throw new RuntimeException(e);
		}
		properties = new Properties(defaultConfig);
		properties.putAll(defaultConfig);
		load();
		save();
	}

	/**
	 * Saves the properties and resets the variables so that {@link Config#init()} can be called again.
	 */
	@SuppressWarnings("WeakerAccess") //API method
	public static void cleanUp() {
		save();
		properties = null;
	}

	/**
	 * Reads a value from the configuration file.
	 *
	 * @param key The key of the requested value.
	 * @return The value of the entry.
	 */
	public static String get(String key) {
		return properties.getProperty(key);
	}

	/**
	 * Sets a value of the config file. This does not change the default config.
	 *
	 * @param key   The key that the entry is saved as.
	 * @param value The value that the entry should be set to.
	 */
	@SuppressWarnings("WeakerAccess") //API method
	public static void set(String key, String value) {
		properties.setProperty(key, value);
		save();
	}

	/**
	 * Removes an value from the config file. If an value from the default configuration is deleted, it will be replaced after the next restart.
	 *
	 * @param key The key that should be deleted.
	 */
	@SuppressWarnings("WeakerAccess") //API method
	public static void remove(String key) {
		properties.remove(key);
		save();
	}

	private static void save() {
		try {
			properties.store(new FileOutputStream(configFile), "Config file for the ManagementBot");
		} catch(IOException e) {
			throw new RuntimeException(e);
		}
	}

	private static void load() {
		try {
			//noinspection ResultOfMethodCallIgnored
			configFile.createNewFile();
			properties.load(new FileInputStream(configFile));
		} catch(IOException e) {
			throw new RuntimeException(e);
		}
	}
}
