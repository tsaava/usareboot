package com.usareboot.back.persistence.usareboot.repository;

import com.usareboot.back.persistence.usareboot.entities.ApiTokenEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ApiTokenRepository extends JpaRepository<ApiTokenEntity, Long> {

    Optional<ApiTokenEntity> findApiTokenEntityByVkClientId(long vkClientId);
    Optional<ApiTokenEntity> findApiTokenEntityByGroupId(Long groupId);
}
