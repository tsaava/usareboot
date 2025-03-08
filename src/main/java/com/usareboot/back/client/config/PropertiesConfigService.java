package com.usareboot.back.client.config;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Properties;
public class PropertiesConfigService {
    private final String filePath;

    public PropertiesConfigService(String filePath) {
        this.filePath = filePath;
    }

    public Properties loadProperties() throws IOException {
        Properties properties = new Properties();
        try (InputStream input = new FileInputStream(filePath)) {
            properties.load(input);
        }
        return properties;
    }

    public void saveProperties(Properties properties) throws IOException {
        try (OutputStream output = new FileOutputStream(filePath)) {
            properties.store(output, null);
        }
    }

    public void updateToken(String newToken) throws IOException {
        Properties properties = loadProperties();
        properties.setProperty("vk.token", newToken);
        saveProperties(properties);
    }
}
