package utilities;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class ConfigReader {
	
	private static Properties properties = new Properties();

    static {
        try (InputStream input = ConfigReader.class.getClassLoader()
                .getResourceAsStream("configuration.properties")) {
            if (input == null) {
                throw new RuntimeException("Unable to find configuration.properties");
            }
            properties.load(input);
        } catch (IOException ex) {
            throw new RuntimeException("Failed to load configuration.properties", ex);
        }
    }

    public static String getProperty(String key) {
        return properties.getProperty(key);
    }

}
