package com.usareboot.back.models.vk;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
@Getter
@Setter
public class VkOauth2Response {
    private String refresh_token;
    private String access_token;
    private String id_token;
    private String token_type;
    private long expires_in;
    private String user_id;
    private String scope;
}
