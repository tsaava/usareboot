package com.usareboot.back.client;


import com.usareboot.back.models.vk.VkOauth2Response;
import feign.Headers;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "vkIdClient", url = "https://id.vk.com")
public interface VkIdClient {

    @PostMapping(value  = "/oauth2/auth",produces = "application/x-www-form-urlencoded")
//    @Headers("Content-Type: application/x-www-form-urlencoded")
    String exchangeCodeForTokens(
            @RequestParam("grant_type") String grantType,
            @RequestParam("code") String code,
            @RequestParam("code_verifier") String codeVerifier,
            @RequestParam("device_id") String deviceId,
            @RequestParam("client_id") String clientId,
            @RequestParam("client_secret") String clientSecret,
            @RequestParam("redirect_uri") String redirectUri,
            @RequestParam("state") String state
    );
}