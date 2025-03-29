package com.usareboot.back.controllers.VK;

import com.google.gson.Gson;
import com.usareboot.back.models.vk.TokenRequest;
import com.usareboot.back.models.vk.VkOauth2Response;
import com.usareboot.back.services.vk.VkService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/api/usareboot/vk")
@RequiredArgsConstructor
@Slf4j
public class VKAuthController {

    private final VkService vkService;

   /* @GetMapping("/oauth/callback")
    public ResponseEntity<?> callback(@RequestParam("code") String code) {
        vkService.saveAccessToken(code);
        return new ResponseEntity<>(HttpStatus.OK);
    }*/

    @PostMapping("/oauth2/token")
    public ResponseEntity<?> exchangeCodeForTokens(@RequestBody TokenRequest request) throws IOException {
        String s = vkService.exchangeCodeForTokens(request);
        log.info("s: {}",s);
        return new ResponseEntity<>(s, HttpStatus.OK);
    }

    @PostMapping("/oauth2/refreshToken")
    public ResponseEntity<?> exchangeRefreshTokens(@RequestBody TokenRequest request) throws IOException {
        vkService.exchangeRefreshTokens(request);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
