package com.usareboot.back.controllers;

import com.usareboot.back.models.AlbumsItemsDTO;
import com.usareboot.back.models.RatesDTO;
import com.usareboot.back.services.RatesService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.concurrent.CompletableFuture;

@RestController
@CrossOrigin(origins = "*")/*!!!!обязательно во все контроллеры вставлять!!*/
@RequestMapping("/api/usareboot/rate")
@PreAuthorize("hasAnyRole('ADMIN')")
@RequiredArgsConstructor
@Slf4j
public class RatesController {

    private final RatesService ratesService;

    @GetMapping("/list")
    public CompletableFuture<ResponseEntity<RatesDTO>> getRatesListAsync() {
        return ratesService.getListRates()
                .thenApply(ResponseEntity::ok)
                .exceptionally(ex -> {
                    log.error("Ошибка при выдаче курса", ex);
                    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
                });
    }

    /*@PatchMapping("/update")
    public ResponseEntity<?> updateAlbumItem(@RequestBody AlbumsItemsDTO data) throws IOException {
        albumsItemsService.updateAlbumItem(data);
        return new ResponseEntity<>(HttpStatus.OK);
    }*/
}