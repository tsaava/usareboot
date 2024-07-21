package com.usareboot.back.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ItemWeightListDTO {
        long itemId;
        long clientId;
        Long statusId;
        long albomId;
        long vkId;
        long orderId;
        String fi;
        String fio;
        String itemName;
        String albomName;
        String itemSize;
        Integer itemCount;
        String statusName;
        Number itemWeight;
        String dateDelivery;
        String spHelpId;
        String razdacha;
}
