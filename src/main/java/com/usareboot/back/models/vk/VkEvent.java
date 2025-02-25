package com.usareboot.back.models.vk;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class VkEvent {
    private String group_id;
    private String type;
    private String event_id;
    private String v;
    private VkPhotoObject object;
}
