package com.usareboot.back.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class VkPhotoGetListDTO {
    String server;
    String photos_list;
    String aid;
    String hash;
    Integer gid;
}
