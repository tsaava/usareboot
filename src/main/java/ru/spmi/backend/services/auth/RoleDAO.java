package ru.spmi.backend.services.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.spmi.backend.repositories.auth.RolesRepository;

@Service

public class RoleDAO {

    @Autowired
    private RolesRepository rolesRepository;


    public String getAllRoles(){
        return rolesRepository.findDistinctByActive(1).getRoleName();
    }

}
