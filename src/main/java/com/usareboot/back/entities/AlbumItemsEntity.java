package com.usareboot.back.entities;

import jakarta.persistence.*;
import lombok.Getter;

import java.math.BigInteger;
import java.sql.Date;
import java.util.Objects;

@Getter
@Entity
@Table(name = "albom_items", schema = "public", catalog = "usareboot")
public class AlbumItemsEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "albom_item_id")
    private long albumItemId;
    @Basic
    @Column(name = "albom_id")
    private Long albumId;
    @Basic
    @Column(name = "vk_item_id")
    private Long vkItemId;
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
    private BigInteger albumItemWeight;
    @Basic
    @Column(name = "albom_item_name")
    private String albumItemName;
    @Basic
    @Column(name = "albom_item_count")
    private Integer albumItemCount;
    @Basic
    @Column(name = "date_create")
    private Date dateCreate;
    @Basic
    @Column(name = "albom_item_cost")
    private Object albumItemCost;
    @Basic
    @Column(name = "albom_item_rate")
    private Object albumItemRate;
    @Basic
    @Column(name = "albom_item_status")
    private Short albumItemStatus;

    public void setAlbumItemId(long albumItemId) {
        this.albumItemId = albumItemId;
    }

    public void setAlbumId(Long albumId) {
        this.albumId = albumId;
    }

    public void setVkItemId(Long vkItemId) {
        this.vkItemId = vkItemId;
    }

    public void setTgItemId(Long tgItemId) {
        this.tgItemId = tgItemId;
    }

    public void setPhotoPath(String photoPath) {
        this.photoPath = photoPath;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setDescriptionShort(String descriptionShort) {
        this.descriptionShort = descriptionShort;
    }

    public void setAlbumItemWeight(BigInteger albumItemWeight) {
        this.albumItemWeight = albumItemWeight;
    }

    public void setAlbumItemName(String albumItemName) {
        this.albumItemName = albumItemName;
    }

    public void setAlbumItemCount(Integer albumItemCount) {
        this.albumItemCount = albumItemCount;
    }

    public void setDateCreate(Date dateCreate) {
        this.dateCreate = dateCreate;
    }

    public void setAlbumItemCost(Object albumItemCost) {
        this.albumItemCost = albumItemCost;
    }

    public void setAlbumItemRate(Object albumItemRate) {
        this.albumItemRate = albumItemRate;
    }

    public void setAlbumItemStatus(Short albumItemStatus) {
        this.albumItemStatus = albumItemStatus;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AlbumItemsEntity that = (AlbumItemsEntity) o;
        return albumItemId == that.albumItemId && Objects.equals(albumId, that.albumId) && Objects.equals(vkItemId, that.vkItemId) && Objects.equals(tgItemId, that.tgItemId) && Objects.equals(photoPath, that.photoPath) && Objects.equals(description, that.description) && Objects.equals(descriptionShort, that.descriptionShort) && Objects.equals(albumItemWeight, that.albumItemWeight) && Objects.equals(albumItemName, that.albumItemName) && Objects.equals(albumItemCount, that.albumItemCount) && Objects.equals(dateCreate, that.dateCreate) && Objects.equals(albumItemCost, that.albumItemCost) && Objects.equals(albumItemRate, that.albumItemRate) && Objects.equals(albumItemStatus, that.albumItemStatus);
    }

    @Override
    public int hashCode() {
        return Objects.hash(albumItemId, albumId, vkItemId, tgItemId, photoPath, description, descriptionShort, albumItemWeight, albumItemName, albumItemCount, dateCreate, albumItemCost, albumItemRate, albumItemStatus);
    }
}
