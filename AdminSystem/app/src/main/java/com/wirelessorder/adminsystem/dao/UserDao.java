package com.wirelessorder.adminsystem.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.wirelessorder.adminsystem.po.User;
import com.wirelessorder.adminsystem.utils.DataBaseHelper;
import com.wirelessorder.adminsystem.utils.Utils;

/**
 * Created by triplez on 16-3-30.
 */
public class UserDao {
    private DataBaseHelper mDataBaseHelper;
    private SQLiteDatabase mDB;
    final private String TABLE_NAME = "t_user";
    public UserDao(Context context) {
        mDataBaseHelper = new DataBaseHelper(context);
        mDB = mDataBaseHelper.getWritableDatabase();
    }

    public void clearUser() {
        String sql = "DELETE FROM " + TABLE_NAME;
        mDB.execSQL(sql);
    }

    public void insertUser(User user) {
        ContentValues cv = new ContentValues();
        cv.put("user_id", user.getUserId());
        cv.put("user_password", user.getUserPassword());
        cv.put("user_name", user.getUserName());
        cv.put("user_phone", user.getUserPhone());
        mDB.insert(TABLE_NAME, null, cv);
    }

    public Cursor getAllUsers() {
        String sql = "SELECT * FROM t_user";
        Cursor c = mDB.rawQuery(sql, null);
        return c;
    }

    public void closeDB() {
        mDB.close();
    }
}
