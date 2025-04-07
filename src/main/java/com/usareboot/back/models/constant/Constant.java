package com.usareboot.back.models.constant;

public class Constant {
    public static final Integer ALBUM_STATUS_OPEN;
    public static final Long ITEM_STATUS_REPAYMENT;
    public static final Integer PERCENTAGE_INCOME_DEFAULT;
    public static final String ALBUM_TITLE = "Скрытый альбом для фото2";

    static {
        ALBUM_STATUS_OPEN = 22;
        PERCENTAGE_INCOME_DEFAULT = 25;
        ITEM_STATUS_REPAYMENT = 25L;
    }
}
