package com.hansung.android.projectandroid2;

import android.provider.BaseColumns;

/**
 * Created by USER on 2017-12-13.
 */

public class stores {

    public static final String DB_NAME="restaurantname.db";
    public static final int DATABASE_VERSION = 1;
    private static final String TEXT_TYPE = " TEXT";
    private static final String COMMA_SEP = ",";
    // To prevent someone from accidentally instantiating the contract class,
    // make the constructor private.
    private stores() {}



    public static class store implements BaseColumns {
        public static final String TABLE_NAMES="Restaurants";
        public static final String KEY_NAMES = "StoreName";
        public static final String KEY_ADD = "Addresss";

        public static final String CREATE_TABLE = "CREATE TABLE " + TABLE_NAMES +
                " (" +
                _ID + " INTEGER PRIMARY KEY" + COMMA_SEP +
                KEY_NAMES + TEXT_TYPE + COMMA_SEP +
                KEY_ADD + TEXT_TYPE +
                ")";

        public static final String DELETE_TABLE = "DROP TABLE IF EXISTS " + TABLE_NAMES;
    }
}
