package com.usareboot.back.controllers;

import com.usareboot.back.persistence.usareboot.entities.auth.UsersEntity;
import com.usareboot.back.services.auth.TabAccessService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("/api/tabs")
@RequiredArgsConstructor
public class TabController {
    private final TabAccessService tabAccessService;

    @GetMapping("/allowed")
    public ResponseEntity<List<String>> getAllowedTabs(Authentication authentication) {
        UsersEntity user = (UsersEntity) authentication.getPrincipal();
        List<String> allowedTabs = tabAccessService.getAllowedTabsForUser(user);

        if (allowedTabs.isEmpty()) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN)
                    .body(Collections.singletonList("У вас нет доступа ни к одной вкладке"));
        }

        return ResponseEntity.ok(allowedTabs);
    }

    @PreAuthorize("@tabAccessService.hasAccessToTab(#user, #tabPath)")
    @GetMapping("/check-access/{tabPath}")
    public boolean checkTabAccess(@PathVariable String tabPath, @AuthenticationPrincipal UsersEntity user) {
        return true; // Доступ разрешён, если не выброшено исключение
    }
}