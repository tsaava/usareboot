package ru.spmi.backend.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.spmi.backend.entities.DRolesEntity;
import ru.spmi.backend.entities.UsersEntity;

import java.util.ArrayList;
import java.util.Set;

@Repository
public interface RolesRepository extends JpaRepository<DRolesEntity, Long> {

    DRolesEntity findDRolesEntityByRoleId(Long roleId);
    DRolesEntity findDRolesEntityByRoleName(String roleName);

}
