package com.usareboot.back.persistence.usareboot.entities;

import jakarta.persistence.*;
import lombok.Getter;

import java.util.Objects;

@Getter
@Entity
@Table(name = "orders", catalog = "usareboot")
public class OrdersEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "order_id")
    private long orderId;
    @Basic
    @Column(name = "albom_id")
    private Long albumId;
    @Basic
    @Column(name = "client_id")
    private Long clientId;
    @Basic
    @Column(name = "sp_help_id")
    private String spHelpId;
    @Basic
    @Column(name = "count_items_new")
    private Short countItemsNew;
//    @Basic
//    @Column(name = "order_cost")
//    private Number orderCost;
    @Basic
    @Column(name = "status_id")
    private Long statusId;
    @Basic
    @Column(name = "count_items_compleate")
    private Short countItemsCompleate;
    @Basic
    @Column(name = "count_items")
    private Short countItems;

    public void setOrderId(long orderId) {
        this.orderId = orderId;
    }

    public void setAlbumId(Long albomId) {
        this.albumId = albomId;
    }

    public void setClientId(Long clientId) {
        this.clientId = clientId;
    }

    public void setSpHelpId(String spHelpId) {
        this.spHelpId = spHelpId;
    }

    public void setCountItemsNew(Short countItemsNew) {
        this.countItemsNew = countItemsNew;
    }

//    public void setOrderCost(Number orderCost) {
//        this.orderCost = orderCost;
//    }

    public void setStatusId(Long orderStatus) {
        this.statusId = orderStatus;
    }

    public void setCountItemsCompleate(Short countItemsCompleate) {
        this.countItemsCompleate = countItemsCompleate;
    }

    public void setCountItems(Short countItems) {
        this.countItems = countItems;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        OrdersEntity that = (OrdersEntity) o;
        return orderId == that.orderId && Objects.equals(albumId, that.albumId) && Objects.equals(clientId, that.clientId) && Objects.equals(spHelpId, that.spHelpId) && Objects.equals(countItemsNew, that.countItemsNew) /*&& Objects.equals(orderCost, that.orderCost)*/ && Objects.equals(statusId, that.statusId) && Objects.equals(countItemsCompleate, that.countItemsCompleate) && Objects.equals(countItems, that.countItems);
    }

    @Override
    public int hashCode() {
        return Objects.hash(orderId, albumId, clientId, spHelpId, countItemsNew/*, orderCost*/, statusId, countItemsCompleate, countItems);
    }
}
