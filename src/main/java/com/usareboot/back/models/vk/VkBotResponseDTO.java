package com.usareboot.back.models.vk;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

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
    private Double itemCost;
    private BigDecimal resultCost;
    private VkEvent vkEvent;
    private BigDecimal rate;
}
