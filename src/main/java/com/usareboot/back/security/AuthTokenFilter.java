package com.usareboot.back.security;

import com.usareboot.back.persistence.usareboot.entities.auth.DRolesEntity;
import com.usareboot.back.persistence.usareboot.repository.auth.RolesRepository;
import com.usareboot.back.services.auth.UserService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Collections;
import java.util.List;

@Slf4j
public class AuthTokenFilter extends OncePerRequestFilter {
    @Autowired
    private  JwtUtils jwtUtils;
    @Autowired
    private  UserService userService;
    @Autowired
    private  RolesRepository rolesRepository;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        try {
            // забирает токен
            String jwt = parseJwt(request);
            // валидирует
            if (jwt != null && jwtUtils.validateJwtToken(jwt)) {
                String username = jwtUtils.getUserNameFromJwtToken(jwt);
                String roleId = jwtUtils.getRoleIdFromToken(jwt);
                var role = rolesRepository.findFirstByRoleId(Long.valueOf(roleId)).getRoleName();
                log.debug("role: {}",role);

                // Преобразуем роль в GrantedAuthority (например, "ROLE_ADMIN")
                List<GrantedAuthority> authorities = Collections.singletonList(
                        new SimpleGrantedAuthority("ROLE_" + role.toUpperCase())
                );

                // загружает юзера из бд (его роль)
                UserDetails userDetails = userService.loadUserByUsername(username);

                UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                        userDetails, null, authorities);

                // устанавливает эту роль в контекст
                authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authentication);
//                System.out.println( SecurityContextHolder.getContext());
            }
        } catch (Exception e) {
            log.error("Cannot set user authentication: {}", e.getMessage());
        }
        filterChain.doFilter(request, response);
    }

    // достает токен из запроса
    private String parseJwt(HttpServletRequest request) {
        String headerAuth = request.getHeader("Authorization");

        if (StringUtils.hasText(headerAuth) && headerAuth.startsWith("Bearer ")) {
            return headerAuth.substring(7, headerAuth.length());
        }
        if (request.getParameter("token") != null) {
            return request.getParameter("token");
        }

        return headerAuth;
    }
}
