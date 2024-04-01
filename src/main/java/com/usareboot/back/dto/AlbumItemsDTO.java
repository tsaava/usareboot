package com.usareboot.back.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class AlbumItemsDTO {
    private  long albomItemId;
    private long albomId;
    private long vkItemId;
    private long tgItemId;
    private String photoPath;
    private String description;
    private String descriptionShort;
    private Number albomItemWeight;
    private String albomItemName;
    private Date dateCreate;
    private Integer albomItemCount;
    private Double albomItemCost;
    private  Double albomItemRate;
    private  Short albomItemStatus;
}