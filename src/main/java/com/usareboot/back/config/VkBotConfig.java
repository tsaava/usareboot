package com.usareboot.back.config;

import com.usareboot.back.entities.ApiTokenEntity;
import com.usareboot.back.repositories.ApiTokenRepository;
import com.usareboot.back.services.vk.VkService;
import com.vk.api.sdk.client.VkApiClient;
import com.vk.api.sdk.client.actors.GroupActor;
import com.vk.api.sdk.httpclient.HttpTransportClient;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Optional;

@Configuration
@RequiredArgsConstructor
public class VkBotConfig {

    @Value("${vk.api.groupId}") // ID группы из application.properties
    private Long groupId;
    @Value("${vk.client.id}")
    private String clientId;

    private final ApiTokenRepository apiTokenRepository;

    public Optional<String> getToken(String clientId) {
        return apiTokenRepository.findApiTokenEntityByVkClientId(Long.parseLong(clientId))
                .map(ApiTokenEntity::getToken);
    }

    @Bean
    public VkApiClient vkApiClient() {
        // Создаем клиент для работы с VK API
        return new VkApiClient(HttpTransportClient.getInstance());
    }

    @Bean
    public GroupActor groupActor() {
        // Получаем токен из базы данных
        String accessToken = getToken(String.valueOf(clientId)).orElse("");
        // Создаем актора для группы
        return new GroupActor(groupId, accessToken);
    }
}