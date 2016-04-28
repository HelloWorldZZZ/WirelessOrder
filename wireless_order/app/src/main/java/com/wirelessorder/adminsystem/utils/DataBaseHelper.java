package com.wirelessorder.adminsystem.utils;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by triplez on 16-3-29.
 */
public class DataBaseHelper extends SQLiteOpenHelper {
    public static final String DB_NAME = "mydb.db";
    public static final int DB_VERSION = 1;

    public static final String CREATE_TABLE_ADMIN = "CREATE TABLE t_admin (" +
            "  admin_id integer primary key autoincrement," +
            "  admin_name varchar(255)," +
            "  admin_password varchar(255)" +
            ");";

    public static final String CREATE_TABLE_MEAL = "CREATE TABLE t_meal (" +
            "  meal_id integer primary key autoincrement," +
            "  meal_type_id integer ," +
            "  meal_name varchar(255) ," +
            "  meal_price double ," +
            "  meal_image varchar(255) ," +
            "  meal_info varchar(255)" +
            ");";

    public static final String CREATE_TABLE_MEAL_TYPE = "CREATE TABLE t_meal_type (" +
            "  meal_type_id integer primary key autoincrement," +
            "  meal_type_name varchar(255) " +
            ");";

    public static final String CREATE_TABLE_ORDER = "CREATE TABLE t_order (" +
            "  order_id integer primary key autoincrement," +
            "  user_id integer ," +
            "  order_user_amount integer ," +
            "  order_sum double ," +
            "  order_date varchar(255) " +
            ");";

    public static final String CREATE_TABLE_ORDER_DETAIL = "CREATE TABLE t_order_detail (" +
            "  order_id integer ," +
            "  meal_id integer ," +
            "  meal_amount integer" +
            ");";

    public static final String CREATE_TABLE_USER = "CREATE TABLE t_user (" +
            "  user_id integer  primary key autoincrement," +
            "  user_password varchar(255) ," +
            "  user_name varchar(255) ," +
            "  user_phone varchar(255)" +
            ");";

    public DataBaseHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_ADMIN);
        db.execSQL(CREATE_TABLE_MEAL);
        db.execSQL(CREATE_TABLE_MEAL_TYPE);
        db.execSQL(CREATE_TABLE_ORDER);
        db.execSQL(CREATE_TABLE_ORDER_DETAIL);
        db.execSQL(CREATE_TABLE_USER);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
