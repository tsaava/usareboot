package com.usareboot.back.services;

import com.usareboot.back.models.CountriesDTO;
import com.usareboot.back.models.Rate;
import com.usareboot.back.models.RatesDTO;
import com.usareboot.back.operators.CommonOperator;
import com.usareboot.back.operators.RatesOperator;
import com.usareboot.back.persistence.usareboot.entities.DCountriesEntity;
import com.usareboot.back.persistence.usareboot.repository.DCountriesRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ThreadLocalRandom;

@Service
@Slf4j
@RequiredArgsConstructor
public class RatesService {

    final ModelMapper modelMapper;
    private final CommonOperator commonOperator;
    private final RatesOperator ratesOperator;

    @Value("${vk.client.standaloneId}")
    private String standaloneId;

    @Value("${vk.api.groupId}")
    private String groupId;
    private final ThreadLocal<Integer> threadLocal = ThreadLocal.withInitial(() -> ThreadLocalRandom.current().nextInt(10000, 100000));

    @Async("taskExecutor")
    public CompletableFuture<RatesDTO> getListRates() {
        var eventId = threadLocal.get();
        log.info("[Сценарий getListRates][Шаг: Начало][EventID: {}]", eventId);

        log.info("[Сценарий getListRates][Шаг: Финиш][EventID: {}]", eventId);
        return ratesOperator.getListRates();
    }

    @Async("taskExecutor")
    public void saveRowRate(Rate rate) {
        var eventId = threadLocal.get();
        log.info("[Сценарий saveRowRate][Шаг: Начало][EventID: {}]", eventId);

        log.info("[Сценарий saveRowRate][Шаг: Сохранение данных в таблицу][EventID: {}]", eventId);
        ratesOperator.saveRowRate(rate);

        log.info("[Сценарий saveRowRate][Шаг: Финиш][EventID: {}]", eventId);
    }

    public Rate getActiveRate(String currency) {
        var eventId = threadLocal.get();
        log.info("[Сценарий getActiveRate][Шаг: Начало][EventID: {}]", eventId);
        var date = LocalDate.now();

        log.info("[Сценарий getActiveRate][Шаг: Получение сущности Страны по валюте][EventID: {}]", eventId);
//        DCountriesEntity country = ratesOperator.getCountryByCurrency(currency);

        log.info("[Сценарий getActiveRate][Шаг: Получение активного курса. Фильтр по валюте и текущей дате][EventID: {}]", eventId);
        Rate rate = ratesOperator.getActiveRateByCurrencyAndDate(currency, date);

        log.info("[Сценарий getActiveRate][Шаг: Финиш][EventID: {}]", eventId);
        return rate;
    }
}
