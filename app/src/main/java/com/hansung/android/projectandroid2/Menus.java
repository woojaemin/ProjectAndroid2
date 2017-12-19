package com.hansung.android.projectandroid2;

import android.provider.BaseColumns;

/**
 * Created by USER on 2017-12-14.
 */

public class Menus {


    public static final String DB_NAME="restaurant3.db";
    public static final int DATABASE_VERSION = 1;
    private static final String TEXT_TYPE = " TEXT";
    private static final String COMMA_SEP = ",";
    // To prevent someone from accidentally instantiating the contract class,
    // make the constructor private.
    private Menus() {}

    /* Inner class that defines the table contents */
    public static class Choice implements BaseColumns {

        public static final String TABLE_NAME2= "Restaurant2";
        public static final String KEY_MENU_NAME = "MENU_NAME";
        public static final String KEY_MENU_PRICE = "MENU_PRICE";
        public static final String KEY_PICTURE = "image";
        public static final String KEY_MENU_EXPLANATION = "MENU_EXPLANATION";
        public static final String KEY_STORE = "Store_Name";


        public static final String CREATE_TABLE2 = "CREATE TABLE " + TABLE_NAME2 +

                " (" +
                _ID + " INTEGER PRIMARY KEY" + COMMA_SEP +
                KEY_MENU_NAME + TEXT_TYPE + COMMA_SEP +
                KEY_MENU_PRICE + TEXT_TYPE + COMMA_SEP +
                KEY_PICTURE + TEXT_TYPE + COMMA_SEP +
                KEY_MENU_EXPLANATION + TEXT_TYPE +
                COMMA_SEP + KEY_STORE + TEXT_TYPE +
                " )";

        public static final String DELETE_TABLE2 = "DROP TABLE IF EXISTS " + TABLE_NAME2;
    }
}
