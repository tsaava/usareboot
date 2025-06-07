package com.usareboot.back.operators;

import com.usareboot.back.models.AlbumsItemsDTO;
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

import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;

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
    private final AlbumItemOperator albumItemOperator;
    private final VkOperator vkOperator;

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
            BigDecimal itemCost = BigDecimal.valueOf(0);
            if (itemsEntity.getItemCost() != null) {
                itemCost = itemsEntity.getItemCost().setScale(0, RoundingMode.UP);
            }
            message = userName + ",\n"
                    + itemName + "\n"
                    + ((itemSize != null && !itemSize.isEmpty()) ? ("Размер: " + itemSize + "\n") : "")
                    + ((itemColor != null && !itemColor.isEmpty()) ? ("Цвет: " + itemColor + "\n") : "")
//                    + "Цена: " + itemCost + "\n"
//                    + "выкуплено";
                    + (itemCount > 1 ? ("Цена (1шт): " + itemCost + "\nвыкуплено " + itemCount + " шт") :
                    "Цена: " + itemCost + "\nвыкуплено");
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
        if (itemStatusId == ITEM_IN_REDEEMED_WITH_CHANGE_COST_STATUS_ID) {
            /*message = userName + ",\n"
                    + "Ваш товар был выкуплен, но ";*/
            String itemName = itemsEntity.getItemName();
            String itemSize = itemsEntity.getItemSize();
            Integer itemCount = itemsEntity.getItemCount();
            String itemColor = itemsEntity.getItemColor();
            BigDecimal itemCost = BigDecimal.valueOf(0);
            if (itemsEntity.getItemCost() != null) {
                itemCost = itemsEntity.getItemCost().setScale(0, RoundingMode.UP);
            }
            message = userName + ",\n"
                    + itemName + "\n"
                    + ((itemSize != null && !itemSize.isEmpty()) ? ("Размер: " + itemSize + "\n") : "")
                    + ((itemColor != null && !itemColor.isEmpty()) ? ("Цвет: " + itemColor + "\n") : "")
                    + (itemCount > 1 ? ("Цена (1шт): " + itemCost + "\nвыкуплено " + itemCount + " шт") :
                    "Цена: " + itemCost + "\nвыкуплено");
        }
        return message;
    }

    public boolean deleteItem(Long itemId) {
        try {
            log.debug("удаление item:{}", itemId);
            itemsRepository.deleteById(itemId);
            return true;
        } catch (Exception e) {
            log.error("Ошибка удаления комментария в бд {}", e.toString());
            throw new RuntimeException("Ошибка удаления комментария в бд");
        }
    }

    public void updateRateAndCostInAlbumItem(AlbumsItemsEntity albumItem, ItemListDTO itemListDTO) throws IOException {
        if (itemListDTO.getAlbumItemCost().compareTo(albumItem.getAlbumItemCost()) <= 0 ||
                itemListDTO.getAlbumItemRate().compareTo(albumItem.getAlbumItemRate()) <= 0) {
            log.debug("Цена или курс меньше объявленной в товаре");
            AlbumsItemsDTO build = AlbumsItemsDTO.builder()
                    .albumItemRate(itemListDTO.getAlbumItemRate())
                    .albumItemCost(itemListDTO.getAlbumItemCost())
                    .build();
            albumItemOperator.updateAlbumItem(albumItem, build);

            var albumsItemsDTO = AlbumsItemsDTO.builder()
                    .albumItemName(albumItem.getAlbumItemName())
                    .description(albumItem.getDescription())
                    .allowableSizes(albumItem.getAllowableSizes())
                    .albumItemColor(albumItem.getItemColor())
                    .albumItemCost(albumItem.getAlbumItemCost())
                    .albumItemRate(albumItem.getAlbumItemRate())
                    .itemUrl(albumItem.getItemUrl())
                    .build();
            log.info("Формируем описание товара с измененой ценой/курсом");
            var allDesc = vkOperator.getAllDesc(albumsItemsDTO);

            log.info("Обновление описания фото в ВК");
            var res = vkOperator.editPhotoInVk(String.valueOf(albumItem.getVkItemId()), allDesc);
            log.info("Обновление описания фото в ВК, res: {}", res);

            albumItem.setDescription(allDesc);
            albumsItemsRepository.save(albumItem);
        }
    }
}
