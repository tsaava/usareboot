package com.usareboot.back.persistence.usareboot.repository;

import com.usareboot.back.persistence.usareboot.entities.AlbumsItemsEntity;
import com.usareboot.back.persistence.usareboot.entities.ItemsEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public interface AlbumsItemsRepository extends JpaRepository<AlbumsItemsEntity, Long> {
    ArrayList<AlbumsItemsEntity> getAlbumsItemsEntitiesByAlbum_AlbumIdOrderByDateCreateDesc(long albumId);
    AlbumsItemsEntity findFirstByVkItemId(Long vkItemId);
    AlbumsItemsEntity findFirstByAlbumItemId(Long albumItemId);
    Optional<List<AlbumsItemsEntity>> findAllByAlbumAlbumId(Long albumId);
}
