package com.usareboot.back.persistence.usareboot.entities;

import jakarta.persistence.*;
import lombok.Getter;

import java.sql.Date;
import java.util.Objects;

@Getter
@Entity
@Table(name = "alboms",  catalog = "usareboot")
public class AlbumsEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "albom_id")
    private long albumId;
    @Basic
    @Column(name = "albom_name")
    private String albumName;
    @Basic
    @Column(name = "albom_date")
    private Date albumDate;

    @Basic
    @Column(name = "albom_date_plane")
    private Date albumDatePlane;
    @Basic
    @Column(name = "count_order")
    private Integer countOrder;
    @Basic
    @Column(name = "country")
    private String country;
    @Basic
    @Column(name = "albom_desc")
    private String albumDesc;
    @Basic
    @Column(name = "shop_url")
    private String shopUrl;
    @Basic
    @Column(name = "albom_vk_url")
    private String albumVkUrl;
    @Basic
    @Column(name = "package_id")
    private Long packageId;
    @Basic
    @Column(name = "course_bank")
    private String courseBank;
    @Basic
    @Column(name = "course_albom")
    private String courseAlbum;
    @Basic
    @Column(name = "bank_name")
    private String bankName;
    @Basic
    @Column(name = "track_number")
    private String trackNumber;
    @Basic
    @Column(name = "warehouse")
    private String warehouse;
    @Basic
    @Column(name = "albom_date_stop")
    private Date albumDateStop;
    @Basic
    @Column(name = "albom_vk_id")
    private long albumVkId;
//    @Basic
//    @Column(name = "date_stop")
//    private Date dateStop;
//    @Basic
//    @Column(name = "status_id")
//    private long statusId;
    @OneToOne
    @JoinColumn(name = "status_id")
    private DStatusesEntity statuses;
    @Basic
    @Column(name = "course_bank_id")
    private Long courseBankId;
    @Basic
    @Column(name = "year")
    private int year;
//    @Basic
//    @Column(name = "card_id")
//    private Long cardId;
    @OneToOne
    @JoinColumn(name = "card_id")
    private CardsEntity cards;

    @Basic
    @Column(name = "album_mapping_dictionary_id")
    private Long albumMappingDictionaryId;

    public void setStatuses(DStatusesEntity statuses) {
        this.statuses = statuses;
    }

    public void setCards(CardsEntity cards) {
        this.cards = cards;
    }

    public void setAlbumId(long albumId) {
        this.albumId = albumId;
    }

    public void setAlbumName(String albumName) {
        this.albumName = albumName;
    }

    public void setAlbumDate(Date albomDate) {
        this.albumDate = albomDate;
    }


    public void setAlbumDatePlane(Date albumDatePlane) {
        this.albumDatePlane = albumDatePlane;
    }

    public void setCountOrder(Integer countOrder) {
        this.countOrder = countOrder;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public void setAlbumDesc(String albumDesc) {
        this.albumDesc = albumDesc;
    }

    public void setShopUrl(String shopUrl) {
        this.shopUrl = shopUrl;
    }

    public void setAlbumVkUrl(String albumVkUrl) {
        this.albumVkUrl = albumVkUrl;
    }

    public void setPackageId(Long packageId) {
        this.packageId = packageId;
    }

    public void setCourseBank(String courseBank) {
        this.courseBank = courseBank;
    }

    public void setCourseAlbum(String courseAlbum) {
        this.courseAlbum = courseAlbum;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public void setTrackNumber(String trackNumber) {
        this.trackNumber = trackNumber;
    }

    public void setWarehouse(String warehouse) {
        this.warehouse = warehouse;
    }

    public void setAlbumDateStop(Date albumDateStop) {
        this.albumDateStop = albumDateStop;
    }

    public void setAlbumVkId(long albumVkId) {
        this.albumVkId = albumVkId;
    }
    public void setAlbumMappingDictionaryId(long albumMappingDictionaryId) {
        this.albumMappingDictionaryId = albumMappingDictionaryId;
    }


//    public void setStatusId(long albumStatus) {
//        this.statusId = albumStatus;
//    }

    public void setCourseBankId(Long courseBankId) {
        this.courseBankId = courseBankId;
    }

    public void setYear(int year) {
        this.year = year;
    }
//    public void setCardId(Long card) {
//        this.cardId = card;
//    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AlbumsEntity that = (AlbumsEntity) o;
        return albumId == that.albumId && albumVkId == that.albumVkId /*&& statusId == that.statusId*/ && Objects.equals(albumName, that.albumName) && Objects.equals(albumDate, that.albumDate) && Objects.equals(albumDatePlane, that.albumDatePlane) && Objects.equals(countOrder, that.countOrder) && Objects.equals(country, that.country) && Objects.equals(albumDesc, that.albumDesc) && Objects.equals(shopUrl, that.shopUrl) && Objects.equals(albumVkUrl, that.albumVkUrl) && Objects.equals(packageId, that.packageId) && Objects.equals(courseBank, that.courseBank) && Objects.equals(courseAlbum, that.courseAlbum) && Objects.equals(bankName, that.bankName) && Objects.equals(trackNumber, that.trackNumber) && Objects.equals(warehouse, that.warehouse) && Objects.equals(albumDateStop, that.albumDateStop)  && Objects.equals(courseBankId, that.courseBankId)/*&& Objects.equals(cardId, that.cardId)*/;
    }

    @Override
    public int hashCode() {
        return Objects.hash(albumId, albumName, albumDate, albumDatePlane, countOrder, country, albumDesc, shopUrl, albumVkUrl, packageId, courseBank, courseAlbum, bankName, trackNumber, warehouse, albumDateStop, albumVkId,  /*statusId,*/ courseBankId/*, cardId*/);
    }
}
