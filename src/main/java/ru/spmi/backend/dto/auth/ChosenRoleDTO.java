package ru.spmi.backend.dto.auth;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ChosenRoleDTO {

    // этот дто можно упразднить (заменить любым общим дто с 1 стринг полем)
    private Long roleId;
    private String roleName;
    private String roleCode;

}
