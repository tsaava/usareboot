package com.usareboot.back.persistence.usareboot.repository;

import com.usareboot.back.persistence.usareboot.entities.DStatusesEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.ArrayList;

public interface DStatusRepository extends JpaRepository<DStatusesEntity, Long> {

    ArrayList<DStatusesEntity> getDStatusesEntityByActiveAndStatusTypeOrderByStatusName(int active, int type);
    DStatusesEntity findDStatusesEntityByStatusName(String statusName);
    DStatusesEntity findDStatusesEntityByStatusId(Long statusId);
}

