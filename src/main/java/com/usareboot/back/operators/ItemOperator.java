package com.usareboot.back.operators;

import com.usareboot.back.entities.AlbumsItemsEntity;
import com.usareboot.back.entities.ItemsEntity;
import com.usareboot.back.models.ParsedComment;
import com.usareboot.back.models.vk.VkBotResponseDTO;
import com.usareboot.back.parser.CommentParser;
import com.usareboot.back.repositories.ItemsRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;

@Slf4j
@Service
@RequiredArgsConstructor
public class ItemOperator {
    private final ItemsRepository itemsRepository;

    public void saveItem(AlbumsItemsEntity albumsItems, long dateInSeconds, String commentText, long orderId, VkBotResponseDTO data) {
        LocalDateTime commentDate = LocalDateTime.ofInstant(Instant.ofEpochSecond(dateInSeconds), ZoneId.systemDefault());// Преобразование даты в LocalDateTime

        ItemsEntity itemsEntity = new ItemsEntity();
        itemsEntity.setAlbomItemId(albumsItems.getAlbum().getAlbumId());
        itemsEntity.setComment(commentText);
        itemsEntity.setDateComment(commentDate);
        itemsEntity.setOrderId(orderId);
        itemsEntity.setItemName(albumsItems.getAlbumItemName());
        itemsEntity.setVkUrl(albumsItems.getItemUrl());
        itemsEntity.setItemStatus(24L);

        if (data == null) {
            CommentParser parser = new CommentParser();// Вызов парсера комментариев
            ParsedComment parsedComment = parser.parse(commentText);

            if (parsedComment.getSize() != null)
                itemsEntity.setItemSize(parsedComment.getSize());
            else
                itemsEntity.setItemSize(albumsItems.getItemSize());
            if (parsedComment.getLink() != null)
                itemsEntity.setItemUrl(parsedComment.getLink());
            else
                itemsEntity.setItemUrl(albumsItems.getItemUrl());
            if (parsedComment.getColor() != null)
                itemsEntity.setItemColor(parsedComment.getColor());
            else
                itemsEntity.setItemColor(albumsItems.getItemColor());

            if (parsedComment.getCount() != null)
                itemsEntity.setItemCount(Integer.valueOf(parsedComment.getCount()));
        } else {
            itemsEntity.setItemSize(data.getItemSize());
            itemsEntity.setItemUrl(data.getItemUrl());
            itemsEntity.setItemColor(data.getItemColor());
            itemsEntity.setItemCount(data.getItemCount());
        }
//        itemsEntity.setVkUrl("https://vk.com/photo-" + groupId + "_" + photoId);

        itemsRepository.save(itemsEntity);
        log.info("Сохранение комментария в itemsEntity прошло успешно");
    }
}
