package com.usareboot.back.repositories;

import com.usareboot.back.entities.auth.TestEntity;
import org.springframework.data.repository.CrudRepository;

public interface MainRepository extends CrudRepository<TestEntity, Long> {



//    @Query(nativeQuery = true, value = "CALL public.vp_import_data_in_list ( cast(:data_list AS json), :albom_name)")
//    void importProcedure(@Param("data_list") String data_list,
//                         @Param("albom_name") String albom_name);
//    Session session = sessionFactory.openSession();
////    Query exQuery = session.q.createSQLQuery("CALL " +
////            "insertHouseHello(:timestmp,:hname,:hno,:hvalue)");
////    @NamedNativeQueries({
////            @NamedNativeQuery(
////                    name = "callStockStoreProcedure",
////                    query = "CALL public.vp_import_data_in_list ( cast(:data_list AS json), :albom_name)"
////
////            )
////    })
//    Query query = session.getNamedQuery("callStockStoreProcedure")
}
