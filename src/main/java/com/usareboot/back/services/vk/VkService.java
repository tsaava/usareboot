package com.usareboot.back.services.vk;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.usareboot.back.client.VkIdClient;
import com.usareboot.back.controllers.VK.ConfigureFeignUrlController;
import com.usareboot.back.entities.*;
import com.usareboot.back.models.AlbumRowRequestDTO;
import com.usareboot.back.models.AlbumsItemsDTO;
import com.usareboot.back.models.vk.*;
import com.usareboot.back.operators.*;
import com.usareboot.back.repositories.*;
import com.vk.api.sdk.objects.messages.Keyboard;
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
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.nio.file.Path;
import java.util.*;
import java.util.concurrent.ThreadLocalRandom;


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

    @Value("${vk.api.redirectStandaloneUri}")
    private String redirectStandaloneUri;

    @Value("${vk.client.id}")
    private String clientId;

    @Value("${vk.client.standaloneId}")
    private String standaloneId;

    @Value("${vk.client.secret}")
    private String clientSecret;

    @Value("${vk.client.secretStandalone}")
    private String clientStandaloneSecret;

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
    //    private final KeyboardService keyboardService;
    private final KeyboardOperator keyboardOperator;
    private final BotVkOperator botVkOperator;
    //    private final VkBotService vkBotService;
    private final StateService stateService;
    private final OrderService orderService;
    private final ItemOperator itemOperator;
    private final VkOperator vkOperator;
    private final CommonOperator commonOperator;
    private final OrderOperator orderOperator;
    private final ObjectMapper mapper;
    private final VkIdClient vkIdClient;

    private final ThreadLocal<Integer> threadLocal = ThreadLocal.withInitial(() -> ThreadLocalRandom.current().nextInt(10000, 100000));
    public void saveAccessToken(VkOauth2Response response) {
        /*String tokenUrl = UriComponentsBuilder.fromHttpUrl("https://oauth.vk.com/access_token")
                .queryParam("client_id", standaloneId)
//                .queryParam("client_id", clientId)
                .queryParam("client_secret", clientStandaloneSecret)
//                .queryParam("client_secret", clientSecret)
                .queryParam("redirect_uri", redirectStandaloneUri)
//                .queryParam("redirect_uri", redirectUri)
                .queryParam("code", code)
                .build().toUriString();

        String response = restTemplate.getForObject(tokenUrl, String.class);
        assert response != null;
        log.info("response: {}", response);*/

//        JsonObject json = JsonParser.parseString(response).getAsJsonObject();
//        log.info("json: {}", json);
        String accessToken = response.getAccess_token();// json.get("access_token").getAsString();
        var expires_in = response.getExpires_in();// json.get("expires_in").getAsLong();
        var date =  java.time.LocalDateTime.now();
        var dataTokenEnd =date.plusSeconds(expires_in);
        apiTokenRepository.findApiTokenEntityByVkClientId(Long.parseLong(standaloneId/*clientId*/))
                .ifPresentOrElse(s -> {
                    s.setToken(accessToken);
//                    s.setRefreshToken(refreshToken);
                    s.setTokenStart(date);
                    s.setTokenEnd(dataTokenEnd);
                    apiTokenRepository.save(s);
                }, () -> {
                    var data = new ApiTokenEntity();
                    data.setVkClientId(Long.parseLong(standaloneId/*clientId*/));
                    data.setToken(accessToken);
                    data.setTokenStart(date);
                    data.setTokenEnd(dataTokenEnd);
                    apiTokenRepository.save(data);
                });

    }

    public String exchangeCodeForTokens(TokenRequest request) throws IOException {
        log.info("exchangeCodeForTokens: {}", request);
        VkOauth2 authorizationCode = VkOauth2.builder()
                .grant_type("authorization_code")
                .code(request.getCode())
                .code_verifier(request.getCode_verifier())
                .device_id(request.getDevice_id())
                .client_id(standaloneId)
                .client_secret(clientStandaloneSecret)
                .state(request.getState())
                .redirect_uri(redirectStandaloneUri)
                .build();

        /*MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
        body.add("grant_type", "authorization_code");
        body.add("code", authorizationCode.getCode());
        body.add("code_verifier", authorizationCode.getCode_verifier());
        body.add("client_id", authorizationCode.getClient_id());
        body.add("device_id",  authorizationCode.getDevice_id());
        body.add("client_secret", authorizationCode.getClient_secret());
        body.add("redirect_uri", redirectStandaloneUri);
        body.add("state", authorizationCode.getState());*/


       /* String response = vkIdClient.exchangeCodeForTokens(
                authorizationCode.getGrant_type(), // grant_type
                authorizationCode.getCode(), // code
                authorizationCode.getCode_verifier(), // code_verifier
                authorizationCode.getDevice_id(), // code_verifier
                authorizationCode.getClient_id(), // client_id
                authorizationCode.getClient_secret(), // client_secret
                authorizationCode.getRedirect_uri(), // redirect_uri
                authorizationCode.getState() // redirect_uri
        );
        log.info("response: {}", response);

        VkOauth2Response vkOauth2Response = mapper.readValue(response, VkOauth2Response.class);
        log.info("vkOauth2Response: {}",vkOauth2Response);
        return vkOauth2Response.getAccess_token();*/

        final CloseableHttpClient httpclient = HttpClients.createDefault();
        final HttpPost httpPost = new HttpPost("https://id.vk.com/oauth2/auth");

        // Формируем параметры запроса
        final List<NameValuePair> params = new ArrayList<>();
        params.add(new BasicNameValuePair("grant_type", "authorization_code"));
        params.add(new BasicNameValuePair("code", request.getCode()));
        params.add(new BasicNameValuePair("code_verifier", request.getCode_verifier()));
        params.add(new BasicNameValuePair("device_id", request.getDevice_id()));
        params.add(new BasicNameValuePair("client_id", standaloneId));
        params.add(new BasicNameValuePair("state", request.getState()));
        params.add(new BasicNameValuePair("redirect_uri", redirectStandaloneUri));
        params.add(new BasicNameValuePair("client_secret", clientStandaloneSecret));

        // Устанавливаем параметры в тело запроса
        httpPost.setEntity(new UrlEncodedFormEntity(params));

        // Выполняем запрос
        try (CloseableHttpResponse response2 = httpclient.execute(httpPost)) {
            final HttpEntity entity2 = response2.getEntity();
            String tempString = EntityUtils.toString(entity2);
            log.info("tempString: {}",tempString);

            VkOauth2Response vkOauth2Response = mapper.readValue(tempString, VkOauth2Response.class);
            log.info("vkOauth2Response: {}",vkOauth2Response);
            saveAccessToken(vkOauth2Response);
            return vkOauth2Response.getAccess_token();
//            return EntityUtils.toString(entity2);
        }
       /* return vkIdClient.exchangeCodeForTokens(authorizationCode.getGrant_type(),
                authorizationCode.getCode(),
                authorizationCode.getCode_verifier(),
                authorizationCode.getDevice_id(),
                authorizationCode.getClient_id(),
                authorizationCode.getClient_secret(),
                authorizationCode.getRedirect_uri(),
                authorizationCode.getState());*/
        /*// Отправляем запрос к VK ID
        RestTemplate restTemplate = new RestTemplate();
        return restTemplate.postForObject(url, body, String.class);*/
    }

    @Async
    public Integer createAlbum(AlbumsEntity albumsEntity) throws IOException {
        var eventId = threadLocal.get();
        log.info("[Сценарий createAlbum][Шаг: Начало][EventID: {}]", eventId);
        accessToken = commonOperator.getTokenClient(clientId).orElse(null);
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
        accessToken = commonOperator.getTokenGroup(clientId).orElse(null);

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
        accessToken = commonOperator.getTokenGroup(clientId).orElse(null);

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
        accessToken = commonOperator.getTokenGroup(clientId).orElse(null);

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
        accessToken = commonOperator.getTokenGroup(clientId).orElse(null);

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
        accessToken = commonOperator.getTokenGroup(clientId).orElse(null);

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


    public void saveCommentUser(JsonObject object) {
//        accessToken = commonOperator.getTokenGroup(groupId).orElse(null);
        try {
            log.info("Сохранение комментария");
            long photoId = object.get("photo_id").getAsLong();
            var albumsItems = albumsItemsRepository.findFirstByVkItemId(photoId);
            log.info("Сохранение комментария в itemsEntity: {}", albumsItems);
//            accessToken = commonOperator.getTokenClient(clientId).orElse(null);

            try {
                var dateInSeconds = object.get("date").getAsLong();
                var commentText = object.get("text").getAsString();
                var albumId = albumsItems.getAlbum().getAlbumId();
                var fromId = object.get("from_id").getAsLong();
                var orderId = orderOperator.getOrderId(fromId, albumId);

                itemOperator.saveItem(albumsItems, dateInSeconds, commentText, orderId, null);
            } catch (Exception e) {
                log.error("Ошибка при сохранения комментария: ", e);
            }

        } catch (Exception e) {
            throw new RuntimeException("Не удалось записать комментарий в базу\n" + e);
        }
    }

   /* public String getCommentPhotoVk(long photo_id) throws IOException {
//        accessToken = commonOperator.getToken(clientId).orElse(null);
//        return  vkOperator.getCommentPhotoVk(Long.parseLong(clientId), accessToken);
        *//*final CloseableHttpClient httpclient = HttpClients.createDefault();
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
        }*//*
    }*/

    @Async
    public void sendKeyboard(String object) throws JsonProcessingException {
        var eventId = threadLocal.get();
        log.info("[Сценарий sendKeyboard][Шаг: Начало][EventID: {}]", eventId);
        var message = mapper.readValue(object, VkMessageNew.class);
        log.info("message: {}", message);
        log.info("message.getObject(): {}", message.getObject());
        String textStart = Optional.ofNullable(message.getObject()).map(VkTypeObject::getMessage).map(VkPhotoObject::getText).orElse("");
        Integer userId = Optional.ofNullable(message.getObject()).map(VkTypeObject::getMessage).map(VkPhotoObject::getFrom_id).orElse(null);
        log.info("[Сценарий sendKeyboard][Шаг: Проверка входного сообщения. Сообщение: {}][EventID: {}]", textStart, eventId);
        if (textStart.equalsIgnoreCase("Начать")) {
            log.info("[Сценарий sendKeyboard][Шаг: Получение стартовой клавиатуры][EventID: {}]", eventId);
            Keyboard startKeyboard = keyboardOperator.getStartKeyboard();
            var text = "Нажмите на кнопку для получения меню бота";

            log.info("[Сценарий sendKeyboard][Шаг: Отправка сообщения в ВК][EventID: {}]", eventId);
            botVkOperator.sendMessageWithKeyboard(userId, startKeyboard, text);
        }
    }
}



