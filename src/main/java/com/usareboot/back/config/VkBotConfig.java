package com.usareboot.back.config;

import com.usareboot.back.persistence.usareboot.entities.ApiTokenEntity;
import com.usareboot.back.persistence.usareboot.repository.ApiTokenRepository;
import com.vk.api.sdk.client.VkApiClient;
import com.vk.api.sdk.client.actors.GroupActor;
import com.vk.api.sdk.client.actors.UserActor;
import com.vk.api.sdk.httpclient.HttpTransportClient;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Optional;

@Configuration
@RequiredArgsConstructor
public class VkBotConfig {

    @Value("${vk.api.groupId}") // ID группы из application.properties
    private Integer groupId;
    @Value("${vk.client.standaloneId}")
    private String standaloneId;

    private final ApiTokenRepository apiTokenRepository;

    public Optional<String> getToken() {
//        return apiTokenRepository.findApiTokenEntityByVkClientId(Long.parseLong(clientId))
        return apiTokenRepository.findApiTokenEntityByGroupId(Long.valueOf(groupId))
                .map(ApiTokenEntity::getToken);
    }

    public Optional<String> getClientToken() {
        return apiTokenRepository.findApiTokenEntityByVkClientId(Long.parseLong(standaloneId))
//        return apiTokenRepository.findApiTokenEntityByGroupId(Long.valueOf(groupId))
                .map(ApiTokenEntity::getToken);
    }

    @Bean
    public VkApiClient vkApiClient() {
        // Создаем клиент для работы с VK API
        return new VkApiClient(new HttpTransportClient());
    }

    @Bean
    public GroupActor groupActor() {
        // Получаем токен из базы данных
        String accessToken = getToken().orElse("");
        // Создаем актора для группы
        return new GroupActor(groupId, accessToken);
    }

    @Bean
    public UserActor userActor() {
        // Получаем токен из базы данных
        String accessToken = getClientToken().orElse("");
        // Создаем актора для группы
        return new UserActor(groupId, accessToken);
    }
}