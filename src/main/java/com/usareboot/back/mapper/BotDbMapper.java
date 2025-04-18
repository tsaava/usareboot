package com.usareboot.back.mapper;

import com.usareboot.back.persistence.usareboot.entities.AlbumsEntity;
import com.usareboot.back.models.AlbumsItemsDTO;
import com.usareboot.back.models.vk.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class BotDbMapper {
    public AlbumsItemsDTO mapBotVkToAlbumItem(VkBotResponseDTO vkBotResponseDTO, AlbumsEntity albumsEntity) {

        var photoId = Optional.ofNullable(vkBotResponseDTO.getVk_event())
                .map(VkEvent::getObject)
                .map(VkPhotoObject::getId).orElse(0L);

        var photoPatch = Optional.ofNullable(vkBotResponseDTO.getVk_event())
                .map(VkEvent::getObject)
                .map(VkPhotoObject::getAttachments)
                .stream()
                .flatMap(s -> s.stream()
                        .map(VkAttachments::getPhoto)
                        .map(VkPhotoSaveDTO::getOrig_photo)
                        .map(VkPhotoSizeResponse::getUrl)
                ).findFirst().orElse("");

        var rate = Arrays.stream(albumsEntity.getCourseAlbum().split("/")).toList();
        double itemRate = 1;
        if(!rate.isEmpty()){
            itemRate = Double.parseDouble(rate.get(0));}
        return AlbumsItemsDTO.builder()
                .albumId(albumsEntity.getAlbumId())
                .vkItemId(photoId)
                .photoPath(photoPatch)
                .albumItemName(vkBotResponseDTO.getItemName())
                .albumItemCount(vkBotResponseDTO.getItemCount())
                .albumItemRate(itemRate)/*Поправить!!! стоит заглушка на курс выкупа*/
                .vkPhotoPath("https://vk.com/photo-" + albumsEntity.getAlbumId() + "-" + photoId)
                .itemUrl(vkBotResponseDTO.getItemUrl())
                .albumItemCost(vkBotResponseDTO.getCost())
                .albumItemColor(vkBotResponseDTO.getItemColor())
                .build();
    }
}
