package ru.spmi.backend.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;
import ru.spmi.backend.repositories.RolesRepository;

@Service

public class RoleDAO {

    @Autowired
    private RolesRepository rolesRepository;


    public String getAllRoles(){
        return rolesRepository.findDistinctByActive(1).getRoleName();
    }

}
