package com.usareboot.back.repositories;

import com.usareboot.back.entities.DStatusesEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.ArrayList;

public interface DStatusRepository extends JpaRepository<DStatusesEntity, Long> {

    ArrayList<DStatusesEntity> getDStatusesEntityByActiveAndStatusType(int active, int type);
}

