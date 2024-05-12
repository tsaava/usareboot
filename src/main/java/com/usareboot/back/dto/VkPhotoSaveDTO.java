package com.usareboot.back.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class VkPhotoSaveDTO {
    String albumId;
    String date;
    String id;
    String sizes;
    String has_tags;
}
