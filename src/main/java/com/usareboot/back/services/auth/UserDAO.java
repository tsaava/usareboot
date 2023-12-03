package com.usareboot.back.services.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.usareboot.back.entities.auth.DRolesEntity;
import com.usareboot.back.entities.auth.UsersEntity;
import com.usareboot.back.repositories.auth.PersonRepository;
import com.usareboot.back.repositories.auth.RolesRepository;
import com.usareboot.back.repositories.science.ScienceRepository;
import com.usareboot.back.repositories.auth.UserRepository;

import javax.xml.bind.DatatypeConverter;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class UserDAO {

    @Autowired
    private PersonRepository personRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RolesRepository rolesRepository;
    @Autowired
    private ScienceRepository scienceRepository;

//    кодировщик
    public String toSha1(String input) throws UnsupportedEncodingException, NoSuchAlgorithmException {
        MessageDigest msdDigest = MessageDigest.getInstance("SHA-1");
        msdDigest.update(input.getBytes("UTF-8"), 0, input.length());
        String msd =  "\\x"+DatatypeConverter.printHexBinary(msdDigest.digest()).toLowerCase();
        return msd;
    }

    public boolean saveUser(UsersEntity user) throws UnsupportedEncodingException, NoSuchAlgorithmException {
        try {
            user.setPassword(toSha1(user.getPassword()));
            userRepository.save(user);
            return true;
        } catch (Exception e) {
            System.out.println("error during saving user");
            return false;
        }
    }


    public UsersEntity findUserByLogin(String login) {
        return userRepository.findUsersEntityByLogin(login).get();
    }

    public UsersEntity findUserByLoginAndPassword(String login, String password) throws UnsupportedEncodingException, NoSuchAlgorithmException {
//        System.out.println( toSha1(password));
        return userRepository.findUsersEntityByLoginAndPassword(login, toSha1(password)).get();
    }

    public ArrayList<UsersEntity> findAll() {
        return userRepository.findAll();
    }

//    public Set<DRolesEntity> findAllUserRolesUserIdfindAllUserRoles(UsersEntity user) {
//        var personUsers = userRepository.findAllByPersonId(user.getPersonId());
//        personUsers.stream().forEach(System.out::println);
//        var personRoles = personUsers.stream()
//                                            .map(x -> rolesRepository.findDRolesEntityByRoleId(Long.parseLong(x.getRoles())))
//                                            .collect(Collectors.toSet());
//        System.out.println(personRoles);
//        return personRoles;
//    }

    public Set<DRolesEntity> findAllUserRolesUserId(UsersEntity user) {
//        var userID = userRepository.findAllByUserId(user.getUserId());
        //из таблицы UsersEntity вытаскиваем поле roles с колекцией ролей
        var roleArray =  (userRepository.findAllRolesByUserId(user.getUserId()).get(0).getRoles());//.toList();//.get(0).getRoles();

//        System.out.println(roleArray);
//        var personRoles = roleArray.stream()
//                .map(x -> rolesRepository.findDRolesEntityByRoleId(x.getRoleId()))
//                .collect(Collectors.toSet());
//        System.out.println(personRoles.stream().findFirst().get().getRoleName());
        return roleArray;//personRoles;
    }

    public String getRoleByLoginAndPassword(String login, String password) {
        return null; //rolesRepository.findDRolesEntityByRoleId(Long.valueOf(userRepository.findUsersEntityByLoginAndPassword(login, password).get().getRoles())).getRoleName();
    }

//    public String getRoleByLogin(String login) {
////        return rolesRepository.findDRolesEntityByRoleId(Long.valueOf(userRepository.findUsersEntityByLogin(login).get().getRoles())).getRoleName();
//
//        var roleArray = userRepository.findUsersEntityByLogin(login).get().getRoles();
//        return rolesRepository.findDRolesEntityByRoleId(roleArray.stream().findFirst().get().getRoleId()).getRoleName();
//
//    }

    public Long getRoleIdByLogin(String login) {
//        return rolesRepository.findDRolesEntityByRoleId(Long.valueOf(userRepository.findUsersEntityByLogin(login).get().getRoles())).getRoleName();

        var roleArray = userRepository.findUsersEntityByLogin(login).get().getRoles();
        return roleArray.stream().findFirst().get().getRoleId();

    }

    public Set<Long> getRoleEntityByLogin(String login) {
        return personRepository.findPersonUsersEntitiesByUserId(userRepository.findUsersEntityByLogin(login).get().getUserId()).stream().map(x -> x.getRoleId()).collect(Collectors.toSet());
    }

    public DRolesEntity getRoleById(Long roleId) {
        return rolesRepository.findDRolesEntityByRoleId(roleId);
    }


    // применяется при смене роли - находит нужную учетную запись по роли и текущей в контексте аутентификации
    public String findNeedLoginByLoginAndRole(String login, String role) {
//        UsersEntity user = userRepository.findUsersEntityByLogin(login).get();
//        ArrayList<UsersEntity> usersEntities = userRepository.findAllByPersonId(user.getPersonId());
//        UsersEntity userOut = usersEntities.stream().filter(x -> rolesRepository.findDRolesEntityByRoleName(role).getRoleId() == Long.parseLong(x.getRoles())).findFirst().get();
//        return userOut.getLogin();


        var roleArray = userRepository.findUsersEntityByLogin(login).get().getRoles();
        System.out.println(roleArray.stream().findFirst());
//        return rolesRepository.findDRolesEntityByRoleId(roleArray.stream().findFirst().get().getRoleId()).getRoleName();
        return rolesRepository.findDRolesEntityByRoleId(roleArray.stream().findFirst().get().getRoleId()).getRoleName();

    }

    //Проверяет наличие роли у данного юзера на всех аккаунтах
//    public boolean checkUserRole(String login, String role) {
//        return findAllUserRoles(userRepository.findUsersEntityByLogin(login).get()).stream().map(x -> x.getRoleName()).collect(Collectors.toList()).contains(role);
//    }
//    public String getUserByPidAndRole()
    public String getPasswordByLogin(String login) throws UnsupportedEncodingException, NoSuchAlgorithmException {
        System.out.println( userRepository.findUsersEntityByLogin(login).get().getPassword());

        return userRepository.findUsersEntityByLogin(login).get().getPassword();
    }

    public Long getRoleNameByRoleId(Long roleId){
        return rolesRepository.findDRolesEntityByRoleId(roleId).getRoleId();
    }





//    // convert Entity to Dto
//    private  StudentsEntity.toDto(): StudentsDto =
//    StudentsDto(
//            id = this.id,
//            name = this.name,
//            group = this.group.toDto(),
//        )

}
