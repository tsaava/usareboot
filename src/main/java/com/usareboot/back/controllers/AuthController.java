package com.usareboot.back.controllers;

import com.google.gson.Gson;
import com.usareboot.back.models.auth.*;
import com.usareboot.back.security.JwtUtils;
import com.usareboot.back.services.auth.UserDAO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;


@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private UserDAO userDAO;


    @Autowired
    private JwtUtils jwtUtils;

    @PostMapping("/login")
    public ResponseEntity<?> authenticateUserPage(@RequestBody @Validated AuthRequestDTO loginRequest) throws UnsupportedEncodingException, NoSuchAlgorithmException {

        // логинимся в контекст
        Authentication authentication = authenticateUser(loginRequest.getLogin(), loginRequest.getPassword());
//        System.out.println(authenticateUser(loginRequest.getLogin(), loginRequest.getPassword()));
        //создаем новый токен с role_code
        //String jwt = jwtUtils.generateJwtToken(loginRequest.getLogin(), userDAO.getRoleByLogin(loginRequest.getLogin()));

        //создаем новый токен с role_id
        String jwt = jwtUtils.generateJwtToken(loginRequest.getLogin(), userDAO.getRoleIdByLogin(loginRequest.getLogin()));

//        // получаем список всех доступных этому person'у ролей
        var list = userDAO.findAllUserRolesUserId(userDAO.findUserByLogin(loginRequest.getLogin()));
        System.out.println(list);
        ArrayList<ChosenRoleDTO> roleList = new ArrayList<>();
        list.forEach(x -> roleList.add(new ChosenRoleDTO(   x.getRoleId(),
//                x.getRoleCode(),
                x.getRoleName())));
       // Set<String> roles = list.getRoleId();//.stream().map(DRolesEntity::getRoleName).collect(Collectors.toSet());
//        Set<String> rolesCode = list.stream().map(x -> x.getRoleCode()).collect(Collectors.toSet());
//        Set<Long> rolesId = list.stream().map(x -> x.getRoleId()).collect(Collectors.toSet());


        var responcedto = new AuthResponceDTO();
        var userdto = new UserDTO();
        var roleListDTO = new RoleListDTO();
        var chosenRoleDTO = new ChosenRoleDTO();
        userdto.setToken(jwt);

        userdto.setLogin(loginRequest.getLogin());

//        responcedto.setRoles(roles);
//        responcedto.setRolesId(rolesId);
//        roleListDTO.setRoleName(roles);
//        roleListDTO.setRoleId(rolesId);

//        roleListDTO.setRolesEntities(list);

        responcedto.setUser(userdto);
        responcedto.setRoleList(roleList);
//        System.out.println(roleListDTO);

        if (list.size() < 2) {
            responcedto.setNeedToChooseRole(false);
        } else {
            responcedto.setNeedToChooseRole(true);
            System.out.println("setNeedToChooseRole(true)");
        }
//        System.out.println("responcedto ="+new Gson().toJson(responcedto));
        return new ResponseEntity<>(new Gson().toJson(responcedto), HttpStatus.OK);
    }


    @GetMapping("/test")
    public String testMethod() {
        return "suck";
    }

    /*
    функция для смены роли пользователя с несколькими учетками
     */
    @PostMapping("/choose_role")
    public ResponseEntity<?> gotChosenRolePage(@RequestHeader("Authorization") String token, @RequestBody @Validated ChosenRoleDTO chosenRole) throws UnsupportedEncodingException, NoSuchAlgorithmException {

        // проверяет можно ли перерегаться
        boolean isTokenValid = jwtUtils.validateJwtToken(token);

        // если можно, то создает новый токен
        if (isTokenValid /*&& isRoleAllowed*/) {
            String newToken = jwtUtils.generateJwtToken(
                        userDAO.findNeedLoginByLoginAndRole(
                                SecurityContextHolder.getContext().getAuthentication().getName(),
                                chosenRole.getRoleCode()),
                        chosenRole.getRoleId()
            );
            // получаем текущий логин из контекста
            String login = SecurityContextHolder.getContext().getAuthentication().getName();

            //создаем новую аутентификацию на основе текущего логина и новой роли
            Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken (
                                                    userDAO.findNeedLoginByLoginAndRole(login, chosenRole.getRoleId().toString()),
                                                    userDAO.getPasswordByLogin(login)
                                                    ));

            // устанавливаем новые данные в контекст
            SecurityContextHolder.getContext().setAuthentication(authentication);

            // возвращаем новый токен
            return new ResponseEntity<>(new ChooseRoleResponseDTO(chosenRole.getRoleId(), newToken), HttpStatus.ACCEPTED);
        }

        return new ResponseEntity<>("Something went wrong....", HttpStatus.FORBIDDEN);
    }

    /*
    лезет в бд сверяться в правильноси введенного логина и пароля после чего созает аутентификацию
    и сохраняет ее в контекст, потом по ней будет осуществляться запрет в доступе к страницам
     */


    public Authentication authenticateUser(String login, String password) throws UnsupportedEncodingException, NoSuchAlgorithmException {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(login, userDAO.toSha1(password)));
//        log.debug(authentication);
        SecurityContextHolder.getContext().setAuthentication(authentication);

        return authentication;
    }

//    @GetMapping("/get_menu")
//    public Map getMenu(@RequestParam(name="roles", required = true, defaultValue = "11") int roles) {
//        System.out.println("in getMenu");
//       return menuDAO.getMenuFromByRole(roles);
//    }

    // вытаскиваем из базы менюшки по роли
//    @PostMapping("/get_menu")
//    public Map getMenu(@RequestParam(name="roles", defaultValue = "34"/*"Просмотр("VISIBLE")"*/) int roles) {
////        System.out.println("in getMenu");
////        System.out.println("roles = "+roles);
//
//        return menuDAO.getMenuFromByRole(roles);
//    }
}
