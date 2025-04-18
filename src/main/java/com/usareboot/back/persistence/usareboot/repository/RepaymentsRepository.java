package com.usareboot.back.persistence.usareboot.repository;

import com.usareboot.back.persistence.usareboot.entities.RepaymentsEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RepaymentsRepository extends JpaRepository<RepaymentsEntity, Long> {

//    Optional<RepaymentsEntity> findRepaymentsEntityByRepaymentName(String repaymentName);
    Optional<RepaymentsEntity> findFirstByRepaymentName(String repaymentName);
}
