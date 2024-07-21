package com.usareboot.back.controllers;

import com.google.gson.Gson;
import com.usareboot.back.models.ItemsRequestDTO;
import com.usareboot.back.services.MainDAO;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "*")/*!!!!обязательно во все контроллеры вставлять!!*/
@RequestMapping("/api/usareboot")
@RequiredArgsConstructor

public class MainController {

    @Autowired
    private MainDAO mainDAO;

    @PostMapping ("/import/data/{albom}")
    public ResponseEntity<?> importData(@PathVariable String albom,
                                        @RequestBody String filters) {
        System.out.print(albom+' '+filters);
        albom=albom.replace("\"","");
        mainDAO.getImportList(filters, albom);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping ("/import/list")
    public ResponseEntity<?> importList( @RequestBody String listAlbom) {
        System.out.println("listAlbom: "+listAlbom);
        return new ResponseEntity<>(new Gson().toJson(mainDAO.getListImport(listAlbom)), HttpStatus.OK);
    }

    @GetMapping("/item/list/{status}")
    public ResponseEntity<?> getItemList(@PathVariable/*(name = "status", required = false)*/ int status) {
        return new ResponseEntity<>(new Gson().toJson(mainDAO.getItemListDao(status)), HttpStatus.OK);
    }

    @GetMapping("/item/weight/list")
    public ResponseEntity<?> getWeightItemList() {
        return new ResponseEntity<>(new Gson().toJson(mainDAO.getItemWeightListDao()), HttpStatus.OK);
    }

    @GetMapping("/statuses/list/{type}")
    public ResponseEntity<?> getItemStatuses(@PathVariable int type) {
        return new ResponseEntity<>(new Gson().toJson(mainDAO.getStatusesItem(type)), HttpStatus.OK);
    }

    @PatchMapping("/item/{id}")
    public void patchItemWeightStatus(@PathVariable long id,
                                      @RequestBody String data) {
        System.out.println(data);
        Gson g = new Gson();
        mainDAO.saveItemWeightAndStatus(id,g.fromJson(data,ItemsRequestDTO.class));
//        return new ResponseEntity<>(new Gson().toJson(mainDAO.getItemListDao()), HttpStatus.OK);
    }

    @PatchMapping("/item/set/date/all")
    public void patchItemDate() {
        mainDAO.setItemDate();
//        return new ResponseEntity<>(new Gson().toJson(mainDAO.getItemListDao()), HttpStatus.OK);
    }


}