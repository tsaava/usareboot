package com.usareboot.back.operators;

import com.usareboot.back.entities.AlbumsItemsEntity;
import com.usareboot.back.models.AlbumsItemsDTO;
import com.usareboot.back.repositories.AlbumsItemsRepository;
import com.usareboot.back.repositories.AlbumsRepository;
import com.usareboot.back.repositories.DStatusRepository;
import com.usareboot.back.services.vk.VkService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.time.LocalDate;
@Slf4j
@Service
@RequiredArgsConstructor
public class AlbumItemOperator {
    private final DStatusRepository dStatusRepository;
    private final AlbumsRepository albumsRepository;
    private final VkService vkService;
    private final AlbumsItemsRepository albumsItemsRepository;

    public void saveAlbumItem(AlbumsItemsDTO albumsItemsDTO) {
        AlbumsItemsEntity albumsItemsEntity = new AlbumsItemsEntity();
        albumsItemsEntity.setAlbumItemStatus(dStatusRepository.findDStatusesEntityByStatusId(albumsItemsDTO.getAlbumItemStatus()));
        albumsItemsEntity.setAlbumItemName(albumsItemsDTO.getAlbumItemName());
        albumsItemsEntity.setAlbumItemCost(albumsItemsDTO.getAlbumItemCost());
        albumsItemsEntity.setAlbumItemRate(albumsItemsDTO.getAlbumItemRate());
        albumsItemsEntity.setItemColor(albumsItemsDTO.getAlbumItemColor());
        albumsItemsEntity.setCost(albumsItemsDTO.getAlbumItemRate() * albumsItemsDTO.getAlbumItemCost());
        albumsItemsEntity.setItemUrl(albumsItemsDTO.getItemUrl());
        albumsItemsEntity.setVkItemId(albumsItemsDTO.getVkItemId());
        albumsItemsEntity.setAlbumId(albumsRepository.findAlbumsEntityByAlbumId(albumsItemsDTO.getAlbumId()));
        LocalDate localDate = LocalDate.now();
        albumsItemsEntity.setDateCreate(Date.valueOf(localDate));
        albumsItemsEntity.setVkPhotoPath(albumsItemsDTO.getVkPhotoPath());
        albumsItemsEntity.setDescription(albumsItemsDTO.getDescription());
        albumsItemsEntity.setNoSize(albumsItemsDTO.isNoSize());
        try {
            var photoUrl = vkService.getCommentPhotoVk(albumsItemsDTO.getVkItemId());
            albumsItemsEntity.setPhotoPath(photoUrl);
        } catch (Exception e) {
            log.error("Не удалось получить ссылку на фото в Вк", e);
        }

        albumsItemsRepository.save(albumsItemsEntity);
    }
}
