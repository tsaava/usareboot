package com.usareboot.back.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.math.BigInteger;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AlbumsItemsDTO {
    private long albumItemId;
    private long albumId;
    private String albumName;
    private Long vkItemId;
    private Long tgItemId;
    private String photoPath;
    private String itemDescription;
    private String description;
    private String itemUrl;
    private String adItemUrl1;
    private String adItemUrl2;
    private String adItemUrl3;
    private String photoUrl;
    private String descriptionShort;
    private BigInteger albumItemWeight;
    private String albumItemName;
    private Integer albumItemCount;
    private String dateCreate;
    private BigDecimal albumItemCost;
    private BigDecimal albumItemRate;
    private String albumItemColor;
    private Long albumItemStatus;
    private String albumItemStatusName;
    private String vkPhotoPath;
    private boolean noSize;
}
