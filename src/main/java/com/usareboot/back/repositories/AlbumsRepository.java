package com.usareboot.back.repositories;
import com.usareboot.back.entities.AlbumsEntity;
import com.usareboot.back.entities.DStatusesEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Date;
import java.util.ArrayList;

public interface AlbumsRepository extends JpaRepository<AlbumsEntity, Long> {
    ArrayList<AlbumsEntity> getAlbumsEntitiesByAlbumDateAfterOrderByAlbumDate(Date albumDate);
    AlbumsEntity findAlbumsEntityByAlbumId(long albumId);
    AlbumsEntity getAlbumsEntityByAlbumMappingDictionaryIdAndStatuses(long albumMappingId, DStatusesEntity statusId);
}
