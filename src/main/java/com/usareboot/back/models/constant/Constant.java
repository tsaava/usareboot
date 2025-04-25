package com.usareboot.back.models.constant;

public class Constant {
    public static final Integer ALBUM_STATUS_OPEN;
    public static final Long ITEM_STATUS_REPAYMENT;
    public static final Integer PERCENTAGE_INCOME_DEFAULT;
    public static final String ALBUM_TITLE;
    public static final long NEW_ITEM_STATUS;
    static {
        ALBUM_STATUS_OPEN = 8;
        PERCENTAGE_INCOME_DEFAULT = 25;
        ITEM_STATUS_REPAYMENT = 16L;
        NEW_ITEM_STATUS = 15L;
        ALBUM_TITLE = "Фотографии на стене сообщества";
    }
}
