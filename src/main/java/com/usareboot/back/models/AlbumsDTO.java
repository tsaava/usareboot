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

    private Integer actionPercent1;
    private Integer rateAction1;
    private Integer actionPercent2;
    private Integer rateAction2;
    private Integer actionPercent3;
    private Integer rateAction3;
    private Long albumMappingDictionaryId;
}
