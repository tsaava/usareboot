package com.usareboot.back.services;

import com.usareboot.back.models.Rate;
import com.usareboot.back.models.RatesDTO;
import com.usareboot.back.operators.CommonOperator;
import com.usareboot.back.operators.RatesOperator;
import com.usareboot.back.persistence.usareboot.entities.AlbumsItemsEntity;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.List;
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
}
