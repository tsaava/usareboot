package com.usareboot.back.models.auth;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ChooseRoleResponseDTO {
    private Long roleId;
    private String token;

}
