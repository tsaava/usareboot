package com.usareboot.back.services.auth;

import com.usareboot.back.persistence.usareboot.repository.auth.RolesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service

public class RoleDAO {

    @Autowired
    private RolesRepository rolesRepository;


    public String getAllRoles(){
        return rolesRepository.findDistinctByActive(1).getRoleName();
    }

}
