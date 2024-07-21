package com.usareboot.back.models.vk;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class VkAlbumResponseDTO {
    Integer id; //"301977987"
    Integer owner_id;//-224336762
    Integer size;// 0
    String title; //aboba2
    Integer feed_disabled;// 0
    Integer feed_has_pinned; //0
    Integer can_upload; //1
    Integer comments_disabled; //0
    Integer created; //1710420276
    String description; //sdsdsdsd
    Integer thumb_id; //0
    Integer updated; //1710420276
    Integer upload_by_admins_only; //0
    String upload_url;
    Integer album_id;
    Integer user_id;

}
