package com.usareboot.back.controllers;

import com.google.gson.Gson;
import com.usareboot.back.client.config.ConfigureFeignUrlController;
import com.usareboot.back.models.AlbumsItemsDTO;
import com.usareboot.back.services.AlbumsItemsDAO;
import com.usareboot.back.services.VkDAO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Map;

@RestController
@CrossOrigin(origins = "*")/*!!!!обязательно во все контроллеры вставлять!!*/
@RequestMapping("/api/usareboot/album/item")
@RequiredArgsConstructor
@Slf4j
public class AlbumsItemsController {
    @Autowired
    private AlbumsItemsDAO albumsItemsDAO;
    @Autowired
    private VkDAO vkDAO;
    @Autowired
    private ConfigureFeignUrlController configureFeignUrlController;
    @Autowired
    private Environment environment;


    @GetMapping("/list/{albumId}")
    public ResponseEntity<?> albumItemsList(@PathVariable long albumId) {
        return new ResponseEntity<>(new Gson().toJson(albumsItemsDAO.getAlbumsItems(albumId)), HttpStatus.OK);
    }

    @PostMapping(value = "/create/{albumId}"/*, produces=MediaType.APPLICATION_OCTET_STREAM_VALUE, consumes=MediaType.APPLICATION_OCTET_STREAM_VALUE*/ /*MediaType.MULTIPART_FORM_DATA_VALUE*//* MediaType.IMAGE_JPEG_VALUE*//*.ALL_VALUE*//*MediaType.IMAGE_GIF_VALUE*/)
    public void itemCreate(
            @PathVariable long albumId,
            @RequestBody AlbumsItemsDTO albumsItemsDTO,
            @RequestBody ByteArrayResource photo) {
        System.out.println(albumsItemsDTO);
    }

    @PostMapping(value = "/photo/upload/{token}/param", consumes = MediaType.MULTIPART_FORM_DATA_VALUE/*, consumes=MediaType.APPLICATION_OCTET_STREAM_VALUE*/ /*MediaType.MULTIPART_FORM_DATA_VALUE*//* MediaType.IMAGE_JPEG_VALUE*//*.ALL_VALUE*//*MediaType.IMAGE_GIF_VALUE*/)
    public ResponseEntity<Map<String, String>> itemPhotoUpload(
            @PathVariable String token,
            @RequestParam(name = "album") long albumId,
            @RequestPart(name = "file") MultipartFile file,
            @RequestPart(name = "data") String data) throws IOException {

        var albumsItemsDTO = vkDAO.saveFileInVk(albumId, file, data);
        return albumsItemsDAO.saveFile( file, albumsItemsDTO);
    }
}