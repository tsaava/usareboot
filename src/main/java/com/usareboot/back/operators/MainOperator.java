package com.usareboot.back.operators;

import com.usareboot.back.models.ItemListDTO;
import com.usareboot.back.persistence.usareboot.entities.AlbumsItemsEntity;
import com.usareboot.back.persistence.usareboot.entities.ItemsEntity;
import com.usareboot.back.persistence.usareboot.repository.AlbumsItemsRepository;
import com.usareboot.back.persistence.usareboot.repository.DStatusRepository;
import com.usareboot.back.persistence.usareboot.repository.ItemsRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

import static com.usareboot.back.models.constant.Constant.ITEM_IN_REDEEMED_STATUS_ID;

@Slf4j
@Service
@RequiredArgsConstructor
public class MainOperator {
    private final ItemsRepository itemsRepository;
    private final AlbumsItemsRepository albumsItemsRepository;
    private final DStatusRepository dStatusRepository;

    @Value("${vk.api.groupId}")
    private String groupId;
    @Value("${vk.api.version}")
    private String apiVersion;

    public ItemsEntity getItem(ItemListDTO itemListDTO) {
        ItemsEntity itemsEntity = itemsRepository.getItemsEntityByItemId(itemListDTO.getItemId());
        if (itemListDTO.getItemColor() != null && !itemListDTO.getItemColor().isEmpty())
            itemsEntity.setItemColor(itemListDTO.getItemColor());
        if (itemListDTO.getItemSize() != null && !itemListDTO.getItemSize().isEmpty())
            itemsEntity.setItemSize(itemListDTO.getItemSize());
        if (itemListDTO.getItemCount() != null && itemListDTO.getItemCount() > 0)
            itemsEntity.setItemCount(itemListDTO.getItemCount());
        return itemsEntity;
    }

    public AlbumsItemsEntity getAlbumItem(ItemsEntity itemsEntity) {
        return albumsItemsRepository.findFirstByAlbumItemId(itemsEntity.getAlbomItemId());
    }

    public Long getItemStatus(ItemListDTO itemListDTO) {
        return dStatusRepository.findDStatusesEntityByStatusName(itemListDTO.getItemStatus()).getStatusId();
    }

    public ItemsEntity saveItemCost(ItemsEntity itemsEntity, Long itemStatusId, AlbumsItemsEntity albumsItemsEntity) {
        if (itemStatusId == ITEM_IN_REDEEMED_STATUS_ID) {
            log.info("Заказ выкуплен, сохраняем стоимость");
            BigDecimal itemRate = albumsItemsEntity.getAlbumItemRate();
            BigDecimal itemCost = albumsItemsEntity.getAlbumItemCost();
            BigDecimal multiply = itemRate.multiply(itemCost);
            itemsEntity.setItemCost(multiply);
            itemsRepository.save(itemsEntity);
        }
        return itemsEntity;
    }

    public String getMessageClientForItem(ItemsEntity itemsEntity, Long itemStatusId) {
        String message = "";
        if (itemStatusId == ITEM_IN_REDEEMED_STATUS_ID) {
            /*сообщение о том, что товар выкуплен
            * Название
              Размер
              Цвет
              Цена из базы*/
            String itemName = itemsEntity.getItemName();
            String itemSize = itemsEntity.getItemSize();
            Integer itemCount = itemsEntity.getItemCount();
            String itemColor = itemsEntity.getItemColor();
            BigDecimal itemCost = itemsEntity.getItemCost();
            message = itemName + "\n"
                    + "Размер: " + itemSize + "\n"
                    + "Цвет: " + itemColor + "\n"
                    + "Цена: " + itemCost + (itemCount > 1 ? " * " + itemCount : "") + "\n"
                    + "выкуплено";
        }
        return message;
    }


}
