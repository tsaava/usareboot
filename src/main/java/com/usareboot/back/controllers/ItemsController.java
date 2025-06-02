package com.usareboot.back.controllers;

import com.google.gson.Gson;
import com.usareboot.back.models.ItemListDTO;
import com.usareboot.back.models.ItemRowsDTO;
import com.usareboot.back.models.ItemsRequestDTO;
import com.usareboot.back.security.JwtUtils;
import com.usareboot.back.services.ItemsService;
import com.usareboot.back.services.MainService;
import com.vk.api.sdk.exceptions.ApiException;
import com.vk.api.sdk.exceptions.ClientException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
@RestController
@CrossOrigin(origins = "*")/*!!!!обязательно во все контроллеры вставлять!!*/
@RequestMapping("/api/usareboot/item")
@RequiredArgsConstructor
@Slf4j
public class ItemsController {
    @Autowired
    private MainService mainService;

    private final ItemsService itemsService;

    @Autowired
    private JwtUtils jwtUtils;

    @GetMapping("/list/{status}")
    public ResponseEntity<?> getItemList(@PathVariable/*(name = "status", required = false)*/ int status) {
        return new ResponseEntity<>(new Gson().toJson(mainService.getItemListDao(status)), HttpStatus.OK);
    }

    @GetMapping("/weight/list")
    public ResponseEntity<?> getItemWeightList() {
        return new ResponseEntity<>(new Gson().toJson(mainService.getItemWeightListDao()), HttpStatus.OK);
    }

    @PostMapping("/list")
    public ResponseEntity<?> saveItemList(@RequestBody ItemListDTO data) {
        log.info("data: {}", data);
        mainService.saveItemAttribute(data);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/{itemId}/duplicate")
    public ResponseEntity<?> saveDuplicateItemRow(@PathVariable Long itemId) {
        mainService.saveDuplicateItemRow(itemId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/status")
    public ResponseEntity<?> saveItemStatus(@RequestBody ItemListDTO data) {
        mainService.saveItemStatus(data);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/delete/{itemId}")
    public ResponseEntity<?> saveItemStatus(@PathVariable Long itemId) throws ClientException, ApiException {
        if (itemId != null)
            mainService.deleteItem(itemId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PatchMapping("/{id}")
    public void patchItemWeightStatus(@PathVariable long id,
                                      @RequestBody String data) {
        System.out.println(data);
        Gson g = new Gson();
        mainService.saveItemWeightAndStatus(id, g.fromJson(data, ItemsRequestDTO.class));
//        return new ResponseEntity<>(new Gson().toJson(mainDAO.getItemListDao()), HttpStatus.OK);
    }

    @PatchMapping("/set/date/all")
    public void patchItemDate() {
        mainService.setItemDate();
//        return new ResponseEntity<>(new Gson().toJson(mainDAO.getItemListDao()), HttpStatus.OK);
    }

    @PostMapping("/save")
    public ResponseEntity<?> saveItemRows(@RequestBody ItemRowsDTO data) {
        log.info("data: {}", data);
        itemsService.saveItemRows(data);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
