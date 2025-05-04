package com.usareboot.back.services.auth;

import com.usareboot.back.persistence.usareboot.entities.auth.UsersEntity;
import com.usareboot.back.persistence.usareboot.repository.auth.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

//@Service
//@RequiredArgsConstructor
public class TabAccessService {
//    private final UserRepository userRepository;

    /**
     * Проверяет, есть ли у пользователя доступ к указанной вкладке.
     */
//    public boolean hasAccessToTab(UsersEntity user, String tabPath) {
//        // Загружаем пользователя с ролями и вкладками (если LAZY)
//        UsersEntity fullUser = userRepository.findByIdWithRolesAndTabs(user.getUserId())
//                .orElseThrow(() -> new RuntimeException("User not found"));
//
//        return fullUser.getRoles().stream()
//                .flatMap(role -> role.getTabs().stream())
//                .anyMatch(tab -> tab.getPath().equals(tabPath));
//    }
}
