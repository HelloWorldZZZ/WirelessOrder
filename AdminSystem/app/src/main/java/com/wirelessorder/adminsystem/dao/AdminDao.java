package com.wirelessorder.adminsystem.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.wirelessorder.adminsystem.po.Admin;
import com.wirelessorder.adminsystem.utils.DataBaseHelper;

/**
 * Created by triplez on 16-3-30.
 */
public class AdminDao {
    private DataBaseHelper mDataBaseHelper;
    private SQLiteDatabase mDB;
    final private String TABLE_NAME = "t_admin";
    public AdminDao(Context context) {
        mDataBaseHelper = new DataBaseHelper(context);
        mDB = mDataBaseHelper.getWritableDatabase();
    }

    public void clearAdmin() {
        String sql = "DELETE FROM " + TABLE_NAME;
        mDB.execSQL(sql);
    }

    public void insertAdmin(Admin admin) {
        ContentValues cv = new ContentValues();
        cv.put("admin_id", admin.getAdminId());
        cv.put("admin_password", admin.getAdminPassword());
        cv.put("admin_name", admin.getAdminName());
        mDB.insert(TABLE_NAME, null, cv);
    }
}
