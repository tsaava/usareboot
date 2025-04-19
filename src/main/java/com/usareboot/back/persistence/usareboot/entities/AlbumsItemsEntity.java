package com.usareboot.back.persistence.usareboot.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.Date;
import java.util.Objects;

@Getter
@Setter
@Entity
@Table(name = "alboms_items", catalog = "usareboot")
public class AlbumsItemsEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "albom_item_id")
    private long albumItemId;
    @ManyToOne
    @JoinColumn(name = "albom_id")
    private AlbumsEntity album;
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
    @Column(name = "item_description")
    private String itemDescription;
    @Basic
    @Column(name = "description")
    private String description;
    @Basic
    @Column(name = "item_url")
    private String itemUrl;

    @Column(name = "item_color")
    private String itemColor;
    @Basic
    @Column(name = "item_size")
    private String itemSize;
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
    private BigDecimal albumItemCost;
    @Basic
    @Column(name = "albom_item_rate")
    private BigDecimal albumItemRate;
    @OneToOne
    @JoinColumn(name = "status_id")
    private DStatusesEntity statuses;
    @Basic
    @Column(name = "vk_photo_path")
    private String vkPhotoPath;

    @Basic
    @Column(name = "cost")
    private BigDecimal cost;

    @Basic
    @Column(name = "no_size")
    private boolean isNoSize;
    public void setAlbumItemId(long albumItemId) {
        this.albumItemId = albumItemId;
    }

    public void setAlbumId(AlbumsEntity album) {
        this.album = album;
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

    public void setItemDescription(String itemDescription) {
        this.itemDescription = itemDescription;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setItemUrl(String itemUrl) {
        this.itemUrl = itemUrl;
    }
    public void setItemColor(String itemColor) {
        this.itemColor = itemColor;
    }public void setItemSize(String itemSize) {
        this.itemSize = itemSize;
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

    public void setAlbumItemCost(BigDecimal albumItemCost) {
        this.albumItemCost = albumItemCost;
    }

    public void setAlbumItemRate(BigDecimal albumItemRate) {
        this.albumItemRate = albumItemRate;
    }

    public void setAlbumItemStatus(DStatusesEntity statuses) {
        this.statuses = statuses;
    }

    public void setVkPhotoPath(String vkPhotoPath) {
        this.vkPhotoPath = vkPhotoPath;
    }

    public void setCost(BigDecimal cost) {
        this.cost = cost;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AlbumsItemsEntity that = (AlbumsItemsEntity) o;
        return albumItemId == that.albumItemId && Objects.equals(album, that.album) && Objects.equals(vkItemId, that.vkItemId) && Objects.equals(tgItemId, that.tgItemId) && Objects.equals(photoPath, that.photoPath) && Objects.equals(description, that.description) && Objects.equals(itemUrl, that.itemUrl) && Objects.equals(descriptionShort, that.descriptionShort) && Objects.equals(albumItemWeight, that.albumItemWeight) && Objects.equals(albumItemName, that.albumItemName) && Objects.equals(albumItemCount, that.albumItemCount) && Objects.equals(dateCreate, that.dateCreate) && Objects.equals(albumItemCost, that.albumItemCost) && Objects.equals(albumItemRate, that.albumItemRate) && Objects.equals(statuses, that.statuses);
    }

    @Override
    public int hashCode() {
        return Objects.hash(albumItemId, album, vkItemId, tgItemId, photoPath, description, itemUrl, descriptionShort, albumItemWeight, albumItemName, albumItemCount, dateCreate, albumItemCost, albumItemRate, statuses);
    }
}
