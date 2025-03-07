package com.usareboot.back.client.config.vk;

import com.usareboot.back.exceptions.VkApiErrorDecoder;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpHeaders;
import org.springframework.security.oauth2.server.resource.web.BearerTokenResolver;
import org.springframework.security.oauth2.server.resource.web.DefaultBearerTokenResolver;
import feign.Logger;
import feign.Request;
import feign.Retryer;
import feign.codec.ErrorDecoder;

import static java.util.concurrent.TimeUnit.SECONDS;
@RequiredArgsConstructor
public class VkClientConfig {
    @Bean
    public BearerTokenResolver bearerTokenResolver() {
        DefaultBearerTokenResolver bearerTokenResolver = new DefaultBearerTokenResolver();
        bearerTokenResolver.setBearerTokenHeaderName(HttpHeaders.PROXY_AUTHORIZATION);
        return bearerTokenResolver;
    }

    // Базовый URL для VK API
    @Bean
    public String vkApiBaseUrl() {
        return "https://api.vk.com/method";
    }

    // Настройка логгера для Feign Client
    @Bean
    public Logger.Level feignLoggerLevel() {
        return Logger.Level.FULL; // Логирование всех запросов и ответов
    }

 /*   // Настройка таймаутов для Feign Client
    @Bean
    public Request.Options feignOptions() {
        return new Request.Options(
                10, SECONDS, // Таймаут подключения (connect timeout)
                10, SECONDS  // Таймаут чтения (read timeout)
        );
    }
*/
    // Настройка повторных попыток (retry)
    @Bean
    public Retryer feignRetryer() {
        return new Retryer.Default(
                1000, // Интервал между попытками (1 секунда)
                SECONDS.toMillis(3), // Максимальный интервал (3 секунды)
                3 // Максимальное количество попыток
        );
    }

    // Настройка обработки ошибок
    @Bean
    public ErrorDecoder feignErrorDecoder() {
        return new VkApiErrorDecoder(); // Кастомный обработчик ошибок
    }
}
