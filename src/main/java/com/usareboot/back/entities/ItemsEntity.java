package com.usareboot.back.entities;

import jakarta.persistence.*;
import lombok.Getter;

import java.math.BigInteger;
import java.sql.Date;
import java.util.Objects;

@Getter
@Entity
@Table(name = "items", schema = "public", catalog = "usareboot")
public class ItemsEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "item_id")
    private long itemId;
    @Basic
    @Column(name = "order_id")
    private Long orderId;
    @Basic
    @Column(name = "item_color")
    private String itemColor;
    @Basic
    @Column(name = "item_size")
    private String itemSize;
    @Basic
    @Column(name = "item_weight")
    private BigInteger itemWeight;
    @Basic
    @Column(name = "item_status")
    private Long itemStatus;
    @Basic
    @Column(name = "item_name")
    private String itemName;
    @Basic
    @Column(name = "item_count")
    private Integer itemCount;
    @Basic
    @Column(name = "import_item_list_id")
    private Long importItemListId;
    @Basic
    @Column(name = "date_delivery")
    private Date dateDelivery;

    public void setItemId(long itemId) {
        this.itemId = itemId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public void setItemColor(String itemColor) {
        this.itemColor = itemColor;
    }

    public void setItemSize(String itemSize) {
        this.itemSize = itemSize;
    }

    public void setItemWeight(BigInteger itemWeight) {
        this.itemWeight = itemWeight;
    }

    public void setItemStatus(Long itemStatus) {
        this.itemStatus = itemStatus;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public void setItemCount(Integer itemCount) {
        this.itemCount = itemCount;
    }

    public void setImportItemListId(Long importItemListId) {
        this.importItemListId = importItemListId;
    }

    public void setDateDelivery(Date dateDelivery) {
        this.dateDelivery = dateDelivery;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ItemsEntity that = (ItemsEntity) o;
        return itemId == that.itemId && Objects.equals(orderId, that.orderId) && Objects.equals(itemColor, that.itemColor) && Objects.equals(itemSize, that.itemSize) && Objects.equals(itemWeight, that.itemWeight) && Objects.equals(itemStatus, that.itemStatus) && Objects.equals(itemName, that.itemName) && Objects.equals(itemCount, that.itemCount) && Objects.equals(importItemListId, that.importItemListId) && Objects.equals(dateDelivery, that.dateDelivery);
    }

    @Override
    public int hashCode() {
        return Objects.hash(itemId, orderId, itemColor, itemSize, itemWeight, itemStatus, itemName, itemCount, importItemListId, dateDelivery);
    }
}
