package com.usareboot.back.models.auth;

import com.usareboot.back.persistence.usareboot.entities.auth.DRolesEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

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
