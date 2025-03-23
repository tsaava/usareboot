package com.usareboot.back.models.vk;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class VkOauth2 {
    private String code;
    private String grant_type;
    private String code_verifier;
    private String device_id;
    private String client_id;
    private String client_secret;
    private String redirect_uri;
    private String state;
}
