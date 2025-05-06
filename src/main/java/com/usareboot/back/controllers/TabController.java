package com.usareboot.back.controllers;

import com.usareboot.back.models.auth.Tab;
import com.usareboot.back.models.auth.TabResponse;
import com.usareboot.back.persistence.usareboot.entities.auth.TabEntity;
import com.usareboot.back.persistence.usareboot.entities.auth.UsersEntity;
import com.usareboot.back.services.auth.TabAccessService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("/api/usareboot/tabs")
@RequiredArgsConstructor
@Slf4j
public class TabController {
    private final TabAccessService tabAccessService;

    @GetMapping("/allowed")
    public ResponseEntity<?> getAllowedTabs(@RequestParam(name="roleId") Long roleId) {

        log.debug("roleId:{}",roleId);
//        UsersEntity user = (UsersEntity) authentication.getPrincipal();
        List<Tab> allowedTabEntities = tabAccessService.getAllowedTabsForUser(roleId);

        if (allowedTabEntities.isEmpty()) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN)
                    .body(Collections.singletonList("У вас нет доступа ни к одной вкладке"));
        }
        TabResponse tabResponse = TabResponse.builder().tabs(allowedTabEntities).build();
        log.debug("allowedTabs: {}",tabResponse);
        return ResponseEntity.ok(tabResponse);
    }

    @PreAuthorize("@tabAccessService.hasAccessToTab(#user, #tabPath)")
    @GetMapping("/check-access/{tabPath}")
    public boolean checkTabAccess(@PathVariable String tabPath, @AuthenticationPrincipal UsersEntity user) {
        return true; // Доступ разрешён, если не выброшено исключение
    }
}