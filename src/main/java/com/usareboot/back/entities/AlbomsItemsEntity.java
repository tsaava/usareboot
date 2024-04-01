package com.usareboot.back.entities;

import jakarta.persistence.*;

import java.math.BigInteger;
import java.sql.Date;
import java.util.Objects;

@Entity
@Table(name = "alboms_items", schema = "public", catalog = "usareboot")
public class AlbomsItemsEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "albom_item_id")
    private long albomItemId;
    @Basic
    @Column(name = "albom_id")
    private long albomId;
    @Basic
    @Column(name = "vk_item_id")
    private long vkItemId;
    @Basic
    @Column(name = "tg_item_id")
    private Long tgItemId;
    @Basic
    @Column(name = "photo_path")
    private String photoPath;
    @Basic
    @Column(name = "description")
    private String description;
    @Basic
    @Column(name = "description_short")
    private String descriptionShort;
    @Basic
    @Column(name = "albom_item_weight")
    private BigInteger albomItemWeight;
    @Basic
    @Column(name = "albom_item_name")
    private String albomItemName;
    @Basic
    @Column(name = "albom_item_count")
    private Integer albomItemCount;
    @Basic
    @Column(name = "date_create")
    private Date dateCreate;
    @Basic
    @Column(name = "albom_item_cost")
    private Object albomItemCost;
    @Basic
    @Column(name = "albom_item_rate")
    private Object albomItemRate;
    @Basic
    @Column(name = "albom_item_status")
    private short albomItemStatus;
    @Basic
    @Column(name = "vk_photo_path")
    private String vkPhotoPath;

    public long getAlbomItemId() {
        return albomItemId;
    }

    public void setAlbomItemId(long albomItemId) {
        this.albomItemId = albomItemId;
    }

    public long getAlbomId() {
        return albomId;
    }

    public void setAlbomId(long albomId) {
        this.albomId = albomId;
    }

    public long getVkItemId() {
        return vkItemId;
    }

    public void setVkItemId(long vkItemId) {
        this.vkItemId = vkItemId;
    }

    public Long getTgItemId() {
        return tgItemId;
    }

    public void setTgItemId(Long tgItemId) {
        this.tgItemId = tgItemId;
    }

    public String getPhotoPath() {
        return photoPath;
    }

    public void setPhotoPath(String photoPath) {
        this.photoPath = photoPath;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDescriptionShort() {
        return descriptionShort;
    }

    public void setDescriptionShort(String descriptionShort) {
        this.descriptionShort = descriptionShort;
    }

    public BigInteger getAlbomItemWeight() {
        return albomItemWeight;
    }

    public void setAlbomItemWeight(BigInteger albomItemWeight) {
        this.albomItemWeight = albomItemWeight;
    }

    public String getAlbomItemName() {
        return albomItemName;
    }

    public void setAlbomItemName(String albomItemName) {
        this.albomItemName = albomItemName;
    }

    public Integer getAlbomItemCount() {
        return albomItemCount;
    }

    public void setAlbomItemCount(Integer albomItemCount) {
        this.albomItemCount = albomItemCount;
    }

    public Date getDateCreate() {
        return dateCreate;
    }

    public void setDateCreate(Date dateCreate) {
        this.dateCreate = dateCreate;
    }

    public Object getAlbomItemCost() {
        return albomItemCost;
    }

    public void setAlbomItemCost(Object albomItemCost) {
        this.albomItemCost = albomItemCost;
    }

    public Object getAlbomItemRate() {
        return albomItemRate;
    }

    public void setAlbomItemRate(Object albomItemRate) {
        this.albomItemRate = albomItemRate;
    }

    public short getAlbomItemStatus() {
        return albomItemStatus;
    }

    public void setAlbomItemStatus(short albomItemStatus) {
        this.albomItemStatus = albomItemStatus;
    }

    public String getVkPhotoPath() {
        return vkPhotoPath;
    }

    public void setVkPhotoPath(String vkPhotoPath) {
        this.vkPhotoPath = vkPhotoPath;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AlbomsItemsEntity that = (AlbomsItemsEntity) o;
        return albomItemId == that.albomItemId && albomId == that.albomId && vkItemId == that.vkItemId && albomItemStatus == that.albomItemStatus && Objects.equals(tgItemId, that.tgItemId) && Objects.equals(photoPath, that.photoPath) && Objects.equals(description, that.description) && Objects.equals(descriptionShort, that.descriptionShort) && Objects.equals(albomItemWeight, that.albomItemWeight) && Objects.equals(albomItemName, that.albomItemName) && Objects.equals(albomItemCount, that.albomItemCount) && Objects.equals(dateCreate, that.dateCreate) && Objects.equals(albomItemCost, that.albomItemCost) && Objects.equals(albomItemRate, that.albomItemRate) && Objects.equals(vkPhotoPath, that.vkPhotoPath);
    }

    @Override
    public int hashCode() {
        return Objects.hash(albomItemId, albomId, vkItemId, tgItemId, photoPath, description, descriptionShort, albomItemWeight, albomItemName, albomItemCount, dateCreate, albomItemCost, albomItemRate, albomItemStatus, vkPhotoPath);
    }
}
