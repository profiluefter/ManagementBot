package util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

public class Configuration {
	private static Properties properties;
	private static File configFile = new File("config.properties");

	public static void init() {
		Properties defaultConfig = new Properties();
		try {
			defaultConfig.load(Configuration.class.getResourceAsStream("/default-config.properties"));
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
		properties = new Properties(defaultConfig);
		properties.putAll(defaultConfig);
		load();
		save();
	}

	public static String get(String key) {
		return properties.getProperty(key);
	}

	public static void set(String key, String value) {
		properties.setProperty(key, value);
		save();
	}

	private static void save() {
		try {
			properties.store(new FileOutputStream(configFile),"Config file for the ManagementBot");
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	private static void load() {
		try {
			//noinspection ResultOfMethodCallIgnored
			configFile.createNewFile();
			properties.load(new FileInputStream(configFile));
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}
}
