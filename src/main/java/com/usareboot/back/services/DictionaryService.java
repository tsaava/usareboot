package com.usareboot.back.services;

import com.usareboot.back.operators.CommonOperator;
import com.usareboot.back.operators.DictionaryOperator;
import com.usareboot.back.persistence.usareboot.entities.DCountriesEntity;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ThreadLocalRandom;

@Service
@Slf4j
@RequiredArgsConstructor
public class DictionaryService {
    private final ThreadLocal<Integer> threadLocal = ThreadLocal.withInitial(() -> ThreadLocalRandom.current().nextInt(10000, 100000));

    @Value("${vk.api.groupId}")
    private String groupId;
    @PersistenceContext
    private EntityManager entityManager;

    private final CommonOperator commonOperator;
    private final DictionaryOperator dictionaryOperator;
    @Async
    public CompletableFuture<List<DCountriesEntity>> getCountries() {
        var eventId = threadLocal.get();

        log.info("[Сценарий getCountries][Шаг: Начало][EventID: {}]", eventId);
        List<DCountriesEntity> countries = dictionaryOperator.getCountries();

        log.info("[Сценарий getCountries][Шаг: Финиш][EventID: {}]", eventId);
        return CompletableFuture.completedFuture(countries);
    }
}
