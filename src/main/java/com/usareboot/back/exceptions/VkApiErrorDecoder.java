package com.usareboot.back.exceptions;

import feign.Response;
import feign.codec.ErrorDecoder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

@Slf4j
public class VkApiErrorDecoder implements ErrorDecoder {

    @Override
    public Exception decode(String methodKey, Response response) {
        // Логирование ошибки
        log.error("Ошибка при вызове VK API: {}", response.status());

        // Возвращаем кастомное исключение
        return new ResponseStatusException(
                HttpStatus.valueOf(response.status()),
                "Ошибка VK API: " + response.body()
        );
    }
}