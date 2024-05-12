package com.usareboot.back.repositories;

import com.usareboot.back.entities.AlbumsEntity;
import com.usareboot.back.entities.AlbumsItemsEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.ArrayList;
import java.util.Date;

public interface AlbumsItemsRepository extends JpaRepository<AlbumsItemsEntity, Long> {
    ArrayList<AlbumsItemsEntity> getAlbumsItemsEntitiesByAlbum_AlbumId(long albumId);

}
