package com.usareboot.back.models.vk;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class VkBotResponseDTO {
    private String itemName;
    private String itemUrl;
    private String itemPhotoPath;
    private String itemSize;
    private Integer itemCount;
    private Integer clientId;
    private Float cost;
    private Object vk_event;
}
