package com.usareboot.back.operators;

import com.usareboot.back.persistence.usareboot.entities.DCountriesEntity;
import com.usareboot.back.persistence.usareboot.repository.DCountriesRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class DictionaryOperator {
    private final DCountriesRepository countriesRepository;
    private final int active = 1;
    public List<DCountriesEntity> getCountries() {
        return countriesRepository.findAllByActive(active);
    }
}
