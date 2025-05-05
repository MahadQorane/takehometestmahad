package com.myibltest.config;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Configuration manager class to handle properties and settings
 */
public class ConfigManager {
    private static final Logger LOGGER = LoggerFactory.getLogger(ConfigManager.class);
    private static final Properties properties = new Properties();
    private static ConfigManager instance;

    /**
     * Private constructor to enforce singleton pattern
     */
    private ConfigManager() {
        loadDefaultProperties();
    }

    /**
     * Get the singleton instance of ConfigManager
     *
     * @return ConfigManager instance
     */
    public static synchronized ConfigManager getInstance() {
        if (instance == null) {
            instance = new ConfigManager();
        }
        return instance;
    }

    /**
     * Load default properties from config file
     */
    private void loadDefaultProperties() {
        try {
            // Default properties can be loaded from a file
            // properties.load(getClass().getClassLoader().getResourceAsStream("config.properties"));

            // For this example, we're setting defaults programmatically
            properties.setProperty("api.base.url", "https://testapi.io/api/RMSTest");
            properties.setProperty("api.timeout", "10000");
            properties.setProperty("response.time.threshold", "1000");
        } catch (Exception e) {
            LOGGER.error("Failed to load default properties: {}", e.getMessage());
        }
    }

    /**
     * Load properties from a file
     *
     * @param filePath path to the properties file
     */
    public void loadProperties(String filePath) {
        try (FileInputStream fis = new FileInputStream(filePath)) {
            properties.load(fis);
            LOGGER.info("Properties loaded from {}", filePath);
        } catch (IOException e) {
            LOGGER.error("Failed to load properties from {}: {}", filePath, e.getMessage());
        }
    }

    /**
     * Get a property value
     *
     * @param key property key
     * @return property value or null if not found
     */
    public String getProperty(String key) {
        return properties.getProperty(key);
    }

    /**
     * Get a property value with default
     *
     * @param key property key
     * @param defaultValue default value if property is not found
     * @return property value or default value if not found
     */
    public String getProperty(String key, String defaultValue) {
        return properties.getProperty(key, defaultValue);
    }

    /**
     * Set a property value
     *
     * @param key property key
     * @param value property value
     */
    public void setProperty(String key, String value) {
        properties.setProperty(key, value);
    }

    /**
     * Get property as integer
     *
     * @param key property key
     * @param defaultValue default value if property is not found or not a valid integer
     * @return property value as integer or default value
     */
    public int getIntProperty(String key, int defaultValue) {
        String value = properties.getProperty(key);
        if (value == null) {
            return defaultValue;
        }

        try {
            return Integer.parseInt(value);
        } catch (NumberFormatException e) {
            LOGGER.warn("Property {} is not a valid integer: {}", key, value);
            return defaultValue;
        }
    }
}