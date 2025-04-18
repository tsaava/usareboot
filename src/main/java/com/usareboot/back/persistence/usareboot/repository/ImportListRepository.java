package com.usareboot.back.persistence.usareboot.repository;

import com.usareboot.back.models.interfaces.ImportListResponse;
import com.usareboot.back.models.interfaces.ItemListResponse;
import com.usareboot.back.persistence.usareboot.entities.ImportItemListEntity;
import com.usareboot.back.models.interfaces.ItemWeightListResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.ArrayList;

public interface ImportListRepository extends JpaRepository<ImportItemListEntity, Long> {

    @Value("${spring.datasource.usareboot.schema}")
    String schema = "";
    ArrayList<ImportItemListEntity> getImportItemListEntitiesByVikup(String albomName);

    @Query(value = "SELECT * FROM prod.vf_import_list(  :list_albom);", nativeQuery = true)
    ArrayList<ImportListResponse> importListProcedure(@Param("list_albom") String list_albom);

    /**
     * Получение списка всех товаров для ввода статуса и веса
     * @return ArrayList<ItemListResponse>
     */
    @Query(value = "SELECT * FROM prod.vf_item_list_status_and_weight();", nativeQuery = true)
    ArrayList<ItemWeightListResponse> weightItemListProcedure();


    /**
     * Получение оставленных комментов в вк из таблицы item
     * @return ArrayList<ItemListResponse>
     */
    @Query(value = "SELECT * FROM prod.vf_item_list(:statusId);", nativeQuery = true)
    ArrayList<ItemListResponse> itemListProcedure(@Param("statusId") int statusId);

}

