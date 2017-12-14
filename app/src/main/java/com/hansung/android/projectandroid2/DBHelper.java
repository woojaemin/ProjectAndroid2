package com.hansung.android.projectandroid2;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by jhim0 on 2017-11-17.
 */

public class DBHelper extends SQLiteOpenHelper {

    final static String TAG = "GBHouse";

    public DBHelper(Context context) {
        super(context, UserContract.DB_NAME, null, UserContract.DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.i(TAG, getClass().getName() + ".onCreate()");
        db.execSQL(UserContract.Users.CREATE_TABLE);
    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        Log.i(TAG, getClass().getName() + ".onUpgrade()");
        db.execSQL(UserContract.Users.DELETE_TABLE);
        onCreate(db);
    }

    public long insertUserByMethod(String name, String address, String phone, String picture) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(UserContract.Users.KEY_NAME, name);
        values.put(UserContract.Users.KEY_ADDRESS, address);
        values.put(UserContract.Users.KEY_PHONE, phone);
        values.put(UserContract.Users.KEY_PICTURE, picture);
        return db.insert(UserContract.Users.TABLE_NAME, null, values);
    }

    public Cursor getAllUsersBySQL() {
        String sql = "Select * FROM " + UserContract.Users.TABLE_NAME;
        return getReadableDatabase().rawQuery(sql, null);
    }

    public long deleteUserByMethod() {
        SQLiteDatabase db = getWritableDatabase();
        return db.delete(UserContract.Users.TABLE_NAME, null, null);
    }
}



