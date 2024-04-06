package com.usareboot.back.entities;

import jakarta.persistence.*;
import lombok.Getter;

import java.math.BigInteger;
import java.sql.Date;
import java.util.Objects;

@Getter
@Entity
@Table(name = "alboms_items", schema = "public", catalog = "usareboot")
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
    private Double albumItemCost;
    @Basic
    @Column(name = "albom_item_rate")
    private Double albumItemRate;
    @OneToOne
    @JoinColumn(name = "status_id")
    private DStatusesEntity statuses;


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

    public void setAlbumItemCost(Double albumItemCost) {
        this.albumItemCost = albumItemCost;
    }

    public void setAlbumItemRate(Double albumItemRate) {
        this.albumItemRate = albumItemRate;
    }

    public void setAlbumItemStatus(DStatusesEntity statuses) {
        this.statuses = statuses;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AlbumsItemsEntity that = (AlbumsItemsEntity) o;
        return albumItemId == that.albumItemId && Objects.equals(album, that.album) && Objects.equals(vkItemId, that.vkItemId) && Objects.equals(tgItemId, that.tgItemId) && Objects.equals(photoPath, that.photoPath) && Objects.equals(description, that.description) && Objects.equals(descriptionShort, that.descriptionShort) && Objects.equals(albumItemWeight, that.albumItemWeight) && Objects.equals(albumItemName, that.albumItemName) && Objects.equals(albumItemCount, that.albumItemCount) && Objects.equals(dateCreate, that.dateCreate) && Objects.equals(albumItemCost, that.albumItemCost) && Objects.equals(albumItemRate, that.albumItemRate) && Objects.equals(statuses, that.statuses);
    }

    @Override
    public int hashCode() {
        return Objects.hash(albumItemId, album, vkItemId, tgItemId, photoPath, description, descriptionShort, albumItemWeight, albumItemName, albumItemCount, dateCreate, albumItemCost, albumItemRate, statuses);
    }
}
