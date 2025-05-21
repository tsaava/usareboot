package com.usareboot.back.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ItemListDTO {
        long itemId;
        long clientId;
        Long itemStatusId;
        Long payStatusId;
        Long albumId;
        Long albumItemId;
        Long vkId;
        long orderId;
        String clientUrl;
        String fi;
        String itemName;
        String albumVkUrl;
        String albumName;
        String itemSize;
        String itemColor;
        Integer itemCount;
        String itemUrl;
        String itemStatus;
        String payStatus;
        Number itemWeight;
        String dateComment;
        String photoPath;
        String vkPhotoPath;
        String comment;
        Number albumItemCost;
        Number albumItemRate;
        Number cost;
        String repaymentName;
        boolean isNotSize;
        Boolean isPriceDependsSize;
}
