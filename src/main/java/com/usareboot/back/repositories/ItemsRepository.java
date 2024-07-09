package com.usareboot.back.repositories;

import com.usareboot.back.entities.ItemsEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.query.Param;

import java.util.Date;

public interface ItemsRepository extends JpaRepository<ItemsEntity, Long> {

    ItemsEntity getItemsEntitiesByItemId(long itemId);

//    ItemsEntity getItemsEntitiesBypho(long itemId);
    @Procedure
    void item_set_date_all();

//@Query("UPDATE ItemsEntity SET dateDelivery=null WHERE dateDelivery IS NULL and itemStatus=10")
//void setDate(@Param ("date") Date date);
//@Query(nativeQuery = true, value = "CALL item_set_date_all()")
//void setDate(@Param ("date") Date date);



//    @Procedure
//    int GET_TOTAL_CARS_BY_MODEL(String model);
//    Query<ItemsEntity> query = session.createNativeQuery("CALL GetAllFoos()").addEntity(ItemsEntity.class);
//    List<Foo> allFoos = query.list();
}

