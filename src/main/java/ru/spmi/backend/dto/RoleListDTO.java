package ru.spmi.backend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.spmi.backend.entities.DRolesEntity;

import java.util.ArrayList;
import java.util.Set;

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
