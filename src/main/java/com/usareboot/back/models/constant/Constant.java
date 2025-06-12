package com.usareboot.back.models.constant;

public class Constant {
    public static final Integer ALBUM_STATUS_OPEN;
//    public static final Long ITEM_STATUS_REPAYMENT;
    public static final Integer PERCENTAGE_INCOME_DEFAULT;
    public static final String ALBUM_TITLE;
    public static final long NEW_ITEM_STATUS;
    public static final long ITEM_IN_REDEEMED_STATUS_ID;
    public static final long ITEM_IN_NOT_REDEEMED_SIZE_STATUS_ID;
    public static final long ITEM_IN_NOT_REDEEMED_COURSE_STATUS_ID;
    public static final long ITEM_IN_NOT_REDEEMED_COST_STATUS_ID;
    public static final long ITEM_IN_CANCELED_BY_STORE_STATUS_ID;
    public static final long ALBUM_ITEM_DEFAULT_STATUS_ID;
    public static final long ALBUM_DEFAULT_STATUS_ID;
    public static final long ALBUM_CLOSE_STATUS_ID;
    public static final long ITEM_IN_REDEEMED_WITH_CHANGE_COST_STATUS_ID;
    static {
        ALBUM_STATUS_OPEN = 8;
        PERCENTAGE_INCOME_DEFAULT = 25;
//        ITEM_STATUS_REPAYMENT = 16L;
        NEW_ITEM_STATUS = 15L;
        ITEM_IN_REDEEMED_STATUS_ID = 16L;
        ITEM_IN_NOT_REDEEMED_SIZE_STATUS_ID = 18L;
        ITEM_IN_CANCELED_BY_STORE_STATUS_ID = 17L;
        ITEM_IN_NOT_REDEEMED_COURSE_STATUS_ID = 20L;
        ITEM_IN_NOT_REDEEMED_COST_STATUS_ID = 21L;
        ITEM_IN_REDEEMED_WITH_CHANGE_COST_STATUS_ID = 22L;
        ALBUM_ITEM_DEFAULT_STATUS_ID = 13L;
        ALBUM_DEFAULT_STATUS_ID = 8L;
        ALBUM_CLOSE_STATUS_ID = 9L;
        ALBUM_TITLE = "Фотографии на стене сообщества";
    }
}
