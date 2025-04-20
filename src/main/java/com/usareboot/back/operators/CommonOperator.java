package com.usareboot.back.operators;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.usareboot.back.persistence.usareboot.entities.ApiTokenEntity;
import com.usareboot.back.persistence.usareboot.repository.ApiTokenRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.time.LocalDateTime;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class CommonOperator {
    private final RestTemplate restTemplate;
    private final ApiTokenRepository apiTokenRepository;

    @Value("${vk.api.groupId}")
    private String groupId;
    @Value("${vk.api.version}")
    private String apiVersion;

    public Optional<String> getTokenGroup(String groupId) {
        return apiTokenRepository.findApiTokenEntityByGroupId(Long.valueOf(groupId))
//        return apiTokenRepository.findApiTokenEntityByVkClientId(Long.parseLong(clientId))
                .map(ApiTokenEntity::getToken);
    }

    public Optional<String> getTokenClient(String clientId) {
        //        if(LocalDateTime.now().isAfter(token.getTokenEnd()))
        return apiTokenRepository.findApiTokenEntityByVkClientId(Long.parseLong(clientId))
                .map(ApiTokenEntity::getToken);
    }

   /* public Optional<String> getServicePasskeyClient(String clientId) {
        //        if(LocalDateTime.now().isAfter(token.getTokenEnd()))
        return apiTokenRepository.findApiTokenEntityByVkClientId(Long.parseLong(clientId))
                .map(ApiTokenEntity::getServicePasskey);
    }*/

    public boolean isExpiredToken(String clientId) {
        ApiTokenEntity apiTokenEntity = apiTokenRepository.findApiTokenEntityByVkClientId(Long.parseLong(clientId)).orElse(null);
        return apiTokenEntity != null && LocalDateTime.now().isAfter(apiTokenEntity.getTokenEnd());
    }

    public Optional<String> getRefreshToken(String clientId) {
        return apiTokenRepository.findApiTokenEntityByVkClientId(Long.parseLong(clientId))
                .map(ApiTokenEntity::getRefreshToken);
    }

    public JsonObject getUserName(int userId) {
        var accessToken = getTokenGroup(groupId).orElse(null);

        String url = UriComponentsBuilder.fromHttpUrl("https://api.vk.com/method/users.get")
                .queryParam("user_ids", userId)
                .queryParam("access_token", accessToken)
                .queryParam("v", apiVersion)
                .build().toUriString();

        String response = restTemplate.getForObject(url, String.class);
        JsonObject json = JsonParser.parseString(response).getAsJsonObject();
        JsonObject user = json.getAsJsonArray("response").get(0).getAsJsonObject();
        return user;/*user.get("first_name").getAsString() + " " + user.get("last_name").getAsString();*/
    }

}
