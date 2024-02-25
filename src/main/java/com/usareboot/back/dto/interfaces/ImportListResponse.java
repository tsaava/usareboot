package com.usareboot.back.dto.interfaces;

import java.math.BigInteger;

public interface ImportListResponse {
//    long getimport_item_list_id();
    String getclient();
    long getclient_id();
    String getvikup();
    String getrazdacha();
//    Boolean getpack();
//    Boolean getnote();
//    Boolean getsender();
//    String getdate();


//    String getaddress();
//    String getfio();
//    String getphone();
//    String getemail();
//    String getstatus();
//    String getnum_order();
//    BigInteger getisdownload();
//    String getitem_color();

//    String getitem_size();
//    Double getitem_weight();
    String getitem_name();
    Integer getitem_count();
    String getsp_help_id();
//    String getcomment();
    Double getitem_cost();
    String getdate_stop();
}
