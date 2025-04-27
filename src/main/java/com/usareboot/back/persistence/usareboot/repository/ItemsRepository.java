package com.usareboot.back.persistence.usareboot.repository;

import com.usareboot.back.persistence.usareboot.entities.ItemsEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.query.Procedure;

public interface ItemsRepository extends JpaRepository<ItemsEntity, Long> {

    ItemsEntity getItemsEntityByItemId(long itemId);

    @Procedure
    void item_set_date_all();

}

