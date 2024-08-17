package com.usareboot.back.repositories;

import com.usareboot.back.entities.RepaymentsEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RepaymentsRepository extends JpaRepository<RepaymentsEntity, Long> {

//    Optional<RepaymentsEntity> findRepaymentsEntityByRepaymentName(String repaymentName);
    Optional<RepaymentsEntity> findFirstByRepaymentName(String repaymentName);
}
