package com.usareboot.back.repositories.auth;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.usareboot.back.entities.auth.PersonUsersEntity;

import java.util.Set;

@Repository
public interface PersonRepository extends JpaRepository<PersonUsersEntity, Long> {

    Set<PersonUsersEntity> findPersonUsersEntitiesByUserId(Long personId);

}
