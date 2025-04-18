package com.usareboot.back.operators;

import com.usareboot.back.persistence.usareboot.entities.AlbumsEntity;
import com.usareboot.back.persistence.usareboot.repository.AlbumsRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class AlbumOperator {
    private final AlbumsRepository albumsRepository;

    public long getAlbumVkId(long albumId) {
        AlbumsEntity albumsEntityByAlbum = albumsRepository.findAlbumsEntityByAlbumId(albumId);
        return albumsEntityByAlbum.getAlbumVkId();
    }

    public void delAlbum(long albumId) {
        albumsRepository.deleteById(albumId);
    }
}
