package com.usareboot.back.persistence.usareboot.repository;

import com.usareboot.back.persistence.usareboot.entities.DCountriesEntity;
import com.usareboot.back.persistence.usareboot.entities.OrdersEntity;
import com.usareboot.back.persistence.usareboot.entities.RateBuyingEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

import java.time.LocalDate;
import java.util.List;

public interface RateBuyingRepository extends JpaRepository<RateBuyingEntity, Long> {
    RateBuyingEntity findFirstByCountryAndDateBuyingIsBefore(DCountriesEntity country, LocalDate dateBuying);
    List<RateBuyingEntity> findAllByCountryAndDateBuyingIsBefore(DCountriesEntity country, LocalDate dateBuying);
//    List<RateBuyingEntity> findAllByCountryAndDateBuyingLessThanOrEqual(DCountriesEntity country, LocalDate dateBuying);
    List<RateBuyingEntity> findAllByCountryAndDateBuyingLessThanEqual(DCountriesEntity country, LocalDate dateBuying);
    List<RateBuyingEntity> findAllByCountryCurrencyAndDateBuyingLessThanEqual(String currency, LocalDate dateBuying);
}
