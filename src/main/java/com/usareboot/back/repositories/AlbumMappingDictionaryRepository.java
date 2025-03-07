package com.usareboot.back.repositories;

import com.usareboot.back.entities.AlbumMappingDictionaryEntity;
import com.usareboot.back.entities.AlbumsItemsEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.ArrayList;
import java.util.Optional;

public interface AlbumMappingDictionaryRepository extends JpaRepository<AlbumMappingDictionaryEntity, Long> {
    Optional<AlbumMappingDictionaryEntity> getAlbumMappingDictionaryEntityByLinkContains(String itemUrl);
}
