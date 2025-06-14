package com.usareboot.back.services.vk;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.JsonObject;
import com.usareboot.back.models.AlbumRowRequestDTO;
import com.usareboot.back.models.AlbumsItemsDTO;
import com.usareboot.back.models.vk.*;
import com.usareboot.back.operators.*;
import com.usareboot.back.persistence.usareboot.entities.AlbumMappingDictionaryEntity;
import com.usareboot.back.persistence.usareboot.entities.AlbumsEntity;
import com.usareboot.back.persistence.usareboot.entities.ApiTokenEntity;
import com.usareboot.back.persistence.usareboot.repository.AlbumMappingDictionaryRepository;
import com.usareboot.back.persistence.usareboot.repository.AlbumsItemsRepository;
import com.usareboot.back.persistence.usareboot.repository.AlbumsRepository;
import com.usareboot.back.persistence.usareboot.repository.ApiTokenRepository;
import com.vk.api.sdk.exceptions.ApiException;
import com.vk.api.sdk.exceptions.ClientException;
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
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ThreadLocalRandom;


@Service
@Slf4j
@RequiredArgsConstructor
public class VkService {

    @Value("${vk.api.version}")
    private String apiVersion;

    @Value("${vk.api.groupId}")
    private String groupId;

    @Value("${vk.api.redirectStandaloneUri}")
    private String redirectStandaloneUri;

    @Value("${vk.client.id}")
    private String clientId;

    @Value("${vk.client.standaloneId}")
    private String standaloneId;

    @Value("${vk.client.secretStandalone}")
    private String clientStandaloneSecret;

    private final AlbumsItemsRepository albumsItemsRepository;
    private final AlbumsRepository albumsRepository;
    private final AlbumMappingDictionaryRepository albumMappingDictionaryRepository;
    private final ApiTokenRepository apiTokenRepository;
    private final KeyboardOperator keyboardOperator;
    private final BotVkOperator botVkOperator;
    private final ItemOperator itemOperator;
    private final VkOperator vkOperator;
    private final CommonOperator commonOperator;
    private final OrderOperator orderOperator;
    private final ObjectMapper mapper;

    private final ThreadLocal<Integer> threadLocal = ThreadLocal.withInitial(() -> ThreadLocalRandom.current().nextInt(10000, 100000));

    public void saveAccessToken(VkOauth2Response response) {
        String accessToken = response.getAccess_token();
        var expires_in = response.getExpires_in();
        var refreshToken = response.getRefresh_token();
        var date = java.time.LocalDateTime.now();
        var dataTokenEnd = date.plusSeconds(expires_in);
        apiTokenRepository.findApiTokenEntityByVkClientId(Long.parseLong(standaloneId)).ifPresentOrElse(s -> {
            s.setToken(accessToken);
            s.setRefreshToken(refreshToken);
            s.setTokenStart(date);
            s.setTokenEnd(dataTokenEnd);
            apiTokenRepository.save(s);
        }, () -> {
            var data = new ApiTokenEntity();
            data.setVkClientId(Long.parseLong(standaloneId));
            data.setToken(accessToken);
            data.setTokenStart(date);
            data.setRefreshToken(refreshToken);
            data.setTokenEnd(dataTokenEnd);
            apiTokenRepository.save(data);
        });
        log.debug("новый токен сохранен в базу");
    }

    public String exchangeCodeForTokens(TokenRequest request) throws IOException {
        log.info("exchangeCodeForTokens: {}", request);
        VkOauth2 authorizationCode = VkOauth2.builder().grant_type("authorization_code").code(request.getCode()).code_verifier(request.getCode_verifier()).device_id(request.getDevice_id()).client_id(standaloneId).client_secret(clientStandaloneSecret).state(request.getState()).redirect_uri(redirectStandaloneUri).build();

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
            log.info("tempString: {}", tempString);

            VkOauth2Response vkOauth2Response = mapper.readValue(tempString, VkOauth2Response.class);
            log.info("vkOauth2Response: {}", vkOauth2Response);
            saveAccessToken(vkOauth2Response);
            return vkOauth2Response.getAccess_token();
        }
    }

    public void exchangeRefreshTokens(TokenRequest request) throws IOException {
        log.info("exchangeRefreshTokens: {}", request);

        boolean expiredToken = commonOperator.isExpiredToken(standaloneId);
        if (expiredToken) {
            String refreshToken = commonOperator.getRefreshToken(standaloneId).orElse(null);
            final CloseableHttpClient httpclient = HttpClients.createDefault();
            final HttpPost httpPost = new HttpPost("https://id.vk.com/oauth2/auth");

            // Формируем параметры запроса
            final List<NameValuePair> params = new ArrayList<>();
            params.add(new BasicNameValuePair("grant_type", "refresh_token"));
            params.add(new BasicNameValuePair("refresh_token", refreshToken));
            params.add(new BasicNameValuePair("device_id", request.getDevice_id()));
            params.add(new BasicNameValuePair("client_id", standaloneId));
            params.add(new BasicNameValuePair("state", request.getState()));

            // Устанавливаем параметры в тело запроса
            httpPost.setEntity(new UrlEncodedFormEntity(params));

            // Выполняем запрос
            try (CloseableHttpResponse response2 = httpclient.execute(httpPost)) {
                final HttpEntity entity2 = response2.getEntity();
                String tempString = EntityUtils.toString(entity2);
                log.info("tempString: {}", tempString);

                VkOauth2Response vkOauth2Response = mapper.readValue(tempString, VkOauth2Response.class);
                log.debug("Сохранение рефрештокена");
                saveAccessToken(vkOauth2Response);
            }
        }
    }

    @Async
    public Integer createAlbum(AlbumsEntity albumsEntity) throws IOException {
        var eventId = threadLocal.get();
        log.info("[Сценарий createAlbum][Шаг: Начало][EventID: {}]", eventId);

        var description = (albumsEntity.getAlbumDesc() != null && !albumsEntity.getAlbumDesc().isEmpty() && !albumsEntity.getAlbumDesc().equals("null") ? albumsEntity.getAlbumDesc() + "\n" : "")
                + "Курс(ы) альбома: " + albumsEntity.getCourseAlbum();
//        log.info("[Сценарий createAlbum][Шаг: Определить есть ли в словаре данные по альбому][EventID: {}]", eventId);
        Optional<AlbumMappingDictionaryEntity> albumMappingDictionaryEntity = albumMappingDictionaryRepository.findFirstByLinkContains(Optional.ofNullable(albumsEntity).map(AlbumsEntity::getShopUrl).orElse(""));

        var accessToken = commonOperator.getTokenClient(standaloneId).orElse(null);
//        accessToken = commonOperator.getTokenGroup(groupId).orElse(null);
        final CloseableHttpClient httpclient = HttpClients.createDefault();
        final HttpPost httpPost = new HttpPost("https://api.vk.com/method/photos.createAlbum");
        final List<NameValuePair> params = new ArrayList<>();
        params.add(new BasicNameValuePair("access_token", accessToken));
        params.add(new BasicNameValuePair("v", apiVersion));
        params.add(new BasicNameValuePair("title", Optional.ofNullable(albumsEntity).map(AlbumsEntity::getAlbumName).orElse("")));
        params.add(new BasicNameValuePair("group_id", groupId));
        params.add(new BasicNameValuePair("description", description));
        params.add(new BasicNameValuePair("upload_by_admins_only", "0"/*фотографии могут добавлять все пользователи*/));
        params.add(new BasicNameValuePair("http.protocol.content-charset", "UTF-8"));
        httpPost.setEntity(new UrlEncodedFormEntity(params, "UTF-8"));
        try (CloseableHttpResponse response2 = httpclient.execute(httpPost)) {
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
            Integer albumId = null;
            if (!userDtoList.isEmpty()) {
                albumId = userDtoList.get(0).getResponse().getId();
                log.debug("[Сценарий createAlbum][Шаг: Проверка userDtoList.size(): {} и albumId: {}][EventID: {}]", userDtoList.size(), albumId, eventId);
                try {
                    log.info("[Сценарий createAlbum][Шаг: Получение фото по ссылке из таблицы маппинга][EventID: {}]", eventId);
                    String coverPhotoUrl = albumMappingDictionaryEntity.map(AlbumMappingDictionaryEntity::getUrlCover).orElse("");
                    List<MultipartFile> multipartFileList = new ArrayList<>();
                    if (!coverPhotoUrl.isEmpty()) {
                        log.info("[Сценарий createAlbum][Шаг: Получение multipartFile по методу getAlbumCoverPhoto][EventID: {}]", eventId);
                        MultipartFile albumCoverPhoto = vkOperator.getAlbumCoverPhoto(coverPhotoUrl);
                        multipartFileList.add(albumCoverPhoto);
                    }
                    log.info("[Сценарий createAlbum][Шаг: Загрузка фото в альбом: {}][EventID: {}]", albumId, eventId);
                    String vkPhotoPatch = vkOperator.uploadPhotoForAlbum(multipartFileList, albumId).get(0);
                    int photoId = 0;
                    if (vkPhotoPatch != null && vkPhotoPatch.contains("_")) {
                        photoId = Integer.parseInt(vkPhotoPatch.split("_")[1]);
                    }
                    log.info("[Сценарий createAlbum][Шаг: Выбор обложки для альбома: {}][EventID: {}]", albumId, eventId);
                    vkOperator.photosMakeCover(photoId, albumId);

                    log.info("[Сценарий createAlbum][Шаг: ОБновление альбома чтобы не было галочки могут загружать только админы: {}][EventID: {}]", albumId, eventId);
                    vkOperator.editAlbum(albumId);
                } catch (Exception e) {
                    log.error("[Сценарий createAlbum][Шаг: Ошибка получения ссылки из бд на обложку альбома][e: {}]", e.getMessage());
                }
            }
            log.info("[Сценарий createAlbum][Шаг: Финиш][EventID: {}]", eventId);
            return albumId;
        }
    }

    public MultipartFile getMultipartFile(AlbumsItemsDTO albumsItemsDTO, String type) throws IOException {
        return vkOperator.getMultipartFile(albumsItemsDTO, type);
    }

    public void saveCommentUser(JsonObject object) {
        try {
            log.info("Сохранение комментария");
            long photoId = object.get("photo_id").getAsLong();
            var albumsItems = albumsItemsRepository.findFirstByVkItemId(photoId);
            log.info("Сохранение комментария в itemsEntity: {}", albumsItems);
            try {
                var dateInSeconds = object.get("date").getAsLong();
                var commentText = object.get("text").getAsString();
                var albumId = albumsItems.getAlbum().getAlbumId();
                var fromId = object.get("from_id").getAsLong();
                if (object.get("from_id").getAsString().contains(groupId)) {
                    log.debug("Комментарий создала наша группа, выход из сценария сохранения комментария");
                    return;
                }
                var commentId = object.get("id").getAsLong();
                var orderId = orderOperator.getOrderId(fromId, albumId);

                itemOperator.saveItem(albumsItems, dateInSeconds, commentText, orderId, null, commentId);
            } catch (Exception e) {
                log.error("Ошибка при сохранения комментария: {}", e.getMessage());
            }

        } catch (Exception e) {
            throw new RuntimeException("Не удалось записать комментарий в базу\n" + e.getMessage());
        }
    }

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

    public boolean isExpiredToken() throws IOException {
        return commonOperator.isExpiredToken(standaloneId);
    }
}



