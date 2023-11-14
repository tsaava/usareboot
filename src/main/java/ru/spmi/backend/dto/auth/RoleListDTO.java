package ru.spmi.backend.dto.auth;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.spmi.backend.entities.auth.DRolesEntity;

import java.util.ArrayList;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RoleListDTO {

    // этот дто можно упразднить (заменить любым общим дто с 1 стринг полем)
//    private Long roleId;
//    private String roleName;
//    private String roleCode;
    private ArrayList<DRolesEntity>rolesEntities;
}
