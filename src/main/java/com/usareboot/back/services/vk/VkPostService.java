package com.usareboot.back.services.vk;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.usareboot.back.client.VkApiCustomClient;
import com.usareboot.back.controllers.VK.ConfigureFeignUrlController;
import com.usareboot.back.models.vk.VkPostRequestDTO;
import com.usareboot.back.operators.CommonOperator;
import com.usareboot.back.other.ByteArrayToMultipartFile;
import com.usareboot.back.other.ImageCompressor;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class VkPostService {
    private final VkApiCustomClient vkApiCustomClient;
//    private final VkUploadClient vkUploadClient;
    private final ObjectMapper objectMapper;
    private final CommonOperator commonOperator;
    private final ConfigureFeignUrlController configureFeignUrlController;


    @Value("${vk.api.version}")
    private String version;

    @Value("${vk.api.groupId}")
    private String groupId;

    @Value("${vk.client.standaloneId}")
    private String standaloneId;
    @SneakyThrows
    public String postWithPhotos(VkPostRequestDTO vkPostRequestDTO, List<MultipartFile> photos) {
        final String accessToken = commonOperator.getTokenClient(standaloneId).orElse(null);

        log.info("Получаем URL для загрузки");
        // 1. Получаем URL для загрузки
        String uploadServerResponse = vkApiCustomClient.getWallUploadServer(
                groupId, accessToken, version);

        /*JsonNode uploadServer = objectMapper.readTree(uploadServerResponse)
                .path("response")
                .path("upload_url");*/

        log.info("Загружаем и сохраняем фото (максимум 4)");
        List<String> attachments = photos.stream()
                .limit(4)
                .map(image->uploadAndSavePhoto(image,uploadServerResponse))
//                .map(this::uploadAndSavePhoto)
                .collect(Collectors.toList());

        log.info("Публикуем пост");
        String response = vkApiCustomClient.wallPost(
                "-" + groupId, // owner_id для группы
                1,             // from_group
                vkPostRequestDTO.getDescription(),
                String.join(",", attachments),
                accessToken,
                version);

        return "Пост опубликован: " + response;
    }

    @SneakyThrows
    private String uploadAndSavePhoto(MultipartFile photo, String uploadServer) {
        final String accessToken = commonOperator.getTokenClient(standaloneId).orElse(null);

        log.info("Загружаем фото");
        byte[] photoBytes;
        if (ImageCompressor.needsCompression(photo)) {
            photoBytes = ImageCompressor.compressImage(photo);
            log.info("Изображение сжато: {} -> {} байт",
                    photo.getSize(), photoBytes.length);
        } else {
            photoBytes = photo.getBytes();
        }
        log.info("после сжатия");

        MultipartFile compressedFile = new ByteArrayToMultipartFile(
                photoBytes,
                "file",
                "compressed_image.jpg" ,
                "image/jpeg"
        );
        log.info("compressedFile");

//        String uploadResponse = vkUploadClient.uploadPhoto(compressedFile);
        var vkPhotoList = configureFeignUrlController.uploadPhotoInVk(uploadServer, compressedFile);

//        JsonNode uploadData = objectMapper.readTree(uploadServer);

        log.info("Сохраняем фото на сервере VK");
        String saveResponse = vkApiCustomClient.saveWallPhoto(
                groupId,
                accessToken,
                version,
                vkPhotoList.getPhotos_list(),
                String.valueOf(vkPhotoList.getServer()),
                vkPhotoList.getHash());

        log.info("saveResponse: {}", saveResponse);

        JsonNode photoData = objectMapper.readTree(saveResponse)
                .path("response")
                .get(0);

        log.info("photoData: {}", photoData);

        return "photo" + photoData.path("owner_id").asText() +
                "_" + photoData.path("id").asText();
    }
}
