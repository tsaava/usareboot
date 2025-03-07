package com.usareboot.back.operators;

import com.usareboot.back.entities.AlbumsItemsEntity;
import com.usareboot.back.entities.DStatusesEntity;
import com.usareboot.back.models.AlbumsItemsDTO;
import com.usareboot.back.repositories.AlbumsItemsRepository;
import com.usareboot.back.repositories.AlbumsRepository;
import com.usareboot.back.repositories.DStatusRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.time.LocalDate;

@Slf4j
@Service
@RequiredArgsConstructor
public class AlbumOperator {
    private final AlbumsRepository albumsRepository;

    public void delAlbum(long albumId) {
        albumsRepository.deleteById(albumId);
    }
}
