package com.usareboot.back.controllers.VK;

import com.usareboot.back.services.VkDAO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/api/usareboot/vk")
@RequiredArgsConstructor
public class VKAuthController {

    private final VkDAO vkService;

    @GetMapping("/oauth/callback")
    public ResponseEntity<?> callback(@RequestParam("code") String code) {
        vkService.saveAccessToken(code);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
