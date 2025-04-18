package com.usareboot.back.persistence.usareboot.repository;

import com.usareboot.back.persistence.usareboot.entities.AlbumMappingDictionaryEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AlbumMappingDictionaryRepository extends JpaRepository<AlbumMappingDictionaryEntity, Long> {
    Optional<AlbumMappingDictionaryEntity> getAlbumMappingDictionaryEntityByLinkContains(String itemUrl);
}
