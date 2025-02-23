package com.usareboot.back.controllers.VK;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.usareboot.back.services.vk.VkService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.Map;

@RestController
@Slf4j
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
@RequestMapping("/home")
public class VkController {

    private final VkService vkService;

    @PostMapping("/vk/server")
    public Object VkServer(@RequestBody String requestBody) throws IOException {
        JsonObject json = JsonParser.parseString(requestBody).getAsJsonObject();
        String type = json.get("type").getAsString();
        if (type.equals("confirmation")) {
            return "6c0d90eb";
//            return vkDAO.getCallbackConfirmationCode();
        }
        if (type.equals("photo_comment_new")) {
            vkService.saveCommentUser(json.getAsJsonObject("object"));
        }

        if (type.equals("message_new")) {
//            Object o = vkService.startMakeOrder(requestBody);
//            log.info("vkService.startMakeOrder(requestBody):{}",o);
//            return o;
        }
        return "ok";
    }

    @PostMapping("/vk/bot")
    public ResponseEntity<?> VkBot(@RequestBody String requestBody) throws IOException {
        /*JsonObject json = JsonParser.parseString(requestBody).getAsJsonObject();
        String type = json.get("type").getAsString();*/
        String decodedItemName = URLDecoder.decode(requestBody, StandardCharsets.UTF_8);
        log.info("requestBody: {}", requestBody);
        log.info("decodedItemName: {}", decodedItemName);
        return ResponseEntity.ok("ok");
    }
}
