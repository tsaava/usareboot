package com.usareboot.back.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class AlbumItemsDTO {
    long albomItemId;
    long albomId;
    long vkItemId;
    long tgItemId;
    String photoPath;
    String description;
    String descriptionShort;
    Number albomItemWeight;
    String albomItemName;
    Date dateCreate;
    Integer albomItemCount;
    Double albomItemCost;
    Double albomItemRate;
    Short albomItemStatus;
}