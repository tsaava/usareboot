package com.usareboot.back.models.vk;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;


@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class VkPhotoObject {
    private Integer date;
    private Integer from_id;
    private Integer id;
    private Integer version;
    private Integer out;
    private List<String> fwd_messages;
    private Boolean important;
    private Boolean is_hidden;
    private List<VkAttachments> attachments;
    private Integer conversation_message_id;
    private String text;
    private Integer peer_id;
    private Integer random_id;
}
