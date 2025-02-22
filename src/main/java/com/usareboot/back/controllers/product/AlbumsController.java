package com.usareboot.back.controllers.product;

import com.google.gson.Gson;
import com.usareboot.back.entities.AlbumsEntity;
import com.usareboot.back.models.AlbumRowRequestDTO;
import com.usareboot.back.services.AlbumsService;
import com.usareboot.back.services.vk.VkService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@CrossOrigin(origins = "*")/*!!!!обязательно во все контроллеры вставлять!!*/
@RequestMapping("/api/usareboot/albums")
@RequiredArgsConstructor

public class AlbumsController {

    private AlbumsService albumsService;
    private VkService vkService;

    @Autowired
    public AlbumsController(AlbumsService albumsService, VkService vkService/*, Environment environment*/) {
        this.albumsService = albumsService;
        this.vkService = vkService;
    }

    @GetMapping("/list")
    public ResponseEntity<?> albumsList(/*@RequestParam(name="date")
                                            @DateTimeFormat(pattern = "dd.MM.yyyy") Date albumDate*/){
        return new ResponseEntity<>(new Gson().toJson(albumsService.getListAlbums()), HttpStatus.OK);
    }

    @GetMapping("/card")
    public ResponseEntity<?> cardList(){
        return new ResponseEntity<>(new Gson().toJson(albumsService.getAlbumCards()), HttpStatus.OK);
    }

    @PostMapping("/add")
    public ResponseEntity<?> addAlbum(@RequestBody AlbumsEntity data) throws IOException {
        albumsService.albumsAdd(data,null);
        Integer album = vkService.createAlbum(data);
        albumsService.albumsAdd(data,album);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PatchMapping("/update/{token}")
    public ResponseEntity<?> patchAlbum(
                                        @PathVariable String token,
                                        @RequestBody AlbumRowRequestDTO data) throws IOException{
        System.out.println(data);
        var vk= vkService.updAlbum(String.valueOf(data.getAlbumVkId()),token, data);
        System.out.println(vk);
        System.out.println("в вк альбом обновился, id = " + data.getAlbumVkId());
        albumsService.albumsUpd(data, data.getAlbumId());
        System.out.println("в базе альбом обновился");
        return new ResponseEntity<>(HttpStatus.OK);
    }


}