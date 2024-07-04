package com.usareboot.back.controllers;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.usareboot.back.client.config.PropertiesConfigService;
import com.usareboot.back.client.config.YamlConfigService;
import com.usareboot.back.services.VkDAO;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.*;
import java.util.Properties;

@RestController
@CrossOrigin(origins = "*")/*!!!!обязательно во все контроллеры вставлять!!*/
@RequestMapping("/api/usareboot/vk")
@RequiredArgsConstructor
public class VKController {
    @Value("${vk.config.file.path}")
    private String configFilePath;

    @Value("${vk.api.version}")
    private String apiVersion;

    @Value("${vk.api.groupId}")
    private String groupId;

    @Value("${vk.api.redirectUri}")
    private String redirectUri;

    @Value("${vk.client.id}")
    private String clientId;

    @Value("${vk.client.secret}")
    private String clientSecret;

    private final VkDAO vkService;

    private final RestTemplate restTemplate;

    private final PropertiesConfigService yamlConfigService;
    @Autowired
    public VKController(RestTemplate restTemplate, VkDAO vkService) {
        this.restTemplate = restTemplate;
        this.yamlConfigService = new PropertiesConfigService(configFilePath);
        this.vkService = vkService;
    }

    @PostMapping("/auth")
    public ResponseEntity<?> getVkAuth(@RequestBody String silent_token,
                                       @RequestParam(name="uuid") String uuid) throws IOException {
        return new ResponseEntity<>(new Gson().toJson(vkService.vkAuth(silent_token,uuid)), HttpStatus.OK);
    }

    @PostMapping("/oauth")
    public ResponseEntity<?> getVkOAuth(@RequestParam(name="code") String code) throws IOException {
        return new ResponseEntity<>(new Gson().toJson(vkService.vkOAuth(code)), HttpStatus.OK);
    }
//    @GetMapping("/oauth/authorize")
//    public String authorize() {
//        String url = UriComponentsBuilder.fromHttpUrl("https://oauth.vk.com/authorize")
//                .queryParam("client_id", clientId)
//                .queryParam("display", "page")
//                .queryParam("redirect_uri", redirectUri)
//                .queryParam("scope", "32772,offline")
//                .queryParam("response_type", "code")
//                .queryParam("v", apiVersion)
//                .build().toUriString();
//        return "redirect:" + url;
//    }

    @GetMapping("/oauth/callback")
    public ResponseEntity<?> callback(@RequestParam("code") String code) throws FileNotFoundException {
        String tokenUrl = UriComponentsBuilder.fromHttpUrl("https://oauth.vk.com/access_token")
                .queryParam("client_id", clientId)
                .queryParam("client_secret", clientSecret)
                .queryParam("redirect_uri", redirectUri)
                .queryParam("code", code)
                .build().toUriString();

        String response = restTemplate.getForObject(tokenUrl, String.class);
        assert response != null;
        JsonObject json = JsonParser.parseString(response).getAsJsonObject();
        String accessToken = json.get("access_token").getAsString();
        saveAccessTokenToProperties(accessToken);
//        try {
////            yamlConfigService.updateToken(accessToken);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
        return new ResponseEntity<>(HttpStatus.OK);
    }

    private void saveAccessTokenToProperties(String accessToken) throws FileNotFoundException {
        Properties properties = new Properties();

        try (InputStream input = new FileInputStream("src/main/resources/application.properties")) {
            properties.load(input);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        properties.setProperty("vk.api.token", accessToken);
        try (OutputStream output = new FileOutputStream("src/main/resources/application.properties", false)) {
            // Загрузка текущих свойств
//            properties.load(new FileInputStream("src/main/resources/application.properties"));

            // Установка нового access_token


            // Сохранение обновленных свойств
            properties.store(output, null);

        } catch (IOException io) {
            io.printStackTrace();
        }
    }
}
