package com.usareboot.back.controllers;

import com.usareboot.back.models.CountriesDTO;
import com.usareboot.back.models.RatesDTO;
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

import java.util.concurrent.ExecutionException;

@RestController
@CrossOrigin(origins = "*")/*!!!!обязательно во все контроллеры вставлять!!*/
@RequestMapping("/api/usareboot/dictionary")
@PreAuthorize("hasAnyRole('ADMIN')")
@RequiredArgsConstructor
@Slf4j
public class DictionaryController {

    private final RatesService ratesService;

    @GetMapping("/country")
    public ResponseEntity<?> getCountryList() throws ExecutionException, InterruptedException {
//        CountriesDTO rates = ratesService.getListRates().get();
        return new ResponseEntity<>(HttpStatus.OK);
        /*return ratesService.getListRates()
                .thenApply(ResponseEntity::ok)
                .exceptionally(ex -> {
                    log.error("Ошибка при выдаче курса", ex);
                    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
                });*/
    }
}