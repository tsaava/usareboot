package com.usareboot.back.models.vk;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
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
    String access_key;
    String lat;
    @JsonProperty("long")
    String longPhoto;
    VkPhotoSizeResponse orig_photo;
}
