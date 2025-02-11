package com.usareboot.back.controllers.VK;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.usareboot.back.services.VkService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@Slf4j
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
@RequestMapping("/home")
public class VkController {

    private final VkService vkService;

    @PostMapping("/vk/server")
    public String VkServer(@RequestBody String requestBody) throws IOException {
        JsonObject json = JsonParser.parseString(requestBody).getAsJsonObject();
        String type = json.get("type").getAsString();
        if (type.equals("confirmation")) {
            return "6c0d90eb";
//            return vkDAO.getCallbackConfirmationCode();
        }
        if (type.equals("photo_comment_new")) {
            vkService.saveCommentUser(json.getAsJsonObject("object"));
        }
        return "ok";
    }
}
