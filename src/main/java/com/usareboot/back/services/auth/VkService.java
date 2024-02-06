package com.usareboot.back.services.auth;

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
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class VkService  {

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
        System.out.println(silent_token);

        final HttpPost httpPost = new HttpPost("https://api.vk.com/method/auth.exchangeSilentAuthToken");
        final List<NameValuePair> params = new ArrayList<>();
        params.add(new BasicNameValuePair("v", "5.139"));
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

        final HttpPost httpPost = new HttpPost("https://oauth.vk.com/access_token");
        final List<NameValuePair> params = new ArrayList<>();
        params.add(new BasicNameValuePair("client_id", "51727454"));
        params.add(new BasicNameValuePair("client_secret", "Lp8in0hLVi4I7SR7VMKz"));
        params.add(new BasicNameValuePair("redirect_uri", "https://localhost:80/vkAuth"));
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
}


