package localisation;

import java.io.IOException;
import java.util.Properties;

public class Strings {
    private static Properties enLocale = new Properties();
    private static Properties deLocale = new Properties();

    public static void init() {
        try {
            enLocale.load(Strings.class.getResourceAsStream("../strings_en.properties"));
            deLocale.load(Strings.class.getResourceAsStream("../strings_de.properties"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static String getString(String key, Lang language) {
        return (language == Lang.EN ? enLocale : deLocale).getProperty(key);
    }

    public enum Lang {
        EN,
        DE
    }
}
