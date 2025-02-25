package com.usareboot.back.controllers.VK;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
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
            return "6c0d90eb";
//            return vkDAO.getCallbackConfirmationCode();
        }
        if (type.equals("photo_comment_new")) {
            vkService.saveCommentUser(json.getAsJsonObject("object"));
        }
        if (type.equals("message_new")) {
            var o = vkBotService.saveInRedisClientPhoto(requestBody);
            log.info("vkService.saveInRedisClientPhoto(requestBody):{}", o);
        }
        return "ok";
    }

    @PostMapping(value = "/vk/bot")
    public ResponseEntity<?> VkBot(@RequestParam("itemName") String itemName,
                                   @RequestParam("itemUrl") String itemUrl,
                                   @RequestParam("itemPhotoPath") String itemPhotoPath,
                                   @RequestParam("itemSize") String itemSize,
                                   @RequestParam("itemCount") String itemCount,
                                   @RequestParam("clientId") String clientId,
                                   @RequestParam("cost") String cost,
                                   @RequestParam("vk_event") String vk_event) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        JsonObject json = JsonParser.parseString(vk_event).getAsJsonObject();
        log.info("json: {}", json);

        VkEvent vkPhotoObject = mapper.readValue(json.toString(), VkEvent.class);
        log.info("itemName: {}, itemUrl: {}, itemPhotoPath: {}, itemSize: {}, itemCount: {}, clientId: {}, cost: {}, vkPhotoObject: {}",
                itemName, itemUrl, itemPhotoPath, itemSize, itemCount, clientId, cost, vkPhotoObject);


        return ResponseEntity.ok("ok");
    }
}
