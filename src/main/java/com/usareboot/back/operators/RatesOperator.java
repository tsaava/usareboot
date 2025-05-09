package com.usareboot.back.operators;

import com.usareboot.back.models.Rate;
import com.usareboot.back.models.RatesDTO;
import com.usareboot.back.persistence.usareboot.entities.RateBuyingEntity;
import com.usareboot.back.persistence.usareboot.repository.RateBuyingRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class RatesOperator {
    private final RateBuyingRepository ratesRepository;
    public CompletableFuture<RatesDTO> getListRates(){
        List<Rate> rates = ratesRepository.findAll().stream()
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
                entity.getType(),
                entity.getPercentPayment(),
                entity.getDifferenceUsdt(),
                entity.getRatePayment(),
                entity.getPercentClient(),
                entity.getRateClient(),
                entity.getCountry().getCountryName(),
                entity.getCountry().getCurrency());
    }
}

