package com.usareboot.back.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ItemListDTO {
        long itemId;
        long clientId;
        long statusId;
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
        Date dateDelivery;
        String spHelpId;
        String razdacha;
}
