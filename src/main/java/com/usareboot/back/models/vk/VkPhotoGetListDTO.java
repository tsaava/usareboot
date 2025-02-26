package com.usareboot.back.models.vk;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class VkPhotoGetListDTO {
    String server;
    String photos_list;
    String aid;
    String hash;
    Integer gid;
}
