package com.usareboot.back.operators;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.usareboot.back.client.VkApiCustomClient;
import com.usareboot.back.controllers.VK.ConfigureFeignUrlController;
import com.usareboot.back.models.AlbumsItemsDTO;
import com.usareboot.back.models.vk.VkAlbumItemResponse;
import com.usareboot.back.models.vk.VkAlbumResponse;
import com.usareboot.back.models.vk.VkPhotoSaveDTO;
import com.usareboot.back.models.vk.VkPostRequestDTO;
import com.vk.api.sdk.client.TransportClient;
import com.vk.api.sdk.client.VkApiClient;
import com.vk.api.sdk.client.actors.GroupActor;
import com.vk.api.sdk.client.actors.UserActor;
import com.vk.api.sdk.exceptions.ApiException;
import com.vk.api.sdk.exceptions.ClientException;
import com.vk.api.sdk.httpclient.HttpTransportClient;
import com.vk.api.sdk.objects.photos.responses.MessageUploadResponse;
import com.vk.api.sdk.objects.photos.responses.PhotoUploadResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;

@Slf4j
@Service
@RequiredArgsConstructor
public class VkOperator {
    @Value("${vk.api.version}")
    private String apiVersion;

    @Value("${vk.api.groupId}")
    private String groupId;

    @Value("${vk.api.redirectUri}")
    private String redirectUri;

    @Value("${vk.client.id}")
    private String clientId;

    @Value("${vk.client.standaloneId}")
    private String standaloneId;

    @Value("${vk.api.pathPhoto}")
    private String pathPhoto;

    @Value("${vk.api.base-url}")
    private String VK_API_URL;


    private final CommonOperator commonOperator;
    private final VkApiCustomClient vkApiCustomClient;
    private final ConfigureFeignUrlController configureFeignUrlController;
    private final RestTemplate restTemplate;
    private final GroupActor groupActor;
    private final UserActor userActor;


    public String getCommentPhotoVk(long photo_id) throws IOException {
        var accessToken = commonOperator.getTokenClient(standaloneId).orElse(null);
//        var accessToken = commonOperator.getServicePasskeyClient(standaloneId).orElse(null);
        final CloseableHttpClient httpclient = HttpClients.createDefault();
        final HttpPost httpPost = new HttpPost("https://api.vk.com/method/photos.getById");
        final List<NameValuePair> params = new ArrayList<>();
        params.add(new BasicNameValuePair("access_token", accessToken));
        params.add(new BasicNameValuePair("v", apiVersion));
        params.add(new BasicNameValuePair("photos", "-" + groupId + "_" + photo_id));
        params.add(new BasicNameValuePair("http.protocol.content-charset", "UTF-8"));
        httpPost.setEntity(new UrlEncodedFormEntity(params, "UTF-8"));
        System.out.println(httpPost);
        try (
                CloseableHttpResponse response2 = httpclient.execute(httpPost)
        ) {
            final HttpEntity entity2 = response2.getEntity();
            String tempString = EntityUtils.toString(entity2);
            ObjectMapper mapper = new ObjectMapper();
            System.out.println(tempString);
            VkAlbumItemResponse response = mapper.readValue(tempString, VkAlbumItemResponse.class);
            System.out.println("response: " + response);
            return response.getResponse().get(0).getSizes().get(3).getUrl();
        }
    }

    public String delAlbumInVk(long albumId) {
        var accessToken = commonOperator.getTokenClient(standaloneId).orElse(null);
        return vkApiCustomClient.deleteAlbum((int) albumId, Integer.parseInt(groupId), accessToken, apiVersion);
    }

    public String postInVk(VkPostRequestDTO vkPostRequestDTO, MultipartFile file, MultipartFile adFile1, MultipartFile adFile2, MultipartFile adFile3) {
        var accessToken = commonOperator.getTokenClient(standaloneId).orElse(null);
        var ownerId = "-" + groupId;
        return vkApiCustomClient.createPost(accessToken, ownerId, vkPostRequestDTO.getDescription(), "1", apiVersion);
    }

    public String getAllDesc(AlbumsItemsDTO albumsItemsDTO) {
        var allDesc = albumsItemsDTO.getAlbumItemName() + "\n" +
                albumsItemsDTO.getDescription() + "\n" +
                "цена: " + albumsItemsDTO.getAlbumItemCost().toString() + ", курс: " +
                albumsItemsDTO.getAlbumItemRate().toString() + "\n" +
                albumsItemsDTO.getItemUrl();
        return allDesc;
    }


    public String editPhotoInVk(String photo_id,
                                String caption) throws IOException {
        final String accessToken = commonOperator.getTokenClient(standaloneId).orElse(null);

        final CloseableHttpClient httpclient = HttpClients.createDefault();
        final HttpPost httpPost = new HttpPost("https://api.vk.com/method/photos.edit");
        final List<NameValuePair> params = new ArrayList<>();
        params.add(new BasicNameValuePair("access_token", accessToken));
        params.add(new BasicNameValuePair("v", apiVersion));
        params.add(new BasicNameValuePair("photo_id", String.valueOf(photo_id)));
        params.add(new BasicNameValuePair("owner_id", "-" + groupId));
        params.add(new BasicNameValuePair("caption", caption));
        params.add(new BasicNameValuePair("http.protocol.content-charset", "UTF-8"));
        httpPost.setEntity(new UrlEncodedFormEntity(params, "UTF-8"));
        System.out.println(httpPost);
        try (
                CloseableHttpResponse response2 = httpclient.execute(httpPost)
        ) {
            final HttpEntity entity2 = response2.getEntity();
            return EntityUtils.toString(entity2);
        }
    }


    public String getUrlPhotoInAlbumVk(long albumId) throws IOException {
        final String accessToken = commonOperator.getTokenClient(standaloneId).orElse(null);

        final CloseableHttpClient httpclient = HttpClients.createDefault();
        final HttpPost httpPost = new HttpPost("https://api.vk.com/method/photos.getUploadServer");
        final List<NameValuePair> params = new ArrayList<>();
        params.add(new BasicNameValuePair("access_token", accessToken));
        params.add(new BasicNameValuePair("v", apiVersion));
        params.add(new BasicNameValuePair("album_id", String.valueOf(albumId)));
        params.add(new BasicNameValuePair("group_id", groupId));
        httpPost.setEntity(new UrlEncodedFormEntity(params));
        System.out.println(httpPost);
        try (
                CloseableHttpResponse response2 = httpclient.execute(httpPost)
        ) {
//            final HttpEntity entity2 = response2.getEntity();
//            return EntityUtils.toString(entity2);
            final HttpEntity entity2 = response2.getEntity();
            String tempString = EntityUtils.toString(entity2);
            ObjectMapper mapper = new ObjectMapper();
            log.info("getUrlPhotoInAlbumVk tempString: {}", tempString);
            VkAlbumResponse userDtoList = mapper.readValue(tempString, VkAlbumResponse.class);
            return userDtoList.getResponse().getUpload_url();
        }
    }

    public String savePhotoInVk(String photos_list,
                                String album_id,
                                String server,
                                String hash) throws IOException {
        final String accessToken = commonOperator.getTokenClient(standaloneId).orElse(null);

        final CloseableHttpClient httpclient = HttpClients.createDefault();
        final HttpPost httpPost = new HttpPost("https://api.vk.com/method/photos.save");
        final List<NameValuePair> params = new ArrayList<>();
        params.add(new BasicNameValuePair("access_token", accessToken));
        params.add(new BasicNameValuePair("v", apiVersion));
        params.add(new BasicNameValuePair("album_id", String.valueOf(album_id)));
        params.add(new BasicNameValuePair("group_id", groupId));
        params.add(new BasicNameValuePair("photos_list", photos_list));
        params.add(new BasicNameValuePair("server", server));
        params.add(new BasicNameValuePair("hash", hash));

        httpPost.setEntity(new UrlEncodedFormEntity(params));
        log.debug("httpPost:{}", httpPost);
        try (
                CloseableHttpResponse response2 = httpclient.execute(httpPost)
        ) {
            final HttpEntity entity2 = response2.getEntity();
            String tempString = EntityUtils.toString(entity2);
            ObjectMapper mapper = new ObjectMapper();
            VkAlbumItemResponse response = mapper.readValue(tempString, VkAlbumItemResponse.class);
//            log.debug("response: {}", response);
            return response.getResponse().get(0).getId();
        }
    }

    public List<File> getFiles(List<MultipartFile> multipartFiles, List<Path> tempFiles) throws IOException {
        log.info("Создаем временные файлы");
        for (MultipartFile multipartFile : multipartFiles) {
            try {
                Path tempFile = Files.createTempFile("vk_photo_", ".jpg");
                multipartFile.transferTo(tempFile);
                tempFiles.add(tempFile);
            } catch (Exception e) {
                log.error("Файл не загружен");
            }
        }
        log.info("Конвертируем в File для совместимости");
        return tempFiles.stream()
                .map(Path::toFile)
                .toList();
    }


    /**
     * Загружает фотки рекламного поста для отправки в чат
     */
    public List<String> uploadPhotoForAlbum(List<MultipartFile> multipartFiles, int albumId)
            throws ClientException, ApiException, IOException {
        var accessToken = commonOperator.getTokenClient(standaloneId).orElse("");

        TransportClient transportClient = new HttpTransportClient();
        VkApiClient vk = new VkApiClient(transportClient);
        UserActor actor = new UserActor(Integer.valueOf(standaloneId), accessToken);

        log.info("Получаем URL для загрузки");
//        var photoUploadVk = getUrlPhotoInAlbumVk(albumId);

        var uploadUrl = vk.photos().getUploadServer(actor)
                .albumId(albumId)
                .groupId(Integer.valueOf(groupId))
                .execute()
                .getUploadUrl()
                .toString();
        List<String> photoAttachments = new ArrayList<>();

        List<Path> tempFiles = new ArrayList<>();
        List<File> photoFiles;
        try {
            photoFiles = getFiles(multipartFiles, tempFiles);
//            for (var photoFile : multipartFiles) {
            for (File photoFile : photoFiles) {
                log.info("Загружаем файл");
//                var vkPhotoList = configureFeignUrlController.uploadPhotoInVk(photoUploadVk, photoFile);

                PhotoUploadResponse uploadResponse = vk.upload()
                        .photo(uploadUrl, photoFile)
                        .execute();


                log.info("Сохранение фотографий после загрузки");
                var photos = vk.photos().save(actor)
                        .albumId(albumId)
                        .server(uploadResponse.getServer())
                        .hash(uploadResponse.getHash())
                        .photosList(uploadResponse.getPhotosList())
                        .groupId(Integer.valueOf(groupId))
                        .execute();
//                var photo = savePhotoInVk(vkPhotoList.getPhotos_list(), String.valueOf(albumId), String.valueOf(vkPhotoList.getServer()), vkPhotoList.getHash());

                var photo = photos.get(0);
                var photoId = photo.getId().toString();
                log.info("photoId: {}", photoId);
                log.info("photo.getOwnerId(): {}", photo.getOwnerId());

//                photoAttachments.add("-"+groupId + "_" + photo);
                photoAttachments.add(photo.getOwnerId() + "_" + photoId);
                log.info("photoAttachments: {}", photoAttachments);
            }
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
        return photoAttachments;
    }

    /**
     * Загружает фотки рекламного поста для отправки в чат
     */
    public List<String> uploadPhotoForChat(List<MultipartFile> multipartFiles)
            throws ClientException, ApiException, IOException {
        var accessToken = commonOperator.getTokenClient(standaloneId).orElse("");

        TransportClient transportClient = new HttpTransportClient();
        VkApiClient vk = new VkApiClient(transportClient);
        UserActor actor = new UserActor(Integer.valueOf(standaloneId), accessToken);
//        UserActor actor = new UserActor(Integer.valueOf(groupId), accessToken);

        log.info("Загрузка фотографий на сервер VK");
        var uploadUrl = getMessagesUploadServer();
       /* var uploadUrl = vk.photos().getMessagesUploadServer(actor)
                .execute()
                .setGroupId(Integer.valueOf(groupId))
                .getUploadUrl();*/

        List<String> photoAttachments = new ArrayList<>();

        List<Path> tempFiles = new ArrayList<>();
        List<File> photoFiles;
        try {
            photoFiles = getFiles(multipartFiles, tempFiles);
            for (File photoFile : photoFiles) {
                MessageUploadResponse uploadResponse = vk.upload()
                        .photoMessage(uploadUrl.toString(), photoFile)
                        .execute();

                log.info("Сохранение фотографий после загрузки");
                var photos = vk.photos().saveMessagesPhoto(actor, uploadResponse.getPhoto())
                        .server(uploadResponse.getServer())
                        .hash(uploadResponse.getHash())
                        .execute();

                var photo = photos.get(0);
                var photoId = photo.getId().toString();
                log.info("photoId: {}", photoId);
                log.info("photo.getOwnerId(): {}", photo.getOwnerId());

                photoAttachments.add(photo.getOwnerId() + "_" + photo.getId());
            }
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
        return photoAttachments;
    }

    public String getMessagesUploadServer() {
        String url = VK_API_URL + "/photos.getMessagesUploadServer";
        var accessToken = commonOperator.getTokenClient(standaloneId).orElse("");

        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(url)
                .queryParam("group_id", groupId)
//                .queryParam("user_id", standaloneId)
                .queryParam("access_token", accessToken)
                .queryParam("v", apiVersion);

        ResponseEntity<Map> response = restTemplate.getForEntity(
                builder.toUriString(),
                Map.class
        );

        return (String) ((Map<?, ?>) Objects.requireNonNull(response.getBody()).get("response")).get("upload_url");
    }
    /**
     * Отправляет фото из альбома в чат
     *
     * @param chatId   ID беседы (положительное число)
     * @param photoIds ID фото в формате "ownerId_photoId"
     * @param message  Текст сообщения
     */
    public void sendPhotoToChat(int chatId, List<String> photoIds, String message)
            throws ClientException, ApiException {
        var accessToken = commonOperator.getTokenClient(standaloneId).orElse("");

        TransportClient transportClient = new HttpTransportClient();
        VkApiClient vk = new VkApiClient(transportClient);
        UserActor actor = new UserActor(Integer.valueOf(standaloneId), accessToken);

        List<String> attachments = new ArrayList<>();
        for (var photoId : photoIds) {
            log.info("Формируем attachment");
            attachments.add("photo" + photoId);
        }
        String stringAttach = String.join(",", attachments);
        log.info("stringAttach: {}", stringAttach);
        // Отправляем сообщение
        vk.messages().send(groupActor)
                .chatId(chatId)
                .groupId(Integer.parseInt(groupId))
//                .userId(Integer.valueOf(standaloneId))
                .randomId(new Random().nextInt())
                .message(message)
                .attachment(stringAttach)
                .execute();
        log.info("Сообщение успешно отправлено");
    }

    public String getMessage(AlbumsItemsDTO albumsItemsDTO) {
        BigDecimal itemCost = BigDecimal.valueOf(0);
        if (albumsItemsDTO.getAlbumItemCost() != null && albumsItemsDTO.getAlbumItemRate() != null) {
//            itemCost = (int) (albumsItemsDTO.getAlbumItemCost() * albumsItemsDTO.getAlbumItemRate());
            itemCost = albumsItemsDTO.getAlbumItemCost().multiply(albumsItemsDTO.getAlbumItemRate())
                    .setScale(0, RoundingMode.CEILING);
        }
        var message = albumsItemsDTO.getAlbumItemName() + " "
                + albumsItemsDTO.getItemDescription() + "\n"
                + itemCost + "+вес" + "\n" +
                albumsItemsDTO.getVkPhotoPath();
        return message;
    }


    public int getOrCreateHiddenAlbum(String albumTitle)
            throws ClientException, ApiException {
        var accessToken = commonOperator.getTokenClient(standaloneId).orElse("");

        TransportClient transportClient = new HttpTransportClient();
        VkApiClient vk = new VkApiClient(transportClient);
        UserActor actor = new UserActor(Integer.valueOf(standaloneId), accessToken);

        // 1. Получаем все альбомы сообщества
        var albums = vk.photos().getAlbums(actor)
                .ownerId(-Integer.parseInt(groupId))  // Для групп используем отрицательный ID
                .needSystem(true)   // Включаем системные альбомы
                .execute()
                .getItems();

        // 2. Ищем скрытый альбом по названию
        for (var album : albums) {
            if (album.getTitle().equalsIgnoreCase(albumTitle)){
            // Проверяем параметры приватности
                return album.getId();  // Нашли подходящий скрытый альбом
            }
        }

        // 3. Если не нашли - создаем новый
        return createHiddenAlbum(albumTitle);
    }

    private int createHiddenAlbum(String title)
            throws ClientException, ApiException {
        var accessToken = commonOperator.getTokenClient(standaloneId).orElse("");

        TransportClient transportClient = new HttpTransportClient();
        VkApiClient vk = new VkApiClient(transportClient);
        UserActor actor = new UserActor(Integer.valueOf(standaloneId), accessToken);


        return vk.photos().createAlbum(actor, title)
                .privacyView("only_me")
                .privacyComment("only_me")
                .uploadByAdminsOnly(true)
                .commentsDisabled(true)
                .groupId(Integer.valueOf(groupId))
                .execute()
                .getId();
    }

    public String uploadToAlbum(int albumId, File photoFile)
            throws ClientException, ApiException, IOException {
        var accessToken = commonOperator.getTokenClient(standaloneId).orElse("");

        TransportClient transportClient = new HttpTransportClient();
        VkApiClient vk = new VkApiClient(transportClient);
        UserActor actor = new UserActor(Integer.valueOf(standaloneId), accessToken);


        // 1. Получаем URL для загрузки
        var uploadUrl = vk.photos().getUploadServer(actor)
                .albumId(albumId)
                .groupId(Integer.valueOf(groupId))
                .execute()
                .getUploadUrl()
                .toString();

        // 2. Загружаем файл
        PhotoUploadResponse uploadResponse = vk.upload()
                .photo(uploadUrl, photoFile)
                .execute();

        // 3. Сохраняем в альбом
        var photos = vk.photos().save(actor)
                .albumId(albumId)
                .server(uploadResponse.getServer())
                .hash(uploadResponse.getHash())
                .photosList(uploadResponse.getPhoto())
                .groupId(Integer.valueOf(groupId))
                .execute();

        var photo = photos.get(0);
        return "photo" + photo.getOwnerId() + "_" + photo.getId();
    }

}
