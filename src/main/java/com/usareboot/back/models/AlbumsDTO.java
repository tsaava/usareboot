package com.usareboot.back.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class AlbumsDTO {
    private long albumId;
    private String albumName;
    private String albumDate;
    private String albumDatePlane;
    private Integer countOrder;
    private String country;
    private String albumDesc;
    private String shopUrl;
    private String albumVkUrl;
    private Long packageId;
    private String courseBank;
    private String courseAlbum;
    private String bankName;
    private String trackNumber;
    private String warehouse;
    private String albumDateStop;
    private long albumVkId;
    private Long courseBankId;
    private Long albumStatus;
    private String albumStatusName;
    private Long cardId;
    private String cardName;

}
/*  private long albumId;
    private long albumVkId;
    private String albumDesc;
    private String courseAlbum;
    private String albumDatePlane;
    private Integer countOrder;
    private String albumDateStop;
    private String albumStatus;
    private String card;*/