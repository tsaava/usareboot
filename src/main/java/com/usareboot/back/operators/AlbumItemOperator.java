package com.usareboot.back.operators;

import com.usareboot.back.persistence.usareboot.entities.AlbumsItemsEntity;
import com.usareboot.back.persistence.usareboot.entities.DStatusesEntity;
import com.usareboot.back.models.AlbumsItemsDTO;
import com.usareboot.back.persistence.usareboot.repository.AlbumsItemsRepository;
import com.usareboot.back.persistence.usareboot.repository.AlbumsRepository;
import com.usareboot.back.persistence.usareboot.repository.DStatusRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;

import static com.usareboot.back.models.constant.Constant.ALBUM_ITEM_DEFAULT_STATUS_ID;

@Slf4j
@Service
@RequiredArgsConstructor
public class AlbumItemOperator {
    private final DStatusRepository dStatusRepository;
    private final AlbumsRepository albumsRepository;
    private final AlbumsItemsRepository albumsItemsRepository;
    private final VkOperator vkOperator;

    public AlbumsItemsEntity saveAlbumItem(AlbumsItemsDTO albumsItemsDTO) {
        AlbumsItemsEntity albumsItemsEntity = new AlbumsItemsEntity();
        Long albumItemStatus = albumsItemsDTO.getAlbumItemStatus()!=null? albumsItemsDTO.getAlbumItemStatus() : ALBUM_ITEM_DEFAULT_STATUS_ID;
        DStatusesEntity status = dStatusRepository.findDStatusesEntityByStatusId(albumItemStatus);
        albumsItemsEntity.setAlbumItemStatus(status);
        albumsItemsEntity.setAlbumItemName(albumsItemsDTO.getAlbumItemName());
        BigDecimal albumItemCost = albumsItemsDTO.getAlbumItemCost();
        albumsItemsEntity.setAlbumItemCost(albumItemCost);
        albumsItemsEntity.setAlbumItemRate(albumsItemsDTO.getAlbumItemRate());
        albumsItemsEntity.setItemColor(albumsItemsDTO.getAlbumItemColor());
//        albumsItemsEntity.setCost(albumsItemsDTO.getAlbumItemRate() * albumsItemsDTO.getAlbumItemCost());
        var cost = albumItemCost.multiply(albumsItemsDTO.getAlbumItemRate())
                .setScale(0, RoundingMode.CEILING);
        albumsItemsEntity.setCost(cost);
        albumsItemsEntity.setItemUrl(albumsItemsDTO.getItemUrl());
        albumsItemsEntity.setVkItemId(albumsItemsDTO.getVkItemId());
        albumsItemsEntity.setAlbumId(albumsRepository.findAlbumsEntityByAlbumId(albumsItemsDTO.getAlbumId()));
        albumsItemsEntity.setDateCreate(LocalDateTime.now());
        albumsItemsEntity.setVkPhotoPath(albumsItemsDTO.getVkPhotoPath());
        albumsItemsEntity.setDescription(albumsItemsDTO.getDescription());
        albumsItemsEntity.setItemDescription(albumsItemsDTO.getItemDescription());
        albumsItemsEntity.setNoSize(albumsItemsDTO.isNoSize());
        albumsItemsEntity.setAllowableSizes(albumsItemsDTO.getAllowableSizes());
        albumsItemsEntity.setPriceDependsSize(albumsItemsDTO.isPriceDependsSize());
        try {
            var photoUrl = vkOperator.getCommentPhotoVk(albumsItemsDTO.getVkItemId());
            albumsItemsEntity.setPhotoPath(photoUrl);
        } catch (Exception e) {
            log.error("Не удалось получить ссылку на фото в Вк: {}", e.getMessage());
            albumsItemsEntity.setPhotoPath(albumsItemsDTO.getPhotoPath());
        }
        return albumsItemsRepository.save(albumsItemsEntity);
    }

    public AlbumsItemsEntity getAlbumItem(AlbumsItemsDTO albumsItemsDTO) {
        return albumsItemsRepository.findFirstByAlbumItemId(albumsItemsDTO.getAlbumItemId());
    }

    public void updateAlbumItem(AlbumsItemsEntity albumsItemsEntity, AlbumsItemsDTO albumsItemsDTO) {
        albumsItemsEntity.setAlbumItemCost(albumsItemsDTO.getAlbumItemCost());
        albumsItemsEntity.setAlbumItemRate(albumsItemsDTO.getAlbumItemRate());
        albumsItemsRepository.save(albumsItemsEntity);
    }
}
