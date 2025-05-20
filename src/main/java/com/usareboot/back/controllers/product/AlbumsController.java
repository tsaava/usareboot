package com.usareboot.back.controllers.product;

import com.google.gson.Gson;
import com.usareboot.back.persistence.usareboot.entities.AlbumsEntity;
import com.usareboot.back.models.AlbumRowRequestDTO;
import com.usareboot.back.services.AlbumsService;
import com.usareboot.back.services.vk.VkService;
import com.vk.api.sdk.exceptions.ApiException;
import com.vk.api.sdk.exceptions.ClientException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@CrossOrigin(origins = "*")/*!!!!обязательно во все контроллеры вставлять!!*/
@RequestMapping("/api/usareboot/albums")
@RequiredArgsConstructor
//@PreAuthorize("hasAnyAuthority('admin')")
@PreAuthorize("hasAnyRole('ADMIN', 'MANAGER', 'PROMOTION')")
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
                                            @DateTimeFormat(pattern = "dd.MM.yyyy") Date albumDate*/) {
        return new ResponseEntity<>(new Gson().toJson(albumsService.getListAlbums()), HttpStatus.OK);
    }

    @GetMapping("/card")
    public ResponseEntity<?> cardList() {
        return new ResponseEntity<>(new Gson().toJson(albumsService.getAlbumCards()), HttpStatus.OK);
    }

    @PostMapping("/add")
    public ResponseEntity<?> addAlbum(@RequestBody AlbumsEntity data) throws IOException, ClientException, ApiException {
//        albumsService.albumsAdd(data, null);
        Integer albumId = vkService.createAlbum(data);
        albumsService.albumsAdd(data, albumId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/delete/{albumId}")
    public ResponseEntity<?> delAlbum(@PathVariable Long albumId) throws IOException {
        if (albumId != null)
            albumsService.delAlbum(albumId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PatchMapping("/update")
    public ResponseEntity<?> patchAlbum(
            @RequestBody AlbumRowRequestDTO data) throws IOException, ClientException, ApiException {
        albumsService.updateAlbum(String.valueOf(data.getAlbumVkId()), data);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}