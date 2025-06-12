package com.usareboot.back.services;

import com.usareboot.back.models.*;
import com.usareboot.back.operators.AlbumItemOperator;
import com.usareboot.back.operators.AlbumOperator;
import com.usareboot.back.operators.MainOperator;
import com.usareboot.back.operators.VkOperator;
import com.usareboot.back.persistence.usareboot.entities.AlbumsItemsEntity;
import com.usareboot.back.persistence.usareboot.entities.DStatusesEntity;
import com.usareboot.back.persistence.usareboot.entities.ItemsEntity;
import com.usareboot.back.persistence.usareboot.repository.*;
import com.vk.api.sdk.exceptions.ApiException;
import com.vk.api.sdk.exceptions.ClientException;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.StoredProcedureQuery;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.ThreadLocalRandom;

@Service
@Slf4j
@RequiredArgsConstructor
public class MainService {
    private final ThreadLocal<Integer> threadLocal = ThreadLocal.withInitial(() -> ThreadLocalRandom.current().nextInt(10000, 100000));

    private final ImportListRepository importListRepository;
    private final ItemsRepository itemsRepository;
    private final DStatusRepository dStatusRepository;
    private final RepaymentsRepository repaymentsRepository;
    private final ApiTokenRepository apiTokenRepository;
    private final MainOperator mainOperator;
    private final AlbumItemOperator albumItemOperator;
    private final VkOperator vkOperator;
    @PersistenceContext
    private EntityManager entityManager;

    @Value("${spring.datasource.usareboot.schema}")
    String schemaName;

    public ArrayList<ImportDTO> getListImport(String listAlbom) {
        ArrayList<ImportDTO> scienceDiplomsList = new ArrayList<>();
        if (!Objects.equals(listAlbom, "[]"))
            listAlbom = listAlbom.replace("[", "").replace("]", "");
        var bdFuncResponse = importListRepository.importListProcedure(listAlbom);
        if (!bdFuncResponse.isEmpty()) {
            bdFuncResponse.forEach(x -> scienceDiplomsList.add(new ImportDTO(
                    x.getclient(),
                    x.getclient_id(),
                    x.getvikup(),
                    x.getrazdacha(),
                    x.getitem_name(),
                    x.getitem_count(),
                    x.getsp_help_id(),
                    x.getitem_cost(),
                    x.getdate_stop()
            )));
        }
        return scienceDiplomsList;
    }

    @PersistenceContext
    EntityManager em;

    @Transactional
    public void getImportList(String data, String albomName) {
        var isHave = importListRepository.getImportItemListEntitiesByVikup(albomName);
        log.info("isHave: " + isHave);
        if (isHave.isEmpty()) {
            System.out.println("выполняется процедура импорта");
            StoredProcedureQuery spq = em.createNamedStoredProcedureQuery("vpImportDataInList");
            spq.setParameter("data", data);
            System.out.println(data + ' ' + albomName);
            spq.setParameter("albom_name", albomName);
            spq.execute();
        }
    }

    public ArrayList<ItemListDTO> getItemListDao(int statusId) {
        ArrayList<ItemListDTO> scienceDiplomsList = new ArrayList<>();
        var bdFuncResponse = importListRepository.itemListProcedure(statusId);
        if (!bdFuncResponse.isEmpty()) {
            bdFuncResponse.forEach(x -> scienceDiplomsList.add(new ItemListDTO(
                    x.getitem_id(),
                    x.getclient_id(),
                    x.getitem_status_id(),
                    x.getpay_status_id(),
                    x.getalbom_id(),
                    x.getalbom_item_id(),
                    x.getvk_id(),
                    x.getorder_id(),
                    x.getclient_Url(),
                    x.getfi(),
                    x.getitem_name(),
                    x.getalbom_vk_url(),
                    x.getalbom_name(),
                    x.getitem_size(),
                    x.getitem_color(),
                    x.getitem_count(),
                    x.getitem_url(),
                    x.getitem_status(),
                    x.getpay_status(),
                    x.getitem_weight(),
                    x.getdate_comment(),
                    x.getphoto_path(),
                    x.getvk_photo_path(),
                    x.getcomment(),
                    x.getalbom_item_cost(),
                    x.getalbom_item_rate(),
                    x.getcost(),
                    x.getrepayment_name(),
                    x.getis_not_size(),
                    x.getis_price_depends_size()
            )));
        }
        return scienceDiplomsList;
    }

    public void saveItemAttribute(ItemListDTO itemListDTO) {
        try {
            ItemsEntity itemList = itemsRepository.getItemsEntityByItemId(itemListDTO.getItemId());
            if (itemListDTO.getItemStatus() != null) {
                var itemStatusId = dStatusRepository.findDStatusesEntityByStatusName(itemListDTO.getItemStatus()).getStatusId();
                itemList.setItemStatus(itemStatusId);
            }
            if (itemListDTO.getPayStatus() != null) {
                var payStatusId = dStatusRepository.findDStatusesEntityByStatusName(itemListDTO.getPayStatus()).getStatusId();
                itemList.setCostStatus(payStatusId);
            }
//            String repaymentName = itemListDTO.getRepaymentName();
            itemList.setRepaymentName(itemListDTO.getRepaymentName());
            itemList.setItemCount(itemListDTO.getItemCount());
            itemList.setItemColor(itemListDTO.getItemColor());
            log.info("itemListDTO.getItemSize(): {}", itemListDTO.getItemSize());
            if (itemListDTO.getItemSize() != null)
                itemList.setItemSize(itemListDTO.getItemSize());
            if (itemListDTO.getCost() != null)
                itemList.setItemCost(itemListDTO.getCost());
            if (itemListDTO.getAlbumItemRate() != null)
                itemList.setItemRate(itemListDTO.getAlbumItemRate());
            if (itemListDTO.getAlbumItemCost() != null)
                itemList.setItemCostCu(itemListDTO.getAlbumItemCost());
            log.info("itemList: {}", itemList);
            itemsRepository.save(itemList);
            log.info("Успешное сохранение данных в таблицу item");
            /*if (!repaymentName.isEmpty()) {
//                Optional<RepaymentsEntity> repaymentsEntityByRepaymentName = repaymentsRepository.findFirstByRepaymentName(repaymentName);

//                if (repaymentsEntityByRepaymentName.isEmpty()) {
                    RepaymentsEntity repayment = new RepaymentsEntity();
                    repayment.setRepaymentName(repaymentName);
                    repayment.setPercentageIncome(PERCENTAGE_INCOME_DEFAULT);
                    log.info("repayment.getPercentageIncome(): " + repayment.getPercentageIncome());
                    RepaymentsEntity save = repaymentsRepository.save(repayment);
                    itemList.setRepaymentId(save.getRepaymentId());
                    itemsRepository.save(itemList);
                    log.info("Успешное создание строки с repaymentName в таблице repayments и запись repaymentId в таблицу item");
                    itemList.setItemStatus(ITEM_STATUS_REPAYMENT);
                    itemsRepository.save(itemList);
//                }
         *//*   } else {
                if (repaymentsEntityByRepaymentName.isPresent()) {
                    itemList.setRepaymentId(null);
                    itemsRepository.save(itemList);
                    log.info("Успешное удаление трека и обнуление ссылки на repayments");
                }*//*
            }*/
        } catch (Exception e) {
            log.error("В бд не записался данный трек: {}: {}", itemListDTO.getRepaymentName(), e.getMessage());
        }
    }

    @Transactional
    public void saveDuplicateItemRow(Long itemId) {
        ItemsEntity duplicate = new ItemsEntity();
        ItemsEntity original = itemsRepository.findById(itemId)
                .orElseThrow(() -> new EntityNotFoundException("Item not found"));
// Копируем свойства
        BeanUtils.copyProperties(original, duplicate);

// Обнуляем ID (чтобы создалась новая запись)
        duplicate.setItemId(null);
        duplicate.setVkCommentId(null);
        var comment = "ДУБЛИКАТ\n" + original.getComment();
        duplicate.setComment(comment);

        itemsRepository.save(duplicate);
//        this.entityManager.persist(itemsEntity);

       /* ItemsEntity itemsEntity =new ItemsEntity();
        itemsEntity.setAlbomItemId(itemListDTO.getAlbumItemId());
        itemsEntity.setItemCost((BigDecimal) itemListDTO.getAlbumItemCost());
        itemsEntity.setOrderId(itemListDTO.getOrderId());
        itemsEntity.setItemSize(itemListDTO.getItemSize());
        itemsEntity.setItemName(itemListDTO.getItemName());
        if (itemListDTO.getItemStatus() != null) {
            var itemStatusId = dStatusRepository.findDStatusesEntityByStatusName(itemListDTO.getItemStatus()).getStatusId();
            itemsEntity.setItemStatus(itemStatusId);
        }
        if (itemListDTO.getPayStatus() != null) {
            var payStatusId = dStatusRepository.findDStatusesEntityByStatusName(itemListDTO.getPayStatus()).getStatusId();
            itemsEntity.setCostStatus(payStatusId);
        }
        itemsEntity.setVkUrl(itemListDTO.getAlbumVkUrl());
        itemsEntity.setItemUrl(itemListDTO.getItemUrl());
        itemsEntity.setComment(itemListDTO.getComment());
        itemsEntity.setItemColor(itemListDTO.getItemColor());
        itemsEntity.setItemColor(itemListDTO.getItemColor());
        itemsEntity.setVkCommentId(itemListDTO.getVkCommentId());*/
    }

    public void saveItemStatus(ItemListDTO itemListDTO) {
        try {
            var eventId = threadLocal.get();
            log.info("[Сценарий saveItemStatus][Шаг: Начало][EventID: {}]", eventId);

            log.info("[Сценарий saveItemStatus][Шаг: Получаем информацию о заказе][EventID: {}]", eventId);
            ItemsEntity item = mainOperator.getItem(itemListDTO);

            log.info("[Сценарий saveItemStatus][Шаг: Вытаскиваем информацию о товаре][EventID: {}]", eventId);
            AlbumsItemsEntity albumItem = mainOperator.getAlbumItem(item);

            log.info("[Сценарий saveItemStatus][Шаг: Маппинг наименования статуса товара и вытаскивание id][EventID: {}]", eventId);
            var itemStatusId = mainOperator.getItemStatus(itemListDTO);
            log.info("[Сценарий saveItemStatus][Шаг: Статус заказа клиента: {}, id: {}][EventID: {}]", itemListDTO.getItemStatus(), itemStatusId, eventId);

            log.info("[Сценарий saveItemStatus][Шаг: Сохранение курса и цену в товар (albumItems)][EventID: {}]", eventId);
//            mainOperator.updateRateAndCostInAlbumItem(albumItem, itemListDTO);

            log.info("[Сценарий saveItemStatus][Шаг: Сохраняем стоимость заказа для тех товаров которые выкупили][EventID: {}]", eventId);
            ItemsEntity itemsEntity = mainOperator.saveItemCost(item, itemStatusId, albumItem);

            log.info("[Сценарий saveItemStatus][Шаг: Формирование сообщения для создания комментария под фото клиенту][EventID: {}]", eventId);
            String messageClientForItem = mainOperator.getMessageClientForItem(itemsEntity, itemStatusId);

            if (!messageClientForItem.isEmpty()) {
                log.info("[Сценарий saveItemStatus][Шаг: Создание комментария под фото][EventID: {}]", eventId);
                vkOperator.createCommentInVk(messageClientForItem, itemsEntity, albumItem);
            }
            log.info("[Сценарий saveItemStatus][Шаг: Финиш][EventID: {}]", eventId);
        } catch (Exception e) {
            log.error("Сценарий завершился с ошибкой, itemListDTO: {}: {}", itemListDTO, e.getMessage());
        }
    }

    public ArrayList<ItemWeightListDTO> getItemWeightListDao() {
        ArrayList<ItemWeightListDTO> scienceDiplomsList = new ArrayList<>();
        var bdFuncResponse = importListRepository.weightItemListProcedure();
        if (!bdFuncResponse.isEmpty()) {
            bdFuncResponse.forEach(x -> scienceDiplomsList.add(new ItemWeightListDTO(
                    x.getitem_id(),
                    x.getclient_id(),
                    x.getstatus_id(),
                    x.getalbom_id(),
                    x.getvk_id(),
                    x.getorder_id(),
                    x.getfi(),
                    x.getfio(),
                    x.getitem_name(),
                    x.getalbom_name(),
                    x.getitem_size(),
                    x.getitem_count(),
                    x.getstatus_name(),
                    x.getitem_weight(),
                    x.getdate_delivery(),
                    x.getsp_help_id(),
                    x.getrazdacha()
            )));
        }
        return scienceDiplomsList;
    }

    public void saveItemWeightAndStatus(long itemId, ItemsRequestDTO itemsRequestDTO) {
        ItemsEntity ie = itemsRepository.getItemsEntityByItemId(itemId);
        ie.setItemWeight(itemsRequestDTO.getItemWeight());
        ie.setItemStatus(itemsRequestDTO.getStatusId());
        if (itemsRequestDTO.getDateDelivery() != null)
            ie.setDateDelivery(new java.sql.Date(itemsRequestDTO.getDateDelivery().getTime()));
        else ie.setDateDelivery(null);
        log.info(String.valueOf(ie));

        itemsRepository.save(ie);
    }

    public ArrayList<DStatusesEntity> getStatusesItem(int type) {
        return dStatusRepository.getDStatusesEntityByActiveAndStatusTypeOrderBySort(1, type);
    }

    @Transactional
    public void setItemDate() {
        long millis = System.currentTimeMillis();
        java.sql.Date date = new java.sql.Date(millis);
        System.out.println(date);
        itemsRepository.item_set_date_all();
        log.info("обновление прошло успешно");
    }

    @Async
    public void deleteItem(long itemId) throws ClientException, ApiException {
        var eventId = threadLocal.get();

        ItemsEntity itemsEntity = mainOperator.getItem(itemId);
        long vkPostId = Optional.ofNullable(itemsEntity).map(ItemsEntity::getVkCommentId).orElse(0L);
        if (vkPostId != 0) {
            log.info("[Сценарий deleteItem][Шаг: Удаления поста в ВК][EventID: {}]", eventId);
            String res = vkOperator.photosDeleteComment((int) vkPostId);

            log.info("[Сценарий deleteAlbum][Шаг: Результат удаления поста в ВК: {}][EventID: {}]", res, eventId);
        }
        log.info("[Сценарий deleteAlbum][Шаг: Удаления коментария в БД][EventID: {}]", eventId);
        mainOperator.deleteItem(itemId);

        log.info("[Сценарий deleteAlbum][Шаг: Комментарий успешно удалился в БД][EventID: {}]", eventId);

        log.info("[Сценарий deleteItem][Шаг: Финиш][EventID: {}]", eventId);
    }
}
