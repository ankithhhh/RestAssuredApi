package utils;

import java.io.FileInputStream;

import java.io.IOException;
import java.util.Properties;

public class ConfigReader {

    private static final String CONFIG_FILE_PATH = "src/test/resources/configuration/config.properties";
    private static Properties properties;

    static {
        try (FileInputStream input = new FileInputStream(CONFIG_FILE_PATH)) {
            properties = new Properties();
            properties.load(input);
        } catch (IOException e) {
            throw new RuntimeException("Failed to load config.properties file", e);
        }
    }

    public static String getBaseUrl() {
        return properties.getProperty("baseUrl");
    }
}