package ru.spmi.backend.services.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.spmi.backend.entities.auth.UsersEntity;

import java.util.*;

@Service("userDetailsService")
public class UserService implements UserDetailsService {


    @Autowired
    private UserDAO userDAO;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {
        UsersEntity user = userDAO.findUserByLogin(login);
//        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        List<GrantedAuthority> authorities = buildUserAuthority(userDAO.getRoleEntityByLogin(login));
//        System.out.println(authorities.size());

        return buildUserForAuthentication(user, authorities);
    }

    public User buildUserForAuthentication(UsersEntity user, List<GrantedAuthority> grantedAuthorities) {
//        System.out.println(grantedAuthorities.size());
        return new User(user.getLogin(), user.getPassword(), true, true, true, true, grantedAuthorities);
    }

    public List<GrantedAuthority> buildUserAuthority(Set<Long> userRolesId) {
        Set<GrantedAuthority> grantedAuthorities = new HashSet<>();
//        System.out.println(userRolesId.size());
        userRolesId.forEach((userRoleId) -> grantedAuthorities.add(new SimpleGrantedAuthority(userDAO.getRoleById(userRoleId).getRoleName())));
        return new ArrayList<>(grantedAuthorities);
    }

}


