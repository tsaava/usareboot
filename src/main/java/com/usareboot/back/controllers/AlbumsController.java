package com.usareboot.back.controllers;

import com.google.gson.Gson;
import com.usareboot.back.dto.AlbumRowRequestDTO;
import com.usareboot.back.entities.AlbumsEntity;
import com.usareboot.back.services.AlbumsDAO;
import com.usareboot.back.services.VkDAO;
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

public class AlbumsController {

    private AlbumsDAO albumsDAO;
    private VkDAO vkDAO;

    @Autowired
    public AlbumsController(AlbumsDAO albumsDAO, VkDAO vkDAO/*, Environment environment*/) {
        this.albumsDAO = albumsDAO;
        this.vkDAO =vkDAO;
        //this.environment =environment;
    }

    @GetMapping("/list")
    public ResponseEntity<?> albumsList(/*@RequestParam(name="date")
                                            @DateTimeFormat(pattern = "dd.MM.yyyy") Date albumDate*/){
        return new ResponseEntity<>(new Gson().toJson(albumsDAO.getListAlbums()), HttpStatus.OK);
    }

    @GetMapping("/card")
    public ResponseEntity<?> cardList(){
        return new ResponseEntity<>(new Gson().toJson(albumsDAO.getAlbumCards()), HttpStatus.OK);
    }

    @PostMapping("/add/{token}")
    public ResponseEntity<?> addAlbum(@PathVariable String token,
                                      @RequestBody AlbumsEntity data) throws IOException {
//        System.out.print("addAlbum data");
//        System.out.println(data.gets);

        Integer album = vkDAO.createAlbum(token, data);
        System.out.println("в вк альбом создался, id = "+album);
        albumsDAO.albumsAdd(data,album);
        System.out.println("в базу альбом добавился");
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PatchMapping("/update/{token}")
    public ResponseEntity<?> patchAlbum(/*@RequestParam(name = "id") Integer id,
                                        @RequestParam(name = "vkId") String vkId,
                                        @RequestParam(name = "token") String token*/
                                        @PathVariable String token,
                                        @RequestBody AlbumRowRequestDTO data) throws IOException, ParseException {
//        System.out.printf("patchAlbum +%s %s %s %s",id,vkId,token,data);
        System.out.println(data);
        var vk=vkDAO.updAlbum(String.valueOf(data.getAlbumVkId()),token, data);
        System.out.println(vk);
        System.out.println("в вк альбом обновился, id = " + data.getAlbumVkId());
//        albumsDAO.convertToEntity(data);
//        AlbumsEntity albumsEntity = albumsDAO.convertToEntity(data);
//        System.out.println(albumsEntity);
        albumsDAO.albumsUpd(data, data.getAlbumId());
        System.out.println("в базе альбом обновился");
        return new ResponseEntity<>(HttpStatus.OK);
    }


}