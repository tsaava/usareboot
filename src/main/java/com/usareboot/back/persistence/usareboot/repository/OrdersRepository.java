package com.usareboot.back.persistence.usareboot.repository;

import com.usareboot.back.persistence.usareboot.entities.OrdersEntity;
import org.springframework.data.repository.CrudRepository;

public interface OrdersRepository extends CrudRepository<OrdersEntity, Long> {
    OrdersEntity getOrdersEntityByClientIdAndAlbumId(long clientId, long albumId);
    OrdersEntity findFirstByOrderId(long orderId);
}
