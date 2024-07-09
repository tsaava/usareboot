package com.usareboot.back.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.usareboot.back.client.config.ConfigureFeignUrlController;
import com.usareboot.back.entities.AlbumsItemsEntity;
import com.usareboot.back.entities.ItemsEntity;
import com.usareboot.back.entities.OrdersEntity;
import com.usareboot.back.entities.auth.UsersEntity;
import com.usareboot.back.models.*;
import com.usareboot.back.entities.AlbumsEntity;
import com.usareboot.back.parser.CommentParser;
import com.usareboot.back.repositories.AlbumsItemsRepository;
import com.usareboot.back.repositories.ItemsRepository;
import com.usareboot.back.repositories.OrdersRepository;
import com.usareboot.back.repositories.UsersRepository;
import com.vk.api.sdk.client.TransportClient;
import com.vk.api.sdk.client.VkApiClient;
import com.vk.api.sdk.httpclient.HttpTransportClient;
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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.*;
import java.nio.file.Path;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import static com.usareboot.back.models.constant.Constant.ALBUM_STATUS_OPEN;

@Service
@Slf4j
public class VkDAO {

    private final RestTemplate restTemplate;

    public VkDAO(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

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

    @Value("${vk.api.token}")
    private String accessToken;
    @Value("${vk.api.pathPhoto}")
    private String pathPhoto;
    @Autowired
    private ConfigureFeignUrlController configureFeignUrlController;
    @Autowired
    private ItemsRepository itemsRepository;
    @Autowired
    private AlbumsItemsRepository albumsItemsRepository;
    @Autowired
    private OrdersRepository ordersRepository;
    @Autowired
    private UsersRepository usersRepository;

    public String vkAuth(String silent_token, String uuid) throws IOException {
        TransportClient transportClient = new HttpTransportClient();
        VkApiClient vk = new VkApiClient(transportClient);
        final CloseableHttpClient httpclient = HttpClients.createDefault();

        final HttpPost httpPost = new HttpPost("https://api.vk.com/method/auth.exchangeSilentAuthToken");
        final List<NameValuePair> params = new ArrayList<>();
        params.add(new BasicNameValuePair("v", apiVersion));
        params.add(new BasicNameValuePair("token", silent_token));
        params.add(new BasicNameValuePair("access_token", "b3c038ccb3c038ccb3c038ccb3b0d6c3e6bb3c0b3c038ccd66cc4d5a0b76abdf85929d2"));
        params.add(new BasicNameValuePair("uuid", uuid));
        httpPost.setEntity(new UrlEncodedFormEntity(params));
        System.out.println(httpPost);

        try (
                CloseableHttpResponse response2 = httpclient.execute(httpPost)
        ) {
            final HttpEntity entity2 = response2.getEntity();
            return EntityUtils.toString(entity2);
        }
    }


    public String vkOAuth(String code) throws IOException {
        final CloseableHttpClient httpclient = HttpClients.createDefault();
        final HttpPost httpPost = new HttpPost("https://oauth.vk.com/access_token");
        final List<NameValuePair> params = new ArrayList<>();
        params.add(new BasicNameValuePair("client_id", "51727454"));
        params.add(new BasicNameValuePair("client_secret", "Lp8in0hLVi4I7SR7VMKz"));
        params.add(new BasicNameValuePair("redirect_uri", redirectUri));
        params.add(new BasicNameValuePair("code", code));
        httpPost.setEntity(new UrlEncodedFormEntity(params));
        System.out.println(httpPost);
        try (
                CloseableHttpResponse response2 = httpclient.execute(httpPost)
        ) {
//            saveAccessTokenToProperties(accessToken);
            final HttpEntity entity2 = response2.getEntity();
            return EntityUtils.toString(entity2);
        }

    }


    public Integer createAlbum(AlbumsEntity albumsEntity) throws IOException {
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
            System.out.println(tempString);
            List<VkAlbumResponse> userDtoList = tempString.stream().map(x -> {
                VkAlbumResponse userDto = null;
                try {
                    userDto = mapper.readValue(x, VkAlbumResponse.class);
                    System.out.print("userDto:");

                    System.out.println(userDto.getResponse().getId());

                } catch (JsonProcessingException e) {
                    System.out.println("exception" + e);
                }

                return userDto;
            }).collect(Collectors.toList());

            return userDtoList.get(0).getResponse().getId();

        }
    }

    public String updAlbum(String vkId, String token, AlbumRowRequestDTO albumsEntity) throws IOException {
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

    public AlbumsItemsDTO saveFileInVk(Long albumId, MultipartFile file, String data) throws IOException {
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

        log.info("Редактирование комментария в вк");
        Gson g = new Gson();
        var albumsItemsDTO = g.fromJson(data, AlbumsItemsDTO.class);
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
        try {
            log.info("Сохранение комментария");

            long photoId = object.get("photo_id").getAsLong();
            var listAlbumItem = albumsItemsRepository.findAllByVkItemId(photoId);
            var albumId = listAlbumItem.stream()
                    .filter(s -> s.getStatuses().getStatusId() == ALBUM_STATUS_OPEN)
                    .findFirst()
                    .map(AlbumsItemsEntity::getAlbum)
                    .map(AlbumsEntity::getAlbumId)
                    .orElseThrow(() -> new RuntimeException("не найден альбом, где хранится фото с комментарием"));

            log.info("Сохранение комментария в orders");
            long orderId = getOrderId(object, albumId);

            log.info("Сохранение комментария в itemsEntity");
            var firstByVkItemId = albumsItemsRepository.findFirstByVkItemId(photoId);
            var itemName = firstByVkItemId.getAlbumItemName();
            saveInAlbumItem(object, orderId, photoId, itemName);

        } catch (Exception e) {
            throw new RuntimeException("Не удалось записать комментарий в базу\n" + e);
        }
    }

    private long getOrderId(JsonObject object, Long albumId) {
        var fromId = object.get("from_id").getAsLong();
        //проверка на существующего пользователя в базе
        var user = usersRepository.getUsersEntityByVkId(fromId);
        if (user == null) {
            JsonObject userName = getUserName((int) fromId);
            user = new UsersEntity();
            user.setVkId(fromId);
            user.setfName(userName.get("first_name").getAsString());
            user.setiName(userName.get("last_name").getAsString());
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
            ordersRepository.save(orders);
            ordersId = 0;
//            ordersId = ordersRepository.save(orders).getOrderId();
        } else
            ordersId = orders.getOrderId();

        log.info("Сохранение комментария в orders прошло успешно");
        return ordersId;
    }

    private void saveInAlbumItem(JsonObject object, long orderId, long photoId, String photoComment) {
        long dateInSeconds = object.get("date").getAsLong();
        String commentText = object.get("text").getAsString();
        LocalDateTime commentDate = LocalDateTime.ofInstant(Instant.ofEpochSecond(dateInSeconds), ZoneId.systemDefault());// Преобразование даты в LocalDateTime
        CommentParser parser = new CommentParser();// Вызов парсера комментариев
        ParsedComment parsedComment = parser.parse(commentText);

        ItemsEntity itemsEntity = new ItemsEntity();
        itemsEntity.setComment(commentText);
        itemsEntity.setDateComment(commentDate);
        itemsEntity.setOrderId(orderId);
        itemsEntity.setItemName(photoComment);
        itemsEntity.setVkUrl("https://vk.com/photo-" + groupId + "_" + photoId);
        itemsEntity.setItemSize(parsedComment.getSize());
        itemsEntity.setItemUrl(parsedComment.getLink());
        itemsEntity.setItemColor(parsedComment.getColor());
        log.info("parsedComment.getColor(), {}", parsedComment.getColor());
        itemsEntity.setItemStatus(24L);

        if (parsedComment.getCount() != null)
            itemsEntity.setItemCount(Integer.valueOf(parsedComment.getCount()));

        itemsRepository.save(itemsEntity);
        log.info("Сохранение комментария в itemsEntity прошло успешно");
    }

    /*public String getCommentPhotoVk(String photo_id) throws IOException {
        final CloseableHttpClient httpclient = HttpClients.createDefault();
        final HttpPost httpPost = new HttpPost("https://api.vk.com/method/photos.getById");
        final List<NameValuePair> params = new ArrayList<>();
        params.add(new BasicNameValuePair("access_token", accessToken));
        params.add(new BasicNameValuePair("v", apiVersion));
        params.add(new BasicNameValuePair("photos", "-" + groupId+"_"+photo_id));
        params.add(new BasicNameValuePair("http.protocol.content-charset", "UTF-8"));
        httpPost.setEntity(new UrlEncodedFormEntity(params, "UTF-8"));
        System.out.println(httpPost);
        try (
                CloseableHttpResponse response2 = httpclient.execute(httpPost)
        ) {
            final HttpEntity entity2 = response2.getEntity();
            return EntityUtils.toString(entity2);
        }
    }*/
}



