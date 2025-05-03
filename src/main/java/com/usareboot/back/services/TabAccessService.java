package com.usareboot.back.services;

import com.usareboot.back.persistence.usareboot.entities.auth.UsersEntity;
import com.usareboot.back.persistence.usareboot.repository.auth.RolesRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TabAccessService {
    private final RolesRepository roleRepository;

    public boolean hasAccessToTab(UsersEntity user, String tabPath) {
        return user.getRoles().stream()
                .flatMap(role -> role.getTabs().stream())
                .anyMatch(tab -> tab.getPath().equals(tabPath));
    }
}