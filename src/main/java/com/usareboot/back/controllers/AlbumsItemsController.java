package com.usareboot.back.controllers;

import com.google.gson.Gson;
import com.usareboot.back.dto.AlbumRowRequestDTO;
import com.usareboot.back.entities.AlbumsEntity;
import com.usareboot.back.services.AlbumsDAO;
import com.usareboot.back.services.AlbumsItemsDAO;
import com.usareboot.back.services.auth.VkDAO;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.text.ParseException;

@RestController
@CrossOrigin(origins = "*")/*!!!!обязательно во все контроллеры вставлять!!*/
@RequestMapping("/api/usareboot/albums")
@RequiredArgsConstructor

public class AlbumsItemsController {
    @Autowired
    private AlbumsItemsDAO albumsItemsDAO;



    @GetMapping("/items/list/{albumId}")
    public ResponseEntity<?> albumItemsList(@PathVariable long albumId){
        return new ResponseEntity<>(new Gson().toJson(albumsItemsDAO.getAlbumsItems(albumId)), HttpStatus.OK);
    }
}