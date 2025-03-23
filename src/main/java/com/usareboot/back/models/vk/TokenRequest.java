package com.usareboot.back.models.vk;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class TokenRequest {
    private String code;
    private String code_verifier;
    private String device_id;
    private String state;
}
