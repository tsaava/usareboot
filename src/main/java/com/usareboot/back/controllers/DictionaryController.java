package com.usareboot.back.controllers;

import com.usareboot.back.models.CountriesDTO;
import com.usareboot.back.models.RatesDTO;
import com.usareboot.back.operators.RatesOperator;
import com.usareboot.back.persistence.usareboot.entities.DCountriesEntity;
import com.usareboot.back.services.DictionaryService;
import com.usareboot.back.services.RatesService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.concurrent.ExecutionException;

@RestController
@CrossOrigin(origins = "*")/*!!!!обязательно во все контроллеры вставлять!!*/
@RequestMapping("/api/usareboot/dictionary")
@PreAuthorize("hasAnyRole('ADMIN')")
@RequiredArgsConstructor
@Slf4j
public class DictionaryController {
    private final DictionaryService dictionaryService;

    @GetMapping("/countries")
    public ResponseEntity<?> getCountryList() throws ExecutionException, InterruptedException {
        List<DCountriesEntity> countries = dictionaryService.getCountries().get();
        return ResponseEntity.ok(countries);
    }
}