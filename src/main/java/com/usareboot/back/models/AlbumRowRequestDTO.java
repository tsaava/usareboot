package com.usareboot.back.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class AlbumRowRequestDTO {
    private long albumId;
    private long albumVkId;
    private String albumDesc;
    private String courseAlbum;
    private Date albumDatePlane;
    private Integer countOrder;
    private Date albumDateStop;
    private String albumStatus;
    private String card;

//    String albumName;
//    String albumDate;
//    String country;
//    String shopUrl;
//    String albumVkUrl;
//    Long packageId;
//    String courseBank;
//    String bankName;
//    String trackNumber;
//    String warehouse;
//    Long courseBankId;
//    String albumStatusName;
//    Long cardId;

}
