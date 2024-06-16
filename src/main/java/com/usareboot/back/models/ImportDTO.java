package com.usareboot.back.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class ImportDTO {
    //основная информация при открытии окна
//    private long importItemListId;
    private String client;
    private long clientId;
    private String vikup;
    private String razdacha;
//    private Boolean pack;
//    private Boolean note;
//    private Boolean sender;
//    private String date;
//    private String address;
//    private String fio;
//    private String phone;
//    private String email;
//    private String status;
//    private String numOrder;
//    private BigInteger isdownload;
//    private String itemColor;
//    private String itemSize;
//    private Double itemWeight;
    private String itemName;
    private Integer itemCount;
    private String spHelpId;
//    private String comment;
    private Double itemCost;
    private String dateStop;

}
