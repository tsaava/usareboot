package com.usareboot.back.repositories;

import com.usareboot.back.entities.OrdersEntity;
import org.springframework.data.repository.CrudRepository;

public interface OrdersRepository extends CrudRepository<OrdersEntity, Long> {
    OrdersEntity getOrdersEntityByClientIdAndAlbumId(long clientId, long albumId);
}
