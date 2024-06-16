package com.usareboot.back.controllers;

import com.usareboot.back.models.ParsedComment;
import com.usareboot.back.parser.CommentParser;
import com.usareboot.back.repositories.auth.UserRepository;
import com.usareboot.back.services.VkDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/home")
public class HomeController {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private VkDAO vkDAO;
    @GetMapping("/home")
    public ResponseEntity<?> getHomePage() throws UnsupportedEncodingException, NoSuchAlgorithmException {
        return new ResponseEntity<>("home page", HttpStatus.OK);
    }
    @PostMapping("/vk/server")
    public String VkServer(@RequestBody String requestBody) {
        JsonObject json = JsonParser.parseString(requestBody).getAsJsonObject();
        String type = json.get("type").getAsString();
        if (type.equals("confirmation")) {
            return "98639192";//vkDAO.getCallbackConfirmationCode();
        }
        if (type.equals("photo_comment_new")) {
            handlePhotoCommentNew(json.getAsJsonObject("object"));
        }
        return "ok";
    }
    private void handlePhotoCommentNew(JsonObject object) {
        String commentText = object.get("text").getAsString();
        long dateInSeconds = object.get("date").getAsLong();
        int fromId = object.get("from_id").getAsInt();

        // Преобразование даты в LocalDateTime
        LocalDateTime commentDate = LocalDateTime.ofInstant(Instant.ofEpochSecond(dateInSeconds), ZoneId.systemDefault());

        // Получение никнейма пользователя
        String userName = vkDAO.getUserName(fromId);

        // Вызов парсера комментариев
        CommentParser parser = new CommentParser();
        ParsedComment parsedComment = parser.parse(commentText);

        // Логика обработки распарсенного комментария
        System.out.println("User: " + userName);
        System.out.println("Date: " + commentDate);
        System.out.println(parsedComment);
    }

    @GetMapping("/users")
    public ResponseEntity<?> homeUsers() throws UnsupportedEncodingException, NoSuchAlgorithmException {
        return new ResponseEntity<>(userRepository.findAll(), HttpStatus.OK);
    }
}
