package com.hansung.android.projectandroid2;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by USER on 2017-12-14.
 */

public class MenuDB extends SQLiteOpenHelper{

    final static String TAG = "GBHouse";

    public MenuDB(Context context) {
        super(context, Menus.DB_NAME, null, Menus.DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.i(TAG, getClass().getName() + ".onCreate()");
        db.execSQL(Menus.Choice.CREATE_TABLE2);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        Log.i(TAG, getClass().getName() + ".onUpgrade()");
        db.execSQL(Menus.Choice.DELETE_TABLE2);
        onCreate(db);
    }

    public long insertUserByMethod2(String menu_name, String menu_price, String img, String menu_explanation, String Store_name) {     //picture 스트링 말고
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(Menus.Choice.KEY_MENU_NAME, menu_name);
        values.put(Menus.Choice.KEY_MENU_PRICE, menu_price);
        values.put(Menus.Choice.KEY_PICTURE, img);
        values.put(Menus.Choice.KEY_MENU_EXPLANATION, menu_explanation);
        values.put(Menus.Choice.KEY_STORE, Store_name);

        return db.insert(Menus.Choice.TABLE_NAME2, null, values);
    }
//
//    public Cursor getAllUsersBySQL() {
//        String sql = "Select * FROM " + Menus.Choice.TABLE_NAME2;
//        return getReadableDatabase().rawQuery(sql, null);
//    }

    public Cursor getAllUsersByMethod() {
        SQLiteDatabase db = getReadableDatabase();
        return db.query(Menus.Choice.TABLE_NAME2, null, null, null, null, null, null);
    }

    public Cursor getMenusByMethod(String _id) {
        SQLiteDatabase db = getReadableDatabase();

        String whereClause = Menus.Choice.KEY_STORE +" = ?";
        String[] whereArgs ={_id};

       return db.query(Menus.Choice.TABLE_NAME2,null,whereClause,whereArgs,null,null,null);
    }

}
