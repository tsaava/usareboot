package com.usareboot.back.repositories;

import com.usareboot.back.entities.AlbumMappingDictionaryEntity;
import com.usareboot.back.entities.AlbumsItemsEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.ArrayList;

public interface AlbumMappingDictionaryRepository extends JpaRepository<AlbumMappingDictionaryEntity, Long> {
    AlbumMappingDictionaryEntity getAlbumMappingDictionaryEntityByLinkContains(String itemUrl);
}
