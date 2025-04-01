package com.usareboot.back.operators;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.usareboot.back.client.VkApiClient;
import com.usareboot.back.controllers.VK.ConfigureFeignUrlController;
import com.usareboot.back.models.AlbumsItemsDTO;
import com.usareboot.back.models.vk.VkAlbumItemResponse;
import com.usareboot.back.models.vk.VkAlbumResponse;
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
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

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

    private final CommonOperator commonOperator;
    private final VkApiClient vkApiClient;
    private final ConfigureFeignUrlController configureFeignUrlController;


    public String getCommentPhotoVk(long photo_id) throws IOException {
        var accessToken = commonOperator.getTokenClient(standaloneId).orElse(null);
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
        return vkApiClient.deleteAlbum((int) albumId, Integer.parseInt(groupId), accessToken, apiVersion);
    }

    public String postInVk(String message) {
        var accessToken = commonOperator.getTokenClient(standaloneId).orElse(null);
        var ownerId = "-"+groupId;
        return vkApiClient.createPost(accessToken, ownerId, message, "1", apiVersion);
    }

    public String getAllDesc(AlbumsItemsDTO albumsItemsDTO) {
        var allDesc = albumsItemsDTO.getAlbumItemName() + "\n" +
                albumsItemsDTO.getDescription() + "\n" +
                "цена: " + albumsItemsDTO.getAlbumItemCost().toString() + ", курс: " +
                albumsItemsDTO.getAlbumItemRate().toString() + "\n" +
                albumsItemsDTO.getItemUrl();
        return allDesc;
    }

    /*public AlbumsItemsDTO saveFileInVk(Long albumId, MultipartFile file, MultipartFile adFile1, MultipartFile adFile2, MultipartFile adFile3, AlbumsItemsDTO albumsItemsDTO) throws IOException {
        final String accessToken = commonOperator.getTokenClient(standaloneId).orElse(null);

        log.info("Редактирование комментария в вк");
        if (file == null && (albumsItemsDTO.getPhotoUrl() == null || albumsItemsDTO.getPhotoUrl().isEmpty())) {
            log.error("Ошибка загрузки: нет ссылки на фотографию");
            throw new RuntimeException("Ошибка загрузки: нет ссылки на фотографию");
        }

        var photoUploadVk = getUrlPhotoInAlbumVk(albumId);
        log.info("Upload photo in vk");
        var vkPhotoList = configureFeignUrlController.uploadPhotoInVk(photoUploadVk, file, adFile1, adFile2, adFile3);

        log.info("Save photo in vk");
        var photo = savePhotoInVk(vkPhotoList.getPhotos_list(), String.valueOf(albumId), String.valueOf(vkPhotoList.getServer()), vkPhotoList.getHash(), accessToken);

        var allDesc = getAllDesc(albumsItemsDTO);
        albumsItemsDTO.setDescription(allDesc);
        editPhotoInVk(photo, allDesc);
        log.info("В ВК фотография успешно загружена и добавлено описание");
        albumsItemsDTO.setVkItemId(Long.parseLong(photo));
        albumsItemsDTO.setVkPhotoPath("https://vk.com/photo-" + groupId + "_" + photo);

        Path filePath = Path.of(pathPhoto);
        log.info("Сохранение фото в БД");
        albumsItemsDTO.setPhotoPath(String.valueOf(filePath));
        return albumsItemsDTO;
    }*/
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
                                String hash,
                                String access_token) throws IOException {
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
}
