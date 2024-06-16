package com.usareboot.back.controllers;
import com.google.gson.Gson;
import com.usareboot.back.services.VkDAO;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.IOException;

@RestController
@CrossOrigin(origins = "*")/*!!!!обязательно во все контроллеры вставлять!!*/
@RequestMapping("/api/usareboot/vk")

@RequiredArgsConstructor
public class VKController {
    @Autowired
    private VkDAO vkService;
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
    @PostMapping("/auth")
    public ResponseEntity<?> getVkAuth(@RequestBody String silent_token,
                                       @RequestParam(name="uuid") String uuid) throws IOException {
        return new ResponseEntity<>(new Gson().toJson(vkService.vkAuth(silent_token,uuid)), HttpStatus.OK);
    }

    @PostMapping("/oauth")
    public ResponseEntity<?> getVkOAuth(@RequestParam(name="code") String code) throws IOException {
        return new ResponseEntity<>(new Gson().toJson(vkService.vkOAuth(code)), HttpStatus.OK);
    }
    @GetMapping("/oauth/authorize")
    public String authorize() {
        String url = UriComponentsBuilder.fromHttpUrl("https://oauth.vk.com/authorize")
                .queryParam("client_id", clientId)
                .queryParam("display", "page")
                .queryParam("redirect_uri", redirectUri)
                .queryParam("scope", "32772,offline")
                .queryParam("response_type", "code")
                .queryParam("v", apiVersion)
                .build().toUriString();
        return "redirect:" + url;
    }
}
