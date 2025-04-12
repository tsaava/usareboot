package com.usareboot.back.controllers.VK;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.usareboot.back.models.vk.VkBotResponseDTO;
import com.usareboot.back.models.vk.VkEvent;
import com.usareboot.back.models.vk.VkPhotoObject;
import com.usareboot.back.models.vk.VkPhotoSaveDTO;
import com.usareboot.back.services.vk.VkBotService;
import com.usareboot.back.services.vk.VkService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.catalina.mapper.Mapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.Map;
import java.util.Optional;

@RestController
@Slf4j
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
@RequestMapping("/home")
public class VkController {

    private final VkService vkService;
    private final VkBotService vkBotService;

    @PostMapping("/vk/server")
    public Object VkServer(@RequestBody String requestBody) throws IOException {
        JsonObject json = JsonParser.parseString(requestBody).getAsJsonObject();
        String type = json.get("type").getAsString();
        if (type.equals("confirmation")) {
            return "9da08a29";
//            return vkDAO.getCallbackConfirmationCode();
        }
        if (type.equals("photo_comment_new")) {
            vkService.saveCommentUser(json.getAsJsonObject("object"));
        }
        if (type.equals("message_new")) {
            vkService.sendKeyboard(requestBody);
            /*var o = vkBotService.sendMessageWithKeyboard(requestBody);
            log.info("vkService.saveInRedisClientPhoto(requestBody):{}", o);*/
        }
        return "ok";
    }
}
