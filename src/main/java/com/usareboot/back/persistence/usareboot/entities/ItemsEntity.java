package com.usareboot.back.persistence.usareboot.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigInteger;
import java.sql.Date;
import java.time.LocalDateTime;
import java.util.Objects;

@Getter
@Setter
@Entity
@Table(name = "items",  catalog = "usareboot")
public class ItemsEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "item_id")
    private long itemId;
    @Basic
    @Column(name = "albom_item_id")
    private Long albomItemId;
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
    @Basic
    @Column(name = "point_type")
    private String pointType;
    @Basic
    @Column(name = "vk_url")
    private String vkUrl;
    @Basic
    @Column(name = "item_url")
    private String itemUrl;
    @Basic
    @Column(name = "comment")
    private String comment;
    @Basic
    @Column(name = "date_comment")
    private LocalDateTime dateComment;
    @Getter
    @Basic
    @Column(name = "cost_status")
    private Long costStatus;
    @Getter
    @Basic
    @Column(name = "repayment_id")
    private Long repaymentId;
    @Getter
    @Basic
    @Column(name = "repayment_name")
    private String repaymentName;
    @Basic
    @Column(name = "item_cost")
    private String itemCost;

    @Basic
    @Column(name = "comment_id")
    private Long commentId;

    public void setItemId(long itemId) {
        this.itemId = itemId;
    }
    public void setAlbomItemId(long albomItemId) {
        this.albomItemId = albomItemId;
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


    public void setPointType(String pointType) {
        this.pointType = pointType;
    }


    public void setVkUrl(String vkUrl) {
        this.vkUrl = vkUrl;
    }


    public void setItemUrl(String itemUrl) {
        this.itemUrl = itemUrl;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

      public void setDateComment(LocalDateTime dateComment) {
        this.dateComment = dateComment;
    }

    public void setCostStatus(Long costStatus) {
        this.costStatus = costStatus;
    }
    public void setRepaymentId(Long repaymentId) {
        this.repaymentId = repaymentId;
    }
    public void setRepaymentName(String repaymentName) {
        this.repaymentName = repaymentName;
    }
    public void setItemCost(String itemCost) {
        this.itemCost = itemCost;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ItemsEntity that = (ItemsEntity) o;
        return itemId == that.itemId && Objects.equals(itemStatus, that.itemStatus) && Objects.equals(orderId, that.orderId) && Objects.equals(itemColor, that.itemColor) && Objects.equals(itemSize, that.itemSize) && Objects.equals(itemWeight, that.itemWeight) && Objects.equals(itemName, that.itemName) && Objects.equals(itemCount, that.itemCount) && Objects.equals(importItemListId, that.importItemListId) && Objects.equals(dateDelivery, that.dateDelivery) && Objects.equals(pointType, that.pointType) && Objects.equals(vkUrl, that.vkUrl) && Objects.equals(itemUrl, that.itemUrl) && Objects.equals(comment, that.comment) && Objects.equals(dateComment, that.dateComment) && Objects.equals(costStatus, that.costStatus);
    }

    @Override
    public int hashCode() {
        return Objects.hash(itemId, orderId, itemColor, itemSize, itemWeight, itemStatus, itemName, itemCount, importItemListId, dateDelivery, pointType, vkUrl, itemUrl, comment, dateComment, costStatus);
    }
}
