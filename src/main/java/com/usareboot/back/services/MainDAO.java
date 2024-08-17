package com.usareboot.back.services;

import com.usareboot.back.entities.DStatusesEntity;
import com.usareboot.back.entities.ItemsEntity;
import com.usareboot.back.entities.RepaymentsEntity;
import com.usareboot.back.models.ImportDTO;
import com.usareboot.back.models.ItemListDTO;
import com.usareboot.back.models.ItemWeightListDTO;
import com.usareboot.back.models.ItemsRequestDTO;
import com.usareboot.back.repositories.*;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.StoredProcedureQuery;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Objects;
import java.util.Optional;

import static com.usareboot.back.models.constant.Constant.ITEM_STATUS_REPAYMENT;
import static com.usareboot.back.models.constant.Constant.PERCENTAGE_INCOME_DEFAULT;

@Service
@Slf4j
public class MainDAO {
    private ImportListRepository importListRepository;
    private ItemsRepository itemsRepository;
    private DStatusRepository dStatusRepository;
    private RepaymentsRepository repaymentsRepository;

    private AlbumsItemsRepository albumsItemsRepository;
    private OrdersRepository ordersRepository;

    @Autowired
    public MainDAO(ImportListRepository importListRepository,
                   ItemsRepository itemsRepository,
                   DStatusRepository dStatusRepository,
                   AlbumsItemsRepository albumsItemsRepository,
                   OrdersRepository ordersRepository,
                   RepaymentsRepository repaymentsRepository) {
        this.importListRepository = importListRepository;
        this.itemsRepository = itemsRepository;
        this.dStatusRepository = dStatusRepository;
        this.albumsItemsRepository = albumsItemsRepository;
        this.ordersRepository = ordersRepository;
        this.repaymentsRepository = repaymentsRepository;
    }

    public ArrayList<ImportDTO> getListImport(String listAlbom) {
        ArrayList<ImportDTO> scienceDiplomsList = new ArrayList<>();
        if (!Objects.equals(listAlbom, "[]"))
            listAlbom = listAlbom.replace("[", "").replace("]", "");
        var bdFuncResponse = importListRepository.importListProcedure(listAlbom);
        if (bdFuncResponse.size() > 0) {
            bdFuncResponse.forEach(x -> scienceDiplomsList.add(new ImportDTO(
//                   x.getimport_item_list_id(),
                    x.getclient(),
                    x.getclient_id(),
                    x.getvikup(),
                    x.getrazdacha(),
//                    x.getpack(),
//                    x.getnote(),
//                    x.getsender(),
//                    x.getdate(),
//                    x.getaddress(),
//                    x.getfio(),
//                    x.getphone(),
//                    x.getemail(),
//                    x.getstatus(),
//                    x.getnum_order(),
//                    x.getisdownload(),
//                    x.getitem_color(),
//                    x.getitem_size(),
//                    x.getitem_weight(),
                    x.getitem_name(),
                    x.getitem_count(),
                    x.getsp_help_id(),
//                    x.getcomment(),
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
//        System.out.println(albomName.replace("\"",""));
        var isHave = importListRepository.getImportItemListEntitiesByVikup(albomName);
        System.out.println("isHave: " + isHave);
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
        if (bdFuncResponse.size() > 0) {
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
                    (x.getalbom_item_cost().doubleValue()*x.getalbom_item_rate().doubleValue()),
                    x.getrepayment_name()
            )));
        }
        return scienceDiplomsList;
    }

    public void saveItemList(ItemListDTO itemListDTO) {
        try {
            ItemsEntity itemList = itemsRepository.getItemsEntitiesByItemId(itemListDTO.getItemId());
            if (itemListDTO.getItemStatus() != null) {
                var itemStatusId = dStatusRepository.findDStatusesEntityByStatusName(itemListDTO.getItemStatus()).getStatusId();
                itemList.setItemStatus(itemStatusId);
            }
            if (itemListDTO.getPayStatus() != null) {
                var payStatusId = dStatusRepository.findDStatusesEntityByStatusName(itemListDTO.getPayStatus()).getStatusId();
                itemList.setCostStatus(payStatusId);
            }
            String repaymentName = itemListDTO.getRepaymentName();
            itemList.setRepaymentName(repaymentName);
            itemsRepository.save(itemList);
            Optional<RepaymentsEntity> repaymentsEntityByRepaymentName = repaymentsRepository.findFirstByRepaymentName(repaymentName);

            if (!repaymentName.isEmpty()) {
                if (repaymentsEntityByRepaymentName.isEmpty()) {
                    RepaymentsEntity repayment = new RepaymentsEntity();
                    repayment.setRepaymentName(repaymentName);
                    repayment.setPercentageIncome(PERCENTAGE_INCOME_DEFAULT);
                    System.out.println("repayment.getPercentageIncome(): " + repayment.getPercentageIncome());
                    RepaymentsEntity save = repaymentsRepository.save(repayment);
                    itemList.setRepaymentId(save.getRepaymentId());
                    itemsRepository.save(itemList);
                    log.info("Успешное создание строки с repaymentName в таблице repayments и запись repaymentId в таблицу item ");
                    itemList.setItemStatus(ITEM_STATUS_REPAYMENT);
                    itemsRepository.save(itemList);
                }
            }
            else {
                if (repaymentsEntityByRepaymentName.isPresent()) {
                    itemList.setRepaymentId(null);
                    itemsRepository.save(itemList);
                    log.info("Успешное удаление трека и обнуление ссылки на repayments");
                }
            }
        } catch (Exception e) {
            log.error("В бд не записался данный трек: {}: {}",itemListDTO.getRepaymentName(), e.getMessage());
        }
    }

    public ArrayList<ItemWeightListDTO> getItemWeightListDao() {
        ArrayList<ItemWeightListDTO> scienceDiplomsList = new ArrayList<>();
        var bdFuncResponse = importListRepository.weightItemListProcedure();
        if (bdFuncResponse.size() > 0) {
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
        ItemsEntity ie = itemsRepository.getItemsEntitiesByItemId(itemId);
        ie.setItemWeight(itemsRequestDTO.getItemWeight());
        ie.setItemStatus(itemsRequestDTO.getStatusId());
        if (itemsRequestDTO.getDateDelivery() != null)
            ie.setDateDelivery(new java.sql.Date(itemsRequestDTO.getDateDelivery().getTime()));
        else ie.setDateDelivery(null);
        System.out.println(ie);
        itemsRepository.save(ie);
    }

    public ArrayList<DStatusesEntity> getStatusesItem(int type) {
//        System.out.println(dStatusRepository.getDStatusesEntityByActiveAndStatusType(1,type));
//        return dStatusRepository.getDStatusesEntityByActiveAndStatusTypeOrderByStatusName(1,type);
        return dStatusRepository.getDStatusesEntityByActiveAndStatusTypeOrderByStatusName(1, type);
    }


    @Transactional
    public void setItemDate() {
//        java.util.Date date = new java.util.Date();
        long millis = System.currentTimeMillis();
        java.sql.Date date = new java.sql.Date(millis);
        System.out.println(date);
//        System.out.println(date);
//        itemsRepository.setDate(date);
        itemsRepository.item_set_date_all();
//        itemsRepository.setDate(new java.sql.Date(date.getTime()));

        System.out.println("обновление прошло успешно");

    }


}
