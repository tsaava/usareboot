package com.usareboot.back.persistence.usareboot.repository;

import com.usareboot.back.persistence.usareboot.entities.ItemsEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.CrudRepository;

public interface ItemsRepository extends CrudRepository<ItemsEntity, Long> {

    ItemsEntity getItemsEntityByItemId(long itemId);
    ItemsEntity findFirstByItemId(long itemId);

    @Procedure
    void item_set_date_all();

}

