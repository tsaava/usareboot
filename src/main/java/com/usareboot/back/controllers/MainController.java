package com.usareboot.back.controllers;

import com.google.gson.Gson;
import com.usareboot.back.models.ItemListDTO;
import com.usareboot.back.models.ItemsRequestDTO;
import com.usareboot.back.services.MainService;
import com.vk.api.sdk.exceptions.ApiException;
import com.vk.api.sdk.exceptions.ClientException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "*")/*!!!!обязательно во все контроллеры вставлять!!*/
@RequestMapping("/api/usareboot")
@RequiredArgsConstructor
@PreAuthorize("hasAnyAuthority('admin')")
public class MainController {

    @Autowired
    private MainService mainService;

    @PostMapping ("/import/data/{albom}")
    public ResponseEntity<?> importData(@PathVariable String albom,
                                        @RequestBody String filters) {
        System.out.print(albom+' '+filters);
        albom=albom.replace("\"","");
        mainService.getImportList(filters, albom);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping ("/import/list")
    public ResponseEntity<?> importList( @RequestBody String listAlbom) {
        System.out.println("listAlbom: "+listAlbom);
        return new ResponseEntity<>(new Gson().toJson(mainService.getListImport(listAlbom)), HttpStatus.OK);
    }

    @GetMapping("/item/list/{status}")
    public ResponseEntity<?> getItemList(@PathVariable/*(name = "status", required = false)*/ int status) {
        return new ResponseEntity<>(new Gson().toJson(mainService.getItemListDao(status)), HttpStatus.OK);
    }
    @GetMapping("/item/weight/list")
    public ResponseEntity<?> getItemWeightList() {
        return new ResponseEntity<>(new Gson().toJson(mainService.getItemWeightListDao()), HttpStatus.OK);
    }

    @PostMapping("/item/list")
    public ResponseEntity<?> saveItemList(@RequestBody ItemListDTO data) {
        mainService.saveItemAttribute(data);
        return new ResponseEntity<>( HttpStatus.OK);
    }

    @PostMapping("/item/status")
    public ResponseEntity<?> saveItemStatus(@RequestBody ItemListDTO data) {
        mainService.saveItemStatus(data);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/item/delete/{itemId}")
    public ResponseEntity<?> saveItemStatus(@PathVariable Long itemId) throws ClientException, ApiException {
        if (itemId != null)
            mainService.deleteItem(itemId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/statuses/list/{type}")
    public ResponseEntity<?> getItemStatuses(@PathVariable int type) {
        return new ResponseEntity<>(new Gson().toJson(mainService.getStatusesItem(type)), HttpStatus.OK);
    }

    @PatchMapping("/item/{id}")
    public void patchItemWeightStatus(@PathVariable long id,
                                      @RequestBody String data) {
        System.out.println(data);
        Gson g = new Gson();
        mainService.saveItemWeightAndStatus(id,g.fromJson(data,ItemsRequestDTO.class));
//        return new ResponseEntity<>(new Gson().toJson(mainDAO.getItemListDao()), HttpStatus.OK);
    }

    @PatchMapping("/item/set/date/all")
    public void patchItemDate() {
        mainService.setItemDate();
//        return new ResponseEntity<>(new Gson().toJson(mainDAO.getItemListDao()), HttpStatus.OK);
    }


}