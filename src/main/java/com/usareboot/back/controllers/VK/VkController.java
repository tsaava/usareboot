package com.usareboot.back.controllers.VK;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.usareboot.back.repositories.auth.UserRepository;
import com.usareboot.back.services.MainDAO;
import com.usareboot.back.services.VkDAO;
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
@CrossOrigin(origins = "*")
@RequestMapping("/home")
public class VkController {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private VkDAO vkDAO;

    @Autowired
    private MainDAO mainDAO;
    @GetMapping("/home")
    public ResponseEntity<?> getHomePage() throws UnsupportedEncodingException, NoSuchAlgorithmException {
        return new ResponseEntity<>("home page", HttpStatus.OK);
    }
    @PostMapping("/vk/server")
    public String VkServer(@RequestBody String requestBody) throws IOException {
        JsonObject json = JsonParser.parseString(requestBody).getAsJsonObject();
        String type = json.get("type").getAsString();
        if (type.equals("confirmation")) {
            return "d54fdc68";
//            return vkDAO.getCallbackConfirmationCode();
        }
        if (type.equals("photo_comment_new")) {
            handlePhotoCommentNew(json.getAsJsonObject("object"));
        }
        return "ok";
    }
    private void handlePhotoCommentNew(JsonObject object) throws IOException {
        vkDAO.saveCommentUser(object);
    }
}
