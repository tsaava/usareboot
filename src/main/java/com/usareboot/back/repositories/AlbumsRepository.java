package com.usareboot.back.repositories;
import com.usareboot.back.entities.AlbumsEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Date;
import java.util.ArrayList;

public interface AlbumsRepository extends JpaRepository<AlbumsEntity, Long> {
    ArrayList<AlbumsEntity> getAllByAlbumDate(Date albumDate);
}
