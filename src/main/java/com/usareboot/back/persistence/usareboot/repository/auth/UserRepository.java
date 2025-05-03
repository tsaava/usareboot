package com.usareboot.back.persistence.usareboot.repository.auth;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.usareboot.back.persistence.usareboot.entities.auth.UsersEntity;

import java.util.ArrayList;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UsersEntity, Long> {

    ArrayList<UsersEntity> findAll();
    Optional<UsersEntity> findUsersEntityByLoginAndPassword(String login, String password);
    Optional<UsersEntity> findFirstByLogin(String login);


//    ArrayList<UsersEntity> findAllByPersonId(Long personId);

    ArrayList<UsersEntity> findAllRolesByUserId(Long userID);

//    Optional<UsersEntity> findUsersEntityByPersonIdAndRoles(Long personId, String role);

 
//    @Query("SELECT fio FROM vf_test_complete_json_person(:filters::Json, :page_rows, :page_num)")
//    @Query(nativeQuery = true, value = "SELECT vf_test_complete_json_person()")
//    ArrayList<EmployeeResponse> listEployeers(String filters, int page_rows, int page_num);

}
