package com.usareboot.back.client.config;

import org.yaml.snakeyaml.DumperOptions;
import org.yaml.snakeyaml.Yaml;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

public class YamlConfigService {
    private final String filePath;

    public YamlConfigService(String filePath) {
        this.filePath = filePath;
    }

    @SuppressWarnings("unchecked")
    public Map<String, Object> loadConfig() throws IOException {
        Yaml yaml = new Yaml();
        try (FileInputStream inputStream = new FileInputStream("src/main/resources/application.yaml")) {
            return yaml.load(inputStream);
        }
    }

    public void saveConfig(Map<String, Object> config) throws IOException {
        DumperOptions options = new DumperOptions();
        options.setDefaultFlowStyle(DumperOptions.FlowStyle.BLOCK);
        Yaml yaml = new Yaml(options);
        try (FileWriter writer = new FileWriter(filePath)) {
            yaml.dump(config, writer);
        }
    }

    @SuppressWarnings("unchecked")
    public void updateToken(String newToken) throws IOException {
        Map<String, Object> config = loadConfig();
        if (config == null) {
            config = new HashMap<>();
        }
        Map<String, Object> vkConfig = (Map<String, Object>) config.get("vk");
        if (vkConfig == null) {
            vkConfig = new HashMap<>();
            config.put("vk", vkConfig);
        }
        vkConfig.put("token", newToken);
        saveConfig(config);
//        Map<String, Object> vkConfig = (Map<String, Object>) config.get("vk");
//        if (vkConfig != null) {
//            vkConfig.put("token", newToken);
//            saveConfig(config);
//        } else {
//            throw new RuntimeException("VK configuration not found in YAML file.");
//        }
    }
}
