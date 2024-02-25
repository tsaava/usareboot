package com.usareboot.back.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigInteger;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class AlbumsDTO {
    long albumId;
    String albumName;
    Date albumDate;
    Double courseExchange;
    Date albumDatePlane;
    Integer countOrder;
    String country;
    String shopName;
    String shopUrl;
    String albumVkUrl;
    Long packageId;
    Double courseBank;
    Double courseAlbum;
    String bankName;
    String trackNumber;
    String warehouse;
    Date albumDateStop;
    long albumVkId;
    Date dateStop;
    long albumStatus;
    Long courseBankId ;
}
