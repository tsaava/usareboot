package com.usareboot.back.operators;

import com.usareboot.back.models.Rate;
import com.usareboot.back.models.RatesDTO;
import com.usareboot.back.persistence.usareboot.entities.DCountriesEntity;
import com.usareboot.back.persistence.usareboot.entities.RateBuyingEntity;
import com.usareboot.back.persistence.usareboot.repository.DCountriesRepository;
import com.usareboot.back.persistence.usareboot.repository.RateBuyingRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class RatesOperator {
    private final RateBuyingRepository ratesRepository;
    private final DCountriesRepository countriesRepository;

    public CompletableFuture<RatesDTO> getListRates() {
        List<Rate> rates = ratesRepository.findAll().stream()
                .sorted(Comparator.comparing(RateBuyingEntity::getDateCreate).reversed())
                .map(this::convertToDTO)
                .collect(Collectors.toList());
        RatesDTO dto = RatesDTO.builder().rates(rates).build();
        return CompletableFuture.completedFuture(dto);
    }

    private Rate convertToDTO(RateBuyingEntity entity) {
        return new Rate(
                entity.getRateBuyingId(),
                entity.getCountry().getCountryId(),
                entity.getDateBuying(),
                entity.getRateExchange(),
                entity.getSumExchange(),
                entity.getSumExchangeUsdt(),
                entity.getType(),
                entity.getPercentPayment(),
                entity.getDifferenceUsdt(),
                entity.getRatePayment(),
                entity.getPercentClient(),
                entity.getRateClient(),
                entity.getCountry().getCountryName(),
                entity.getCountry().getCurrency());
    }

    public void saveRowRate(Rate rate) {
        log.debug("rate: {}", rate);
        RateBuyingEntity rateBuying = ratesRepository.findById(rate.getRateBuyingId()).orElse(new RateBuyingEntity());
        rateBuying.setRateClient(rate.getRateClient());
        rateBuying.setRateExchange(rate.getRateExchange());
        rateBuying.setRatePayment(rate.getRatePayment());
        rateBuying.setDateBuying(rate.getDateBuying());
        rateBuying.setPercentClient(rate.getPercentClient());
        rateBuying.setPercentPayment(rate.getPercentPayment());
        rateBuying.setType(rate.getType());
        rateBuying.setSumExchange(rate.getSumExchange());
        rateBuying.setSumExchangeUsdt(rate.getSumExchangeUsdt());
        rateBuying.setDifferenceUsdt(rate.getDifferenceUsdt());
        rateBuying.setDateCreate(LocalDateTime.now());
        DCountriesEntity country = countriesRepository.findFirstByCountryName(rate.getCountryName()).orElse(new DCountriesEntity());
        log.debug("country: {}", country);
        rateBuying.setCountry(country);
        ratesRepository.save(rateBuying);
    }
}

