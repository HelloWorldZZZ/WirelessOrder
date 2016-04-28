package com.wirelessorder.adminsystem.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.wirelessorder.adminsystem.po.Admin;
import com.wirelessorder.adminsystem.utils.DataBaseHelper;
import com.wirelessorder.adminsystem.utils.Utils;

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

    public Admin getMatchAdmin(String adminName, String password) {
        String sql = "SELECT * FROM t_admin WHERE admin_name=? AND admin_password=?";
        Admin admin = new Admin();
        Cursor c = mDB.rawQuery(sql, new String[]{adminName, Utils.encrypt(password)});
        if (c != null && c.moveToFirst()) {
            admin.setAdminId(c.getInt(c.getColumnIndex("admin_id")));
            admin.setAdminName(c.getString(c.getColumnIndex("admin_name")));
            admin.setAdminPassword(c.getString(c.getColumnIndex("admin_password")));
        }
        c.close();
        return admin;
    }

    public void closeDB() {
        mDB.close();
    }
}
