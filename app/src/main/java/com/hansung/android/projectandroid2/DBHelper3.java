package com.hansung.android.projectandroid2;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by USER on 2017-12-13.
 */

public class DBHelper3 extends SQLiteOpenHelper {

    final static String TAG = "GBHouse";

    public DBHelper3(Context context) {
        super(context, stores.DB_NAME, null, UserContract.DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.i(TAG, getClass().getName() + ".onCreate()");
        db.execSQL(stores.store.CREATE_TABLE);
    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        Log.i(TAG, getClass().getName() + ".onUpgrade()");
        db.execSQL(stores.store.DELETE_TABLE);
        onCreate(db);
    }

    public long insertUserByMethod(String name, String add) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(stores.store.KEY_NAMES, name);
        values.put(stores.store.KEY_ADD, add);
        return db.insert(stores.store.TABLE_NAMES, null, values);
    }

    public Cursor getAllUsersBySQL() {
        String sql = "Select * FROM " + stores.store.TABLE_NAMES;
        return getReadableDatabase().rawQuery(sql, null);
    }

}
