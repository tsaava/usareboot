package com.usareboot.back.persistence.usareboot.repository;

import com.usareboot.back.persistence.usareboot.entities.ImportItemListEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ImportRepository extends JpaRepository<ImportItemListEntity, Void> {

    //@NamedNativeQueries({
////            @NamedNativeQuery(
////                    name = "callStockStoreProcedure",
////                    query = "CALL public.vp_import_data_in_list ( cast(:data_list AS json), :albom_name)"
////
////            )
////    })

//    @Query(value = "CALL vp_import_data_in_list( cast(:data_list AS json), :albom_name);", nativeQuery = true)
//    void importProcedure(@Param("data_list") String data_list,
//                               @Param("albom_name") String albom_name);
}
