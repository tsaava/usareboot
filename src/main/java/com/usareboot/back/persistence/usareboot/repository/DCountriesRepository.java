package com.usareboot.back.persistence.usareboot.repository;

import com.usareboot.back.persistence.usareboot.entities.DCountriesEntity;
import com.usareboot.back.persistence.usareboot.entities.DStatusesEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public interface DCountriesRepository extends JpaRepository<DCountriesEntity, Long> {

    List<DCountriesEntity> findAllByActive(Integer active);
    Optional<DCountriesEntity> findFirstByCountryName(String countryName);
}

