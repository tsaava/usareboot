package com.usareboot.back.models.interfaces;

import java.math.BigDecimal;

public interface ItemListResponse {
    long getitem_id();
    long getclient_id();
    Long getitem_status_id();
    Long getpay_status_id();
    Long getalbom_id();
    Long getalbom_item_id();
    Long getvk_id();
    long getorder_id();
    String getfi();
    String getclient_Url();
    String getitem_name();
    String getalbom_vk_url();
    String getalbom_name();
    String getitem_size();
    String getitem_color();
    Integer getitem_count();
    String getitem_url();
    String getitem_status();
    String getpay_status();
    Number getitem_weight();
    String getdate_comment();
    String getphoto_path();
    String getvk_photo_path();
    String getcomment();
    Number getalbom_item_cost();
    Number getalbom_item_rate();
    Number getcost();
    String getrepayment_name();
    boolean getis_not_size();
}
