package com.usareboot.back.repositories;

import com.usareboot.back.entities.DStatusesEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.ArrayList;

public interface DStatusRepository extends JpaRepository<DStatusesEntity, Long> {

    ArrayList<DStatusesEntity> getDStatusesEntityByActiveAndStatusTypeOrderByStatusName(int active, int type);
    DStatusesEntity findDStatusesEntityByStatusName(String statusName);
    DStatusesEntity findDStatusesEntityByStatusId(Long statusId);
}

