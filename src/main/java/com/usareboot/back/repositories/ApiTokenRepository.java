package com.usareboot.back.repositories;

import com.usareboot.back.entities.AlbumsEntity;
import com.usareboot.back.entities.ApiTokenEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ApiTokenRepository extends JpaRepository<ApiTokenEntity, Long> {

    Optional<ApiTokenEntity> findApiTokenEntityByVkClientId(long vkClientId);
}
