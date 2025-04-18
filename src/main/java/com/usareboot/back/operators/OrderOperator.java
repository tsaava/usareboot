package com.usareboot.back.operators;

import com.google.gson.JsonObject;
import com.usareboot.back.persistence.usareboot.entities.OrdersEntity;
import com.usareboot.back.persistence.usareboot.entities.auth.UsersEntity;
import com.usareboot.back.persistence.usareboot.repository.OrdersRepository;
import com.usareboot.back.persistence.usareboot.repository.UsersRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class OrderOperator {
    private final UsersRepository usersRepository;
    private final OrdersRepository ordersRepository;
    private final CommonOperator commonOperator;

    public long getOrderId(long fromId, Long albumId) {

        //проверка на существующего пользователя в базе
        var user = usersRepository.getUsersEntityByVkId(fromId);
        if (user == null) {
            JsonObject userName = commonOperator.getUserName((int) fromId);
            user = new UsersEntity();
            user.setVkId(fromId);
            user.setiName(userName.get("first_name").getAsString());
            user.setfName(userName.get("last_name").getAsString());
            usersRepository.save(user);
            log.info("Новый пользователь успешно создан: {}", fromId);
        }
        OrdersEntity orders = ordersRepository.getOrdersEntityByClientIdAndAlbumId(user.getUserId(), albumId);
        long ordersId;
        if (orders == null) {
            orders = new OrdersEntity();
            orders.setClientId(user.getUserId());
            orders.setAlbumId(albumId);
            orders.setStatusId(24L);
            log.info(String.valueOf(orders));
            ordersId = ordersRepository.save(orders).getOrderId();
        } else
            ordersId = orders.getOrderId();

        log.info("Сохранение комментария в orders прошло успешно");
        return ordersId;
    }
}
