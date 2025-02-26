package com.usareboot.back.controllers.VK;

import com.usareboot.back.services.vk.VkBotService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
@RequestMapping("/api/usareboot/vk/bot")
public class VkBotController {
    private final VkBotService vkBotService;

    @PostMapping(value = "/item/save")
    public ResponseEntity<?> VkBot(@RequestParam(name = "itemName", required = false) String itemName,
                                   @RequestParam(name = "itemUrl", required = false) String itemUrl,
                                   @RequestParam(name = "itemPhotoPath", required = false) String itemPhotoPath,
                                   @RequestParam(name = "itemSize", required = false) String itemSize,
                                   @RequestParam(name = "itemCount", required = false) String itemCount,
                                   @RequestParam(name = "clientId", required = false) String clientId,
                                   @RequestParam(name = "cost", required = false) String cost,
                                   @RequestParam(name = "vk_event", required = false) String vk_event) throws IOException {
        vkBotService.saveClientItem(itemName, itemUrl, itemPhotoPath, itemSize, itemCount, clientId, cost, vk_event);
        return ResponseEntity.ok("ok");
    }
}
