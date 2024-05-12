package com.usareboot.back.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.usareboot.back.dto.*;
import com.usareboot.back.entities.AlbumsEntity;
import com.vk.api.sdk.client.TransportClient;
import com.vk.api.sdk.client.VkApiClient;
import com.vk.api.sdk.httpclient.HttpTransportClient;
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
import org.springframework.core.env.Environment;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class VkDAO {
    @Autowired
    private Environment environment;
//    @Autowired
//    private Environment environment;
//    private String GROUPID=environment.getRequiredProperty("vk.groupId");
//    private String version = environment.getRequiredProperty("vk.version");
    private String GROUPID;//="224336762";
    private String version;//="5.139";
    /**
     * Получение silent_token
     * @param silent_token
     * @param uuid
     * @return
     * @throws IOException
     */
    public String vkAuth(String silent_token, String uuid) throws IOException {
        TransportClient transportClient = new HttpTransportClient();
        VkApiClient vk = new VkApiClient(transportClient);
        /*final String uri = "https://api.vk.com/method/auth.exchangeSilentAuthToken\" -d \"v=5.131&token=silent_token&access_token=service_token&uuid=uuid\"";

        RestTemplate restTemplate = new RestTemplate();
        String result = restTemplate.getForObject(uri, String.class);

        System.out.println(result);*/
        final CloseableHttpClient httpclient = HttpClients.createDefault();

//        final HttpUriRequest httpGet = new HttpGet("http://jsonplaceholder.typicode.com/posts?_limit=10");
//        try (
//                CloseableHttpResponse response1 = httpclient.execute(httpGet)
//        ){
//            final HttpEntity entity1 = response1.getEntity();
//            System.out.println(EntityUtils.toString(entity1));
//        }
        version = environment.getRequiredProperty("vk.version");

        final HttpPost httpPost = new HttpPost("https://api.vk.com/method/auth.exchangeSilentAuthToken");
        final List<NameValuePair> params = new ArrayList<>();
        params.add(new BasicNameValuePair("v", version));
        params.add(new BasicNameValuePair("token", silent_token));
        params.add(new BasicNameValuePair("access_token", "b3c038ccb3c038ccb3c038ccb3b0d6c3e6bb3c0b3c038ccd66cc4d5a0b76abdf85929d2"));
        params.add(new BasicNameValuePair("uuid", uuid));
        httpPost.setEntity(new UrlEncodedFormEntity(params));
        System.out.println(httpPost);

        try (
                CloseableHttpResponse response2 = httpclient.execute(httpPost)
        ) {
            final HttpEntity entity2 = response2.getEntity();
//            System.out.println(EntityUtils.toString(entity2));
//            httpclient.close();
//            System.out.println(EntityUtils.toString(entity2));

            return EntityUtils.toString(entity2);
        }
    }

    /**
     * Получение access_token
     * @param code
     * @return
     * @throws IOException
     */
    public String vkOAuth( String code) throws IOException {
        TransportClient transportClient = new HttpTransportClient();
        VkApiClient vk = new VkApiClient(transportClient);

        final CloseableHttpClient httpclient = HttpClients.createDefault();

//        final HttpUriRequest httpGet = new HttpGet("http://jsonplaceholder.typicode.com/posts?_limit=10");
//        try (
//                CloseableHttpResponse response1 = httpclient.execute(httpGet)
//        ){
//            final HttpEntity entity1 = response1.getEntity();
//            System.out.println(EntityUtils.toString(entity1));
//        }
//        System.out.println(silent_token);
        String redirectUrl = environment.getRequiredProperty("vk.redirectUrl");
        final HttpPost httpPost = new HttpPost("https://oauth.vk.com/access_token");
        final List<NameValuePair> params = new ArrayList<>();
        params.add(new BasicNameValuePair("client_id", "51727454"));
        params.add(new BasicNameValuePair("client_secret", "Lp8in0hLVi4I7SR7VMKz"));
        params.add(new BasicNameValuePair("redirect_uri", redirectUrl));
        params.add(new BasicNameValuePair("code", code));
        httpPost.setEntity(new UrlEncodedFormEntity(params));
        System.out.println(httpPost);

        try (
                CloseableHttpResponse response2 = httpclient.execute(httpPost)
        ) {
            final HttpEntity entity2 = response2.getEntity();
//            System.out.println(EntityUtils.toString(entity2));
//            httpclient.close();
//            System.out.println(EntityUtils.toString(entity2));

            return EntityUtils.toString(entity2);
        }
    }

    public Integer createAlbum(String token, AlbumsEntity albumsEntity) throws IOException {
//        TransportClient transportClient = new HttpTransportClient();
//        VkApiClient vk = new VkApiClient(transportClient);
        GROUPID=environment.getRequiredProperty("vk.groupId");
        version = environment.getRequiredProperty("vk.version");
        final CloseableHttpClient httpclient = HttpClients.createDefault();
//        headers.set("Content-type", "application/json;charset=UTF-8");
        final HttpPost httpPost = new HttpPost("https://api.vk.com/method/photos.createAlbum");
        final List<NameValuePair> params = new ArrayList<>();
        params.add(new BasicNameValuePair("access_token", token));
        params.add(new BasicNameValuePair("v", version));
        params.add(new BasicNameValuePair("title", albumsEntity.getAlbumName()));
        params.add(new BasicNameValuePair("group_id", GROUPID));
        params.add(new BasicNameValuePair("description", albumsEntity.getAlbumDesc()));
        params.add(new BasicNameValuePair("upload_by_admins_only","0"/*фотографии могут добавлять все пользователи*/));
        params.add(new BasicNameValuePair("http.protocol.content-charset", "UTF-8"));
        httpPost.setEntity(new UrlEncodedFormEntity(params,"UTF-8"));
//        System.out.println(httpPost);
        try (
                CloseableHttpResponse response2 = httpclient.execute(httpPost)
        ) {
             HttpEntity entity2 = response2.getEntity() ;
//            return  EntityUtils.toString(entity2);
            List<String> tempString = Collections.singletonList(EntityUtils.toString(entity2));
            ObjectMapper mapper = new ObjectMapper();
            System.out.println(tempString );
            List<VkResponse> userDtoList = tempString.stream().map(x -> {
                VkResponse userDto = null;
                try {
                    userDto = mapper.readValue(x, VkResponse.class);
                    System.out.print("userDto:");

                    System.out.println(userDto.getResponse().getId());

                } catch (JsonProcessingException e) {
                    System.out.println("exception"+e);
                }
//                return userDto.getResponse().getId();

                return userDto;
            }).collect(Collectors.toList());

            return userDtoList.get(0).getResponse().getId();
//            return  VkAlbumResponseDTO entity2 );

        }
    }

    public String updAlbum(String vkId, String token, AlbumRowRequestDTO albumsEntity) throws IOException {
        GROUPID=environment.getRequiredProperty("vk.groupId");
        version = environment.getRequiredProperty("vk.version");
        final CloseableHttpClient httpclient = HttpClients.createDefault();
        final HttpPost httpPost = new HttpPost("https://api.vk.com/method/photos.editAlbum");
        final List<NameValuePair> params = new ArrayList<>();
        params.add(new BasicNameValuePair("access_token", token));
        params.add(new BasicNameValuePair("v", version));
        params.add(new BasicNameValuePair("album_id", vkId));
        params.add(new BasicNameValuePair("owner_id", "-"+GROUPID));
//        params.add(new BasicNameValuePair("title", albumsDTO.getAlbumName()));
        System.out.println(albumsEntity.getAlbumDesc());
        params.add(new BasicNameValuePair("description", albumsEntity.getAlbumDesc()));
        params.add(new BasicNameValuePair("upload_by_admins_only","1"/*фотографии могут добавлять все пользователи*/));
        params.add(new BasicNameValuePair("http.protocol.content-charset", "UTF-8"));
        httpPost.setEntity(new UrlEncodedFormEntity(params,"UTF-8"));
        try (
                CloseableHttpResponse response2 = httpclient.execute(httpPost)
        ) {
            final HttpEntity entity2 = response2.getEntity();
//            System.out.println(EntityUtils.toString(entity2));
//            httpclient.close();
//            System.out.println(EntityUtils.toString(entity2));

            return EntityUtils.toString(entity2);
        }
//        try (
//                CloseableHttpResponse response2 = httpclient.execute(httpPost)
//        ) {
//            HttpEntity entity2 = response2.getEntity() ;
//            List<String> tempString = Collections.singletonList(EntityUtils.toString(entity2));
//            ObjectMapper mapper = new ObjectMapper();
//            System.out.println(tempString );
//            List<VkResponse> userDtoList = tempString.stream().map(x -> {
//                VkResponse userDto = null;
//                try {
//                    userDto = mapper.readValue(x, VkResponse.class);
//                    System.out.print("userDto:");
//
//                    System.out.println(userDto.getResponse().getId());
//
//                } catch (JsonProcessingException e) {
//                    System.out.println("exception"+e);
//                }
//                return userDto;
//            }).collect(Collectors.toList());
//            return  VkAlbumResponseDTO entity2 );

//        }
    }

    public String getUrlPhotoInAlbumVk(long albumId, String token) throws IOException {
        final CloseableHttpClient httpclient = HttpClients.createDefault();
        GROUPID=environment.getRequiredProperty("vk.groupId");
        version = environment.getRequiredProperty("vk.version");
        final HttpPost httpPost = new HttpPost("https://api.vk.com/method/photos.getUploadServer");
        final List<NameValuePair> params = new ArrayList<>();
        params.add(new BasicNameValuePair("access_token", token));
        params.add(new BasicNameValuePair("v", version));
        params.add(new BasicNameValuePair("album_id", String.valueOf(albumId)));
        params.add(new BasicNameValuePair("group_id", GROUPID));
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
            System.out.println(tempString );
            VkResponse userDtoList = mapper.readValue(tempString, VkResponse.class);
            return userDtoList.getResponse().getUpload_url();
        }
    }
    public VkPhotoSaveDTO savePhotoInVk(String photos_list,
                                                String album_id,
                                                String server,
                                                String hash,
                                                String access_token) throws IOException {
        final CloseableHttpClient httpclient = HttpClients.createDefault();
        GROUPID=environment.getRequiredProperty("vk.groupId");
        version = environment.getRequiredProperty("vk.version");
        final HttpPost httpPost = new HttpPost("https://api.vk.com/method/photos.save");
        final List<NameValuePair> params = new ArrayList<>();
        params.add(new BasicNameValuePair("access_token", access_token));
        params.add(new BasicNameValuePair("v", version));
        params.add(new BasicNameValuePair("album_id", String.valueOf(album_id)));
        params.add(new BasicNameValuePair("group_id", GROUPID));
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
            System.out.println(tempString );
            VkPhotoSaveDTO albumVkDto = mapper.readValue(tempString, VkPhotoSaveDTO.class);
            return albumVkDto;
        }
    }


}


