package com.usareboot.back.controllers.VK;

import com.usareboot.back.services.vk.KeyboardService;
import com.usareboot.back.services.vk.VkBotService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
@RequestMapping("/bot/vk")
public class VkBotController {
    private final VkBotService vkBotService;
    private final KeyboardService keyboardService;

    @PostMapping(value = "/item/save")
    public ResponseEntity<?> itemSave(@RequestParam(name = "itemName", required = false) String itemName,
                                   @RequestParam(name = "itemUrl", required = false) String itemUrl,
                                   @RequestParam(name = "itemPhotoPath", required = false) String itemPhotoPath,
                                   @RequestParam(name = "itemSize", required = false) String itemSize,
                                   @RequestParam(name = "itemCount", required = false) String itemCount,
                                   @RequestParam(name = "clientId", required = false) String clientId,
                                   @RequestParam(name = "cost", required = false) String cost,
                                   @RequestParam(name = "itemColor", required = false) String itemColor,
                                   @RequestParam(name = "timestamp", required = false) Integer timestamp,
                                   @RequestParam(name = "vk_event", required = false) String vk_event) throws IOException {
        vkBotService.saveClientItem(itemName, itemUrl, itemPhotoPath, itemSize, itemCount, clientId, cost, itemColor, timestamp, vk_event);
        return ResponseEntity.ok("ok");
    }

    @PostMapping(value = "/rates")
    public ResponseEntity<?> getRate(@RequestParam(name = "itemUrl") String itemUrl,
                                     @RequestParam(name = "userId") Integer userId) throws IOException {
        vkBotService.getRates(itemUrl, userId);
//        vkBotService.saveClientItem(itemName, itemUrl, itemPhotoPath, itemSize, itemCount, clientId, cost, itemColor, timestamp, vk_event);
        return ResponseEntity.ok("ok");
    }
}
