package com.usareboot.back.repositories.auth;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.usareboot.back.entities.auth.DRolesEntity;

@Repository
public interface RolesRepository extends JpaRepository<DRolesEntity, Long> {

    DRolesEntity findDRolesEntityByRoleId(Long roleId);
//    DRolesEntity findDRolesEntityByRoleName(String roleName);

//    DRolesEntity findDRolesEntitiesByActive(int active);
    DRolesEntity findDistinctByActive(int active);

}
