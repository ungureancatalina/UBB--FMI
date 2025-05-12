package ro.mpp2024.persistence.respository;

import java.io.IOException;
import java.util.Properties;

public class ConfigUtils {
    public static Properties loadProperties() {
        Properties properties = new Properties();
        try {
            properties.load(ConfigUtils.class.getClassLoader().getResourceAsStream("bd.config"));
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("Failed to load database properties", e);
        }
        return properties;
    }
}
