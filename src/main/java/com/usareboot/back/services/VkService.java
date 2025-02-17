package com.usareboot.back.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.usareboot.back.client.config.ConfigureFeignUrlController;
import com.usareboot.back.entities.*;
import com.usareboot.back.entities.auth.UsersEntity;
import com.usareboot.back.models.AlbumRowRequestDTO;
import com.usareboot.back.models.AlbumsItemsDTO;
import com.usareboot.back.models.ParsedComment;
import com.usareboot.back.models.vk.VkAlbumItemResponse;
import com.usareboot.back.models.vk.VkAlbumResponse;
import com.usareboot.back.parser.CommentParser;
import com.usareboot.back.repositories.*;
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
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.file.Path;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;


@Service
@Slf4j
@RequiredArgsConstructor
public class VkService {

    @Value("${vk.api.version}")
    private String apiVersion;

    @Value("${vk.api.groupId}")
    private String groupId;

    @Value("${vk.api.redirectUri}")
    private String redirectUri;

    @Value("${vk.client.id}")
    private String clientId;

    @Value("${vk.client.secret}")
    private String clientSecret;

    //    @Value("${vk.api.token}")
    private String accessToken;

    @Value("${vk.api.pathPhoto}")
    private String pathPhoto;

    private final RestTemplate restTemplate;
    private final ConfigureFeignUrlController configureFeignUrlController;
    private final ItemsRepository itemsRepository;
    private final AlbumsItemsRepository albumsItemsRepository;
    private final OrdersRepository ordersRepository;
    private final UsersRepository usersRepository;
    private final ApiTokenRepository apiTokenRepository;

    private final ThreadLocal<String> threadLocal = new ThreadLocal<>();

    public Optional<String> getToken(String clientId) {
        return apiTokenRepository.findApiTokenEntityByVkClientId(Long.parseLong(clientId))
                .map(ApiTokenEntity::getToken);
    }

    public void saveAccessToken(String code) {
        String tokenUrl = UriComponentsBuilder.fromHttpUrl("https://oauth.vk.com/access_token")
                .queryParam("client_id", clientId)
                .queryParam("client_secret", clientSecret)
                .queryParam("redirect_uri", redirectUri)
                .queryParam("code", code)
                .build().toUriString();

        String response = restTemplate.getForObject(tokenUrl, String.class);
        System.out.println(response);
        assert response != null;
        log.info("response: {}", response);

        JsonObject json = JsonParser.parseString(response).getAsJsonObject();
        log.info("json: {}", json);
        String accessToken = json.get("access_token").getAsString();
        var expires_in = json.get("expires_in").getAsLong();
        var date = java.time.LocalDateTime.now();
        var dataTokenEnd = date.plusSeconds(expires_in);
        apiTokenRepository.findApiTokenEntityByVkClientId(Long.parseLong(clientId))
                .ifPresentOrElse(s -> {
//                    s.setToken(accessToken);
////                    s.setRefreshToken(refreshToken);
//                    s.setTokenStart(date);
//                    s.setTokenEnd(dataTokenEnd);
//                    apiTokenRepository.save(s);
                }, () -> {
                    var data = new ApiTokenEntity();
                    data.setVkClientId(Long.parseLong(clientId));
                    data.setToken(accessToken);
                    data.setTokenStart(date);
                    data.setTokenEnd(dataTokenEnd);
                    apiTokenRepository.save(data);
                });

    }

    @Async
    public Integer createAlbum(AlbumsEntity albumsEntity) throws IOException {
        var eventId = threadLocal.get();
        log.info("[Сценарий createAlbum][Шаг: Начало][EventID: {}]", eventId);
        accessToken = getToken(clientId).orElse(null);
        final CloseableHttpClient httpclient = HttpClients.createDefault();
        final HttpPost httpPost = new HttpPost("https://api.vk.com/method/photos.createAlbum");
        final List<NameValuePair> params = new ArrayList<>();
        params.add(new BasicNameValuePair("access_token", accessToken));
        params.add(new BasicNameValuePair("v", apiVersion));
        params.add(new BasicNameValuePair("title", albumsEntity.getAlbumName()));
        params.add(new BasicNameValuePair("group_id", groupId));
        params.add(new BasicNameValuePair("description", albumsEntity.getAlbumDesc()));
        params.add(new BasicNameValuePair("upload_by_admins_only", "0"/*фотографии могут добавлять все пользователи*/));
        params.add(new BasicNameValuePair("http.protocol.content-charset", "UTF-8"));
        httpPost.setEntity(new UrlEncodedFormEntity(params, "UTF-8"));
        try (
                CloseableHttpResponse response2 = httpclient.execute(httpPost)
        ) {
            HttpEntity entity2 = response2.getEntity();
            List<String> tempString = Collections.singletonList(EntityUtils.toString(entity2));
            ObjectMapper mapper = new ObjectMapper();
            log.debug("tempString: {}", tempString);
            List<VkAlbumResponse> userDtoList = tempString.stream().map(x -> {
                VkAlbumResponse userDto = null;
                try {
                    userDto = mapper.readValue(x, VkAlbumResponse.class);
                    log.debug("userDto.getResponse().getId(): {}", userDto.getResponse().getId());

                } catch (JsonProcessingException e) {
                    log.error("exception" + e);
                }
                return userDto;
            }).toList();
            Integer id = null;
            if (!userDtoList.isEmpty())
                id = userDtoList.get(0).getResponse().getId();
            log.debug("[Сценарий createAlbum][Шаг: Проверка serDtoList.size(): {} и id: {}][EventID: {}]", userDtoList.size(), id, eventId);
            log.info("[Сценарий createAlbum][Шаг: Финиш][EventID: {}]", eventId);
            return id;
        }
    }

    public String updAlbum(String vkId, String token, AlbumRowRequestDTO albumsEntity) throws IOException {
        accessToken = getToken(clientId).orElse(null);

        final CloseableHttpClient httpclient = HttpClients.createDefault();
        final HttpPost httpPost = new HttpPost("https://api.vk.com/method/photos.editAlbum");
        final List<NameValuePair> params = new ArrayList<>();
        params.add(new BasicNameValuePair("access_token", accessToken));
        params.add(new BasicNameValuePair("v", apiVersion));
        params.add(new BasicNameValuePair("album_id", vkId));
        params.add(new BasicNameValuePair("owner_id", "-" + groupId));
        System.out.println(albumsEntity.getAlbumDesc());
        params.add(new BasicNameValuePair("description", albumsEntity.getAlbumDesc()));
        params.add(new BasicNameValuePair("upload_by_admins_only", "1"/*фотографии могут добавлять все пользователи*/));
        params.add(new BasicNameValuePair("http.protocol.content-charset", "UTF-8"));
        httpPost.setEntity(new UrlEncodedFormEntity(params, "UTF-8"));
        try (
                CloseableHttpResponse response2 = httpclient.execute(httpPost)
        ) {
            final HttpEntity entity2 = response2.getEntity();
            return EntityUtils.toString(entity2);
        }

    }

    public String getUrlPhotoInAlbumVk(long albumId) throws IOException {
        accessToken = getToken(clientId).orElse(null);

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
            System.out.println(tempString);
            VkAlbumResponse userDtoList = mapper.readValue(tempString, VkAlbumResponse.class);
            return userDtoList.getResponse().getUpload_url();
        }
    }

    public String savePhotoInVk(String photos_list,
                                String album_id,
                                String server,
                                String hash,
                                String access_token) throws IOException {
        accessToken = getToken(clientId).orElse(null);

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
            return response.getResponse().get(0).getId();
        }
    }

    public String editPhotoInVk(String photo_id,

//                                        String access_token,
                                String caption) throws IOException {
        accessToken = getToken(clientId).orElse(null);

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

    public String getCallbackConfirmationCode() {
        String url = UriComponentsBuilder.fromHttpUrl("https://api.vk.com/method/groups.getCallbackConfirmationCode")
                .queryParam("group_id", groupId)
                .queryParam("access_token", accessToken)
                .queryParam("v", apiVersion)
                .build().toUriString();
        String response = restTemplate.getForObject(url, String.class);
        JsonObject json = JsonParser.parseString(response).getAsJsonObject();
        return json.getAsJsonObject("response").get("code").getAsString();
    }

//    @Async
    public AlbumsItemsDTO saveFileInVk(Long albumId, MultipartFile file, AlbumsItemsDTO albumsItemsDTO) throws IOException {
        accessToken = getToken(clientId).orElse(null);

        log.info("Редактирование комментария в вк");
//        Gson g = new Gson();
//        var albumsItemsDTO = g.fromJson(data, AlbumsItemsDTO.class);
        /*проверка данных: пришло фото или ссылка на фото*/
        if (file == null && (albumsItemsDTO.getPhotoUrl() == null || albumsItemsDTO.getPhotoUrl().isEmpty())) {
            log.error("Ошибка загрузки: нет ссылки на фотографию");
            throw new RuntimeException("Ошибка загрузки: нет ссылки на фотографию");
        }
        if (file == null)
            file = getMultipartFile(albumsItemsDTO);

        var photoUploadVk = getUrlPhotoInAlbumVk(albumId);
        log.info("Upload photo in vk");
        var vkPhotoList = configureFeignUrlController.uploadPhotoInVk(photoUploadVk, file);

        log.info("Save photo in vk");
        var photo = savePhotoInVk(
                vkPhotoList.getPhotos_list(),
                String.valueOf(albumId),
                String.valueOf(vkPhotoList.getServer()),
                vkPhotoList.getHash(),
                accessToken);

        var allDesc = albumsItemsDTO.getAlbumItemName() + "\n" +
                albumsItemsDTO.getDescription() + "\n" +
                "цена: " + albumsItemsDTO.getAlbumItemCost().toString() + ", курс: " +
                albumsItemsDTO.getAlbumItemRate().toString() + "\n" +
                albumsItemsDTO.getItemUrl();
        albumsItemsDTO.setDescription(allDesc);
        editPhotoInVk(photo, allDesc);
        log.info("В ВК фотография успешно загружена и добавлено описание");
        albumsItemsDTO.setVkItemId(Long.parseLong(photo));
        albumsItemsDTO.setVkPhotoPath("https://vk.com/photo-" + groupId + "_" + photo);

        Path filePath = Path.of(pathPhoto);
        log.info("Сохранение фото в БД");
        albumsItemsDTO.setPhotoPath(String.valueOf(filePath));
        return albumsItemsDTO;
    }

    public MultipartFile getMultipartFile(AlbumsItemsDTO albumsItemsDTO) throws IOException {
        // Загружаем InputStream из URL
        URL url = new URL(albumsItemsDTO.getPhotoUrl());
        InputStream inputStream = url.openStream();

        // Достаем имя файла из URL (например, "image.jpg")
        String fileName = albumsItemsDTO.getPhotoUrl().substring(albumsItemsDTO.getPhotoUrl().lastIndexOf("/") + 1);
        log.info("Фото по ссылке получено");

        // Создаем MultipartFile из InputStream
        return new MockMultipartFile(
                fileName,         // Имя файла
                fileName,         // Оригинальное имя файла
                "image/jpeg",     // MIME тип (укажите нужный тип, например, "image/png")
                inputStream       // Данные файла
        );
    }

    //    public String getComments(String postId) {
//        String url = UriComponentsBuilder.fromHttpUrl("https://api.vk.com/method/photos.getComments")
//                .queryParam("access_token", accessToken)
//                .queryParam("v", apiVersion)
//                .queryParam("photo_id", postId)
//                .queryParam("extended", 1)
//                .build().toUriString();
//
//        return restTemplate.getForObject(url, String.class);
//    }
//
    public JsonObject getUserName(int userId) {
        accessToken = getToken(clientId).orElse(null);

        String url = UriComponentsBuilder.fromHttpUrl("https://api.vk.com/method/users.get")
                .queryParam("user_ids", userId)
                .queryParam("access_token", accessToken)
                .queryParam("v", apiVersion)
                .build().toUriString();

        String response = restTemplate.getForObject(url, String.class);
        JsonObject json = JsonParser.parseString(response).getAsJsonObject();
        JsonObject user = json.getAsJsonArray("response").get(0).getAsJsonObject();
        return user;/*user.get("first_name").getAsString() + " " + user.get("last_name").getAsString();*/
    }


    public void saveCommentUser(JsonObject object) {
        accessToken = getToken(clientId).orElse(null);

        try {
            log.info("Сохранение комментария");

            long photoId = object.get("photo_id").getAsLong();
            var albumsItems = albumsItemsRepository.findFirstByVkItemId(photoId);
//            var albumId = listAlbumItem.stream()
//                    .filter(s -> s.getStatuses().getStatusId() == ALBUM_STATUS_OPEN)
//                    .findFirst()
//                    .map(AlbumsItemsEntity::getAlbum)
//                    .map(AlbumsEntity::getAlbumId)
//                    .orElseThrow(() -> new RuntimeException("не найден альбом, где хранится фото с комментарием"));

//            var album = albumsRepository.findAlbumsEntityByAlbumId(albumId);
//            var albumsItems = albumsItemsRepository.findFirstByVkItemIdAndAlbum(photoId, album);
            log.info("Сохранение комментария в itemsEntity: {}", albumsItems);
            saveInAlbumItem(albumsItems, object);

        } catch (Exception e) {
            throw new RuntimeException("Не удалось записать комментарий в базу\n" + e);
        }
    }

    private long getOrderId(JsonObject object, Long albumId) {
        accessToken = getToken(clientId).orElse(null);

        var fromId = object.get("from_id").getAsLong();
        //проверка на существующего пользователя в базе
        var user = usersRepository.getUsersEntityByVkId(fromId);
        if (user == null) {
            JsonObject userName = getUserName((int) fromId);
            user = new UsersEntity();
            user.setVkId(fromId);
            user.setiName(userName.get("first_name").getAsString());
            user.setfName(userName.get("last_name").getAsString());
            usersRepository.save(user);
            log.info("Новый пользователь успешно создан: {}", fromId);
        }
        OrdersEntity orders = ordersRepository.getOrdersEntityByClientIdAndAlbumId(user.getUserId(), albumId);
        long ordersId;
        if (orders == null) {
            orders = new OrdersEntity();
            orders.setClientId(user.getUserId());
            orders.setAlbumId(albumId);
//            orders.setOrderCost(1);
            orders.setStatusId(24L);
            log.info(String.valueOf(orders));
//            ordersRepository.save(orders);
//            ordersId = 0;
            ordersId = ordersRepository.save(orders).getOrderId();
        } else
            ordersId = orders.getOrderId();

        log.info("Сохранение комментария в orders прошло успешно");
        return ordersId;
    }

    private void saveInAlbumItem(AlbumsItemsEntity albumsItems, JsonObject object) {
        accessToken = getToken(clientId).orElse(null);

        try {
            var dateInSeconds = object.get("date").getAsLong();
            var commentText = object.get("text").getAsString();
            var itemName = albumsItems.getAlbumItemName();
            var itemUrl = albumsItems.getItemUrl();
            var itemColor = albumsItems.getItemColor();
            var itemSize = albumsItems.getItemSize();
            var photoId = object.get("photo_id").getAsLong();
            var photoUrl = albumsItems.getItemUrl();
//            var photoUrl = getCommentPhotoVk(photoId);

            var albumId = albumsItems.getAlbum().getAlbumId();
            var albumItemId = albumsItems.getAlbumItemId();
            var orderId = getOrderId(object, albumId);

            LocalDateTime commentDate = LocalDateTime.ofInstant(Instant.ofEpochSecond(dateInSeconds), ZoneId.systemDefault());// Преобразование даты в LocalDateTime
            CommentParser parser = new CommentParser();// Вызов парсера комментариев
            ParsedComment parsedComment = parser.parse(commentText);

            ItemsEntity itemsEntity = new ItemsEntity();
            itemsEntity.setAlbomItemId(albumItemId);
            itemsEntity.setComment(commentText);
            itemsEntity.setDateComment(commentDate);
            itemsEntity.setOrderId(orderId);
            itemsEntity.setItemName(itemName);
            itemsEntity.setVkUrl(photoUrl);
//        itemsEntity.setVkUrl("https://vk.com/photo-" + groupId + "_" + photoId);
            if (parsedComment.getSize() != null)
                itemsEntity.setItemSize(parsedComment.getSize());
            else
                itemsEntity.setItemSize(itemSize);
            if (parsedComment.getLink() != null)
                itemsEntity.setItemUrl(parsedComment.getLink());
            else
                itemsEntity.setItemUrl(itemUrl);
            if (parsedComment.getColor() != null)
                itemsEntity.setItemColor(parsedComment.getColor());
            else
                itemsEntity.setItemColor(itemColor);
            itemsEntity.setItemStatus(24L);

            if (parsedComment.getCount() != null)
                itemsEntity.setItemCount(Integer.valueOf(parsedComment.getCount()));

            itemsRepository.save(itemsEntity);
            log.info("Сохранение комментария в itemsEntity прошло успешно");
        } catch (Exception e) {
            log.error("Ошибка при сохранения комментария: ", e);
        }
    }

    public String getCommentPhotoVk(long photo_id) throws IOException {
        accessToken = getToken(clientId).orElse(null);

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
}



