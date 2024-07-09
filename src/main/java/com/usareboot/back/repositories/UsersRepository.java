package com.usareboot.back.repositories;

import com.usareboot.back.entities.auth.UsersEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UsersRepository extends JpaRepository<UsersEntity, Long> {
    UsersEntity getUsersEntityByVkId(long vkId);
}
