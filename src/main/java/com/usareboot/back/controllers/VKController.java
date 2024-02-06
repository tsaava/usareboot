package com.usareboot.back.controllers;
import com.google.gson.Gson;
import com.usareboot.back.services.auth.VkService;
import com.usareboot.back.services.science.MainDAO;
import com.usareboot.back.services.science.ScienceDAO;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.usareboot.back.repositories.science.ScienceDissertationsRepository;

import java.io.IOException;

@RestController
@CrossOrigin(origins = "*")/*!!!!обязательно во все контроллеры вставлять!!*/
@RequestMapping("/api/usareboot/vk")

@RequiredArgsConstructor
public class VKController {
    @Autowired
    private VkService vkService;

    @PostMapping("/auth")
    public ResponseEntity<?> getVkAuth(@RequestBody String silent_token,
                                       @RequestParam(name="uuid") String uuid) throws IOException {
//        vkService.vkAuth(silent_token,uuid);
//        return null;
//        return (ResponseEntity<?>) vkService.vkAuth(silent_token,uuid);
        return new ResponseEntity<>(new Gson().toJson(vkService.vkAuth(silent_token,uuid)), HttpStatus.OK);
    }

    @PostMapping("/oauth")
    public ResponseEntity<?> getVkOAuth(@RequestParam(name="code") String code) throws IOException {
//        vkService.vkAuth(silent_token,uuid);
//        return null;
//        return (ResponseEntity<?>) vkService.vkAuth(silent_token,uuid);
        return new ResponseEntity<>(new Gson().toJson(vkService.vkOAuth(code)), HttpStatus.OK);
    }
}
