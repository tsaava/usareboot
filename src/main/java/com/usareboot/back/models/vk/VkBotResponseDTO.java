package com.usareboot.back.models.vk;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class VkBotResponseDTO {
    private String itemName;
    private String itemUrl;
    private String itemPhotoPath;
    private String itemSize;
    private Integer itemCount;
    private String itemColor;
    private Integer clientId;
    private Integer timestamp;
    private Double cost;
    private VkEvent vk_event;
}
