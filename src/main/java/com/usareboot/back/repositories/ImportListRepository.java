package com.usareboot.back.repositories;

import com.usareboot.back.models.interfaces.ImportListResponse;
import com.usareboot.back.models.interfaces.ItemListResponse;
import com.usareboot.back.entities.ImportItemListEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.ArrayList;

public interface ImportListRepository extends JpaRepository<ImportItemListEntity, Long> {

    ArrayList<ImportItemListEntity> getImportItemListEntitiesByVikup(String albomName);

    @Query(value = "SELECT * FROM public.vf_import_list(  :list_albom);", nativeQuery = true)
    ArrayList<ImportListResponse> importListProcedure(@Param("list_albom") String list_albom);

    /**
     * Получение списка всех товаров для ввода статуса и веса
     * @return ArrayList<ItemListResponse>
     */
    @Query(value = "SELECT * FROM public.vf_item_list_status_and_weight();", nativeQuery = true)
    ArrayList<ItemListResponse> itemListProcedure();

}

