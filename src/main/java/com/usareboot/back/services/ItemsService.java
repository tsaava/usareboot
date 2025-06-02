package com.usareboot.back.services;

import com.usareboot.back.models.ItemRowsDTO;
import com.usareboot.back.operators.ItemOperator;
import com.usareboot.back.persistence.usareboot.entities.DCountriesEntity;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ThreadLocalRandom;

@Service
@Slf4j
@RequiredArgsConstructor
public class ItemsService {
    private final ThreadLocal<Integer> threadLocal = ThreadLocal.withInitial(() -> ThreadLocalRandom.current().nextInt(10000, 100000));
    private final ItemOperator itemOperator;

    public void saveItemRows(ItemRowsDTO itemRowsDTO) {
        var eventId = threadLocal.get();

        log.info("[Сценарий saveItemRows][Шаг: Начало][EventID: {}]", eventId);

        log.info("[Сценарий saveItemRows][Шаг: Сохранение номера заказа][EventID: {}]", eventId);
        itemOperator.saveNumberOrder(itemRowsDTO);

        log.info("[Сценарий saveItemRows][Шаг: Финиш][EventID: {}]", eventId);
    }
}
