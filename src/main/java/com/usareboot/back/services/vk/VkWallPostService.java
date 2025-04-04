package com.usareboot.back.services.vk;

import com.usareboot.back.models.AlbumsItemsDTO;
import com.usareboot.back.models.vk.VkPostRequestDTO;
import com.usareboot.back.operators.CommonOperator;
import com.usareboot.back.operators.VkOperator;
import com.vk.api.sdk.client.TransportClient;
import com.vk.api.sdk.client.VkApiClient;
import com.vk.api.sdk.client.actors.UserActor;
import com.vk.api.sdk.exceptions.ApiException;
import com.vk.api.sdk.exceptions.ClientException;
import com.vk.api.sdk.httpclient.HttpTransportClient;
import com.vk.api.sdk.objects.photos.responses.SaveWallPhotoResponse;
import com.vk.api.sdk.objects.photos.responses.WallUploadResponse;
import com.vk.api.sdk.objects.wall.responses.PostResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class VkWallPostService {

    @Value("${vk.client.standaloneId}")
    private String standaloneId;

    @Value("${vk.api.groupId}")
    private Integer groupId;

    @Value("${vk.api.chatId}")
    private Integer chatId;

    private final CommonOperator commonOperator;
    private final VkOperator vkOperator;

    @Async
    public void postToWallWithPhotos(AlbumsItemsDTO albumsItemsDTO, List<MultipartFile> multipartFiles, String mainPhotoId)
            throws ClientException, ApiException, IOException {
        String accessToken = commonOperator.getTokenClient(standaloneId).orElse("");

        log.info("Инициализация клиента VK API");
        TransportClient transportClient = HttpTransportClient.getInstance();
        VkApiClient vk = new VkApiClient(transportClient);
        UserActor actor = new UserActor(Integer.valueOf(standaloneId), accessToken);

        log.info("Загрузка фотографий на сервер VK");

        var uploadUrl = vk.photos().getWallUploadServer(actor)
                .groupId(groupId)
                .execute()
                .getUploadUrl().toString();
        List<String> photoAttachments = new ArrayList<>();
        PostResponse postResponse;

        photoAttachments.add("photo-" + groupId + "_" + mainPhotoId);

        List<Path> tempFiles = new ArrayList<>();
        try {
            List<File> photoFiles = vkOperator.getFiles(multipartFiles, tempFiles);

            for (File photoFile : photoFiles) {
                WallUploadResponse uploadResponse = vk.upload()
                        .photoWall(uploadUrl, photoFile)
                        .execute();

                log.info("Сохранение фотографий после загрузки");
                List<SaveWallPhotoResponse> savedPhotos = vk.photos().saveWallPhoto(actor, uploadResponse.getPhoto())
                        .groupId(groupId)
                        .server(uploadResponse.getServer())
                        .hash(uploadResponse.getHash())
                        .execute();

                var photo = savedPhotos.get(0);
//                log.info("photo: {}", photo);
                var photoId = photo.getId().toString();
                log.info("photoId: {}", photoId);

                photoAttachments.add("photo" + photo.getOwnerId() + "_" + photoId);

                // Ограничим 4 фотографиями
                if (photoAttachments.size() >= 4) {
                    break;
                }
            }
            var message = vkOperator.getMessageForPost(albumsItemsDTO);

           /* VkPostRequestDTO vkPostRequestDTO = VkPostRequestDTO.builder()
                    .itemUrl(albumsItemsDTO.getVkPhotoPath())
                    .albumName(albumsItemsDTO.getAlbumName())
                    .itemCost(itemCost)
                    .description(description)
                    .build();*/
            log.info("photoAttachments: {}", photoAttachments);
            // 4. Публикация поста с прикрепленными фотографиями
            postResponse = vk.wall().post(actor)
                    .ownerId(-groupId)
                    .fromGroup(true)
                    .message(message)
                    .attachments(photoAttachments)
                    .execute();
        } catch (IOException e) {
            throw new RuntimeException(e);
        } finally {
            // Автоматическое удаление
            tempFiles.forEach(path -> {
                try {
                    Files.deleteIfExists(path);
                } catch (IOException e) {
                    log.error("Ошибка удаления временного файла", e);
                }
            });
        }
        log.info("Пост успешно опубликован с ID: {}", postResponse.getPostId());
    }

    @Async
    public void sendPostToChat(AlbumsItemsDTO albumsItemsDTO, List<MultipartFile> multipartFiles, String mainPhotoId) {
        try {
            String mainPhotoVkId = "-" + groupId + "_" + mainPhotoId;
            log.info("Загружаем фотографии на сервер ВК");
            List<String> photos = vkOperator.uploadPhotoForChat(multipartFiles);

            log.info("Добавляем основное фото из альбома");
            photos.add(0, mainPhotoVkId);

            log.info("Формируем сообщение для сообщение");
            var message = vkOperator.getMessageForPost(albumsItemsDTO);

            log.info("Отправляем сообщение в чат");
            vkOperator.sendPhotoToChat(chatId, photos, message);
        } catch (Exception e) {
            log.error("Ошибка отправки сообщения в чат", e);
        }
    }
}
