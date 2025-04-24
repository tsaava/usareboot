package com.usareboot.back.controllers.product;

import com.google.gson.Gson;
import com.usareboot.back.controllers.VK.ConfigureFeignUrlController;
import com.usareboot.back.exceptions.ImageProcessingException;
import com.usareboot.back.models.AlbumsItemsDTO;
import com.usareboot.back.other.VkImageConverter;
import com.usareboot.back.services.AlbumsItemsService;
import com.usareboot.back.services.vk.VkService;
import com.usareboot.back.services.vk.VkWallPostService;
import com.vk.api.sdk.exceptions.ApiException;
import com.vk.api.sdk.exceptions.ClientException;
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
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin(origins = "*")/*!!!!обязательно во все контроллеры вставлять!!*/
@RequestMapping("/api/usareboot/album/item")
@RequiredArgsConstructor
@Slf4j
public class AlbumsItemsController {
    @Autowired
    private AlbumsItemsService albumsItemsService;
    @Autowired
    private VkService vkService;
    @Autowired
    private VkWallPostService vkWallPostService;

    private final VkImageConverter vkImageConverter;


    @GetMapping("/list/{albumId}")
    public ResponseEntity<?> albumItemsList(@PathVariable long albumId) {
        return new ResponseEntity<>(new Gson().toJson(albumsItemsService.getAlbumsItems(albumId)), HttpStatus.OK);
    }

    @PostMapping(value = "/photo/upload/param", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Map<String, String>> itemPhotoUpload (
            @RequestParam(name = "album") long albumId,
            @RequestPart(name = "file", required = false) MultipartFile file,
            @RequestPart(name = "adFile1", required = false) MultipartFile adFile1,
            @RequestPart(name = "adFile2", required = false) MultipartFile adFile2,
            @RequestPart(name = "adFile3", required = false) MultipartFile adFile3,
            @RequestPart(name = "data") String data) throws IOException, ClientException, ApiException {

        Gson g = new Gson();
        var dto = g.fromJson(data, AlbumsItemsDTO.class);
        MultipartFile vkFile, vkAdFile1 = null, vkAdFile2 = null, vkAdFile3 = null;
        if (file == null) {
            file = vkService.getMultipartFile(dto, "file");
        }
//        vkFile = vkImageConverter.convertToSupportedFormat(file);
//        log.debug("vkFile:{}", vkFile);
        try {
            if (adFile1 == null) {
                adFile1 = vkService.getMultipartFile(dto, "adFile1");
            }
            vkAdFile1 = vkImageConverter.convertToSupportedFormat(adFile1);
        } catch (Exception e) {
        }
        try {
            if (adFile2 == null) {
                adFile2 = vkService.getMultipartFile(dto, "adFile2");
            }
            vkAdFile2 = vkImageConverter.convertToSupportedFormat(adFile2);
        } catch (Exception e) {
        }
        try {
            if (adFile3 == null) {
                adFile3 = vkService.getMultipartFile(dto, "adFile3");
            }
            vkAdFile3 = vkImageConverter.convertToSupportedFormat(adFile3);
        } catch (Exception e) {
        }
        List<MultipartFile> photos = new ArrayList<>();
        if (vkAdFile1 != null)
            photos.add(vkAdFile1);
        if (vkAdFile2 != null)
            photos.add(vkAdFile2);
        if (vkAdFile3 != null)
            photos.add(vkAdFile3);

        String photoId = albumsItemsService.saveFile(albumId, file, dto);
        vkWallPostService.postToWallWithPhotos(dto, photos, photoId);
        vkWallPostService.sendPostToChat(dto, photos, photoId);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}