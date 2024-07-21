package com.usareboot.back.models.vk;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class VkPhotoSaveDTO {
    String album_id;
    String date;
    String id;
    List<VkPhotoSizeResponse> sizes;
    String owner_id;
    String text;
    String user_id;
    String web_view_token;
    String has_tags;
    Object orig_photo;
}
