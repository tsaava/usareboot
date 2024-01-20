package com.usareboot.back.dto.interfaces;

import java.util.Date;

public interface ItemListResponse {
    long getitem_id();
    long getclient_id();
    long getstatus_id();
    long getalbom_id();
    long getvk_id();
    long getorder_id();
    String getfi();
    String getfio();
    String getitem_name();
    String getalbom_name();
    String getitem_size();

    Integer getitem_count();
    String getstatus_name();
    Number getitem_weight();
    Date getdate_delivery();
    String getsp_help_id();
    String getrazdacha();
}
