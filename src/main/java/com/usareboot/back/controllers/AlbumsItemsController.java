package com.usareboot.back.controllers;

import com.google.gson.Gson;
import com.usareboot.back.client.config.ConfigureFeignUrlController;
import com.usareboot.back.models.AlbumsItemsDTO;
import com.usareboot.back.services.AlbumsItemsDAO;
import com.usareboot.back.services.VkDAO;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.Map;
import java.util.Objects;

import static org.springframework.http.ResponseEntity.ok;

@RestController
@CrossOrigin(origins = "*")/*!!!!обязательно во все контроллеры вставлять!!*/
@RequestMapping("/api/usareboot/album/item")
@RequiredArgsConstructor

public class AlbumsItemsController {
    @Autowired
    private AlbumsItemsDAO albumsItemsDAO;
    @Autowired
    private VkDAO vkDAO;
    @Autowired
    private ConfigureFeignUrlController configureFeignUrlController;
    @Autowired
    private Environment environment;
    @Value("${vk.api.pathPhoto}")
    private String pathPhoto;

    @Value("${vk.api.groupId}")
    private String groupId;
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
//        byte[] bytes = photo.getByteArray();
//        scienceDAO.sciencePhotoUpd(id,format, bytes);
    }

    @PostMapping(value = "/photo/upload/{token}/param", consumes = MediaType.MULTIPART_FORM_DATA_VALUE/*, consumes=MediaType.APPLICATION_OCTET_STREAM_VALUE*/ /*MediaType.MULTIPART_FORM_DATA_VALUE*//* MediaType.IMAGE_JPEG_VALUE*//*.ALL_VALUE*//*MediaType.IMAGE_GIF_VALUE*/)
    public ResponseEntity<Map<String, String>> itemPhotoUpload(
            @PathVariable String token,
            @RequestParam(name = "album") long albumId,
            @RequestPart(name = "file") MultipartFile file,
            @RequestPart(name = "data") String data) throws IOException {

        var photoUploadVk = vkDAO.getUrlPhotoInAlbumVk(albumId, token);
        ////////////////////////////////////////////////////////////
        try {
//            File f = new ClassPathResource("").getFile();
//            final Path path = Paths.get(f.getAbsolutePath() + File.separator + "static" + File.separator + "image");
//            System.out.println(path);
//            if (!Files.exists(path)) {
//                Files.createDirectories(path);
//            }

            System.out.println("Upload photo in vk");
            var vkPhotoList = configureFeignUrlController.uploadPhotoInVk(photoUploadVk, file);

            System.out.println("Save photo in vk");
            var photo = vkDAO.savePhotoInVk(
                    vkPhotoList.getPhotos_list(),
                    String.valueOf(albumId),
                    String.valueOf(vkPhotoList.getServer()),
                    vkPhotoList.getHash(),
                    token);

            System.out.println("Edit photo in vk");
            Gson g = new Gson();
            var albumsItemsDTO = g.fromJson(data, AlbumsItemsDTO.class);
            var allDesc = albumsItemsDTO.getAlbumItemName() + "\n" +
                    albumsItemsDTO.getDescription() + "\n" +
                    "цена: " + albumsItemsDTO.getAlbumItemCost().toString() + ", курс: " +
                    albumsItemsDTO.getAlbumItemRate().toString() + "\n" +
                    albumsItemsDTO.getItemUrl();
            albumsItemsDTO.setDescription(allDesc);
            vkDAO.EditPhotoInVk(photo, allDesc);


            albumsItemsDTO.setVkItemId(Long.parseLong(photo));

            albumsItemsDTO.setVkPhotoPath("https://vk.com/photo-" + groupId + "_" + photo);

            Path filePath = Path.of(pathPhoto);

            Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);

            System.out.println("Save photo in bd");
            albumsItemsDTO.setPhotoPath(String.valueOf(filePath));
            System.out.println("albumsItemsDTO: " + albumsItemsDTO);
            albumsItemsDAO.saveAlbumItem(albumsItemsDTO);

            String fileUri = ServletUriComponentsBuilder.fromCurrentContextPath()
                    .path("/image/")
                    .path(Objects.requireNonNull(file.getOriginalFilename()))
                    .toUriString();

            var result = Map.of(
                    "filename", file.getOriginalFilename(),
                    "fileUri", fileUri
            );

            return ok().body(result);

        } catch (IOException e) {
            System.out.println(e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}