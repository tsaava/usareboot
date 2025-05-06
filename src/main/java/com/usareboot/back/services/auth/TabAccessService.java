package com.usareboot.back.services.auth;

import com.usareboot.back.models.auth.Tab;
import com.usareboot.back.persistence.usareboot.entities.auth.DRolesEntity;
import com.usareboot.back.persistence.usareboot.entities.auth.TabEntity;
import com.usareboot.back.persistence.usareboot.entities.auth.UsersEntity;
import com.usareboot.back.persistence.usareboot.repository.auth.RolesRepository;
import com.usareboot.back.persistence.usareboot.repository.auth.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class TabAccessService {
    private final UserRepository userRepository;
    private final RolesRepository rolesRepository;

    /**
     * Проверяет, есть ли у пользователя доступ к указанной вкладке.
     */
    public void hasAccessToTab(UsersEntity user, String tabPath) {
        // Загружаем пользователя с ролями и вкладками (если LAZY)
        /*UsersEntity fullUser = userRepository.findByIdWithRolesAndTabs(user.getUserId())
                .orElseThrow(() -> new RuntimeException("User not found"));

        return fullUser.getRoles().stream()
                .flatMap(role -> role.getTabEntities().stream())
                .anyMatch(tab -> tab.getPath().equals(tabPath));*/
    }

    /**
     * Получает список разрешённых вкладок для пользователя.
     */
    public List<Tab> getAllowedTabsForUser(Long roleId) {
        // Загружаем пользователя с ролями и вкладками (если ленивая загрузка)
//        UsersEntity fullUser = userRepository.findByIdWithRolesAndTabs(user.getUserId()).orElseThrow(() -> new RuntimeException("User not found"));
        DRolesEntity rolesEntity = rolesRepository.findFirstByRoleId(roleId);
        log.debug("rolesEntity:{}",rolesEntity.getTabEntities());
        List <Tab> tabs =new ArrayList<>();
        // Собираем все уникальные вкладки из всех ролей пользователя
         rolesEntity.getTabEntities()
                .forEach(tab->{
                    Tab build = Tab.builder().name(tab.getName()).path(tab.getPath()).sort(tab.getSort()).build();
                    tabs.add(build);
                });

        return tabs.stream().distinct().sorted(Comparator.comparingInt(s -> Integer.parseInt(s.getSort()))).collect(Collectors.toList());
                /*.map(tab -> Map.of(
                                "name", tab.getName(),
                                "path", tab.getPath()))*/
//                .distinct() // Убираем дубликаты
//                .sorted(Comparator.comparing(item -> Integer.parseInt(item.get("sort"))))
//                .collect(Collectors.toList());
    }
}
