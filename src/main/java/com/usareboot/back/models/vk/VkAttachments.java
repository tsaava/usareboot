package com.usareboot.back.models.vk;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class VkAttachments {
    private String type;
    private VkPhotoSaveDTO photo;
}
