package com.usareboot.back.controllers.VK;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.usareboot.back.repositories.auth.UserRepository;
import com.usareboot.back.services.MainDAO;
import com.usareboot.back.services.VkDAO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
@RestController
@Slf4j
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
@RequestMapping("/home")
public class VkController {

    private final VkDAO vkDAO;

    @PostMapping("/vk/server")
    public String VkServer(@RequestBody String requestBody) throws IOException {
        JsonObject json = JsonParser.parseString(requestBody).getAsJsonObject();
        String type = json.get("type").getAsString();
        if (type.equals("confirmation")) {
            return "6c0d90eb";
//            return vkDAO.getCallbackConfirmationCode();
        }
        if (type.equals("photo_comment_new")) {
            vkDAO.saveCommentUser(json.getAsJsonObject("object"));
        }
        return "ok";
    }
}
