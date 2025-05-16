package com.usareboot.back.operators;

import com.usareboot.back.models.ItemListDTO;
import com.usareboot.back.persistence.usareboot.entities.AlbumsItemsEntity;
import com.usareboot.back.persistence.usareboot.entities.ItemsEntity;
import com.usareboot.back.persistence.usareboot.entities.OrdersEntity;
import com.usareboot.back.persistence.usareboot.entities.auth.UsersEntity;
import com.usareboot.back.persistence.usareboot.repository.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

import static com.usareboot.back.models.constant.Constant.*;

@Slf4j
@Service
@RequiredArgsConstructor
public class MainOperator {
    private final ItemsRepository itemsRepository;
    private final AlbumsItemsRepository albumsItemsRepository;
    private final DStatusRepository dStatusRepository;
    private final OrdersRepository ordersRepository;
    private final UsersRepository usersRepository;

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

    public ItemsEntity getItem(Long itemId) {
        return itemsRepository.findFirstByItemId(itemId);
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
        var orderId = itemsEntity.getOrderId();
        OrdersEntity order = ordersRepository.findFirstByOrderId(orderId);
        var clientId = order.getClientId();
        UsersEntity user = usersRepository.findFirstByUserId(clientId);
        var userName = user.getIName();
        if (itemStatusId == ITEM_IN_REDEEMED_STATUS_ID) {
            String itemName = itemsEntity.getItemName();
            String itemSize = itemsEntity.getItemSize();
            Integer itemCount = itemsEntity.getItemCount();
            String itemColor = itemsEntity.getItemColor();
            BigDecimal itemCost = itemsEntity.getItemCost();
            message = userName + ",\n"
                    + itemName + "\n"
                    + ((itemSize != null && !itemSize.isEmpty()) ? ("Размер: " + itemSize + "\n") : "")
                    + ((itemColor != null && !itemColor.isEmpty()) ? ("Цвет: " + itemColor + "\n") : "")
                    + "Цена: " + itemCost + (itemCount > 1 ? " * " + itemCount : "") + "\n"
                    + "выкуплено";
        }
        if (itemStatusId == ITEM_IN_NOT_REDEEMED_SIZE_STATUS_ID) {
            message = userName + ",\n"
                    + "Ваш товар не был выкуплен: нет размера/товар закончился";
        }
        if (itemStatusId == ITEM_IN_NOT_REDEEMED_COURSE_STATUS_ID) {
            message = userName + ",\n"
                    + "Ваш товар не был выкуплен: закончилась акция";
        }
        if (itemStatusId == ITEM_IN_NOT_REDEEMED_COST_STATUS_ID) {
            message = userName + ",\n"
                    + "Ваш товар не был выкуплен: изменилась цена";
        }
        if (itemStatusId == ITEM_IN_CANCELED_BY_STORE_STATUS_ID) {
            message = userName + ",\n"
                    + "Ваш товар был выкуплен но отменен магазином";
        }
        return message;
    }

    public boolean deleteItem(Long itemId){
        try {
            log.debug("удаление item:{}", itemId);
            itemsRepository.deleteById(itemId);
            return true;
        }
        catch (Exception e){
            log.error("Ошибка удаления комментария в бд {}", e.toString());
            throw new RuntimeException("Ошибка удаления комментария в бд");
        }
    }
}
