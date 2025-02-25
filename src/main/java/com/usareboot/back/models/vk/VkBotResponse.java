package com.usareboot.back.models.vk;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class VkBotResponse {
    private String itemName;
    private String itemUrl;
    private String itemPhotoPath;
    private String itemSize;
    private Integer itemCount;
    private Float cost;
    private Object vk_event;
}
