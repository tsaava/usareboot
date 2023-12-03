package com.usareboot.back.dto.auth;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Set;

@Data
@AllArgsConstructor
public class ChooseRoleResponseDTO {
    private Long roleId;
    private String token;

}
