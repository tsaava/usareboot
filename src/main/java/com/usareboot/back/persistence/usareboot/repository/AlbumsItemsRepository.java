package com.usareboot.back.persistence.usareboot.repository;

import com.usareboot.back.persistence.usareboot.entities.AlbumsItemsEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.ArrayList;

public interface AlbumsItemsRepository extends JpaRepository<AlbumsItemsEntity, Long> {
    ArrayList<AlbumsItemsEntity> getAlbumsItemsEntitiesByAlbum_AlbumIdOrderByDateCreateDesc(long albumId);
    AlbumsItemsEntity findFirstByVkItemId(Long vkItemId);
    AlbumsItemsEntity findFirstByAlbumItemId(Long albumItemId);
}
