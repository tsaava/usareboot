package com.usareboot.back.persistence.usareboot.repository;

import com.usareboot.back.persistence.usareboot.entities.OrdersEntity;
import com.usareboot.back.persistence.usareboot.entities.RateBuyingEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

public interface RateBuyingRepository extends JpaRepository<RateBuyingEntity, Long> {
}
