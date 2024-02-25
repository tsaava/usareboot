package com.usareboot.back.controllers;

import com.google.gson.Gson;
import com.usareboot.back.dto.ItemsRequestDTO;
import com.usareboot.back.repositories.AlbumsRepository;
import com.usareboot.back.services.AlbumsDAO;
import com.usareboot.back.services.MainDAO;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

@RestController
@CrossOrigin(origins = "*")/*!!!!обязательно во все контроллеры вставлять!!*/
@RequestMapping("/api/usareboot/albums")
@RequiredArgsConstructor

public class AlbumsController {

    private AlbumsDAO albumsDAO;

    @Autowired
    public AlbumsController(AlbumsDAO albumsDAO) {
        this.albumsDAO = albumsDAO;
    }

    @GetMapping("/list")
    public ResponseEntity<?> importData(@RequestParam("date")
                                            @DateTimeFormat(pattern = "dd.MM.yyyy") Date albumDate){
        System.out.println("aboba");
        return new ResponseEntity<>(new Gson().toJson(albumsDAO.getListAlbums(albumDate)), HttpStatus.OK);
    }


}