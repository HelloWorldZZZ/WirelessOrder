package com.wirelessorder.adminsystem.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.wirelessorder.adminsystem.po.Order;
import com.wirelessorder.adminsystem.po.OrderDetail;
import com.wirelessorder.adminsystem.utils.DataBaseHelper;

/**
 * Created by triplez on 16-3-30.
 */
public class OrderDao {
    private DataBaseHelper mDataBaseHelper;
    private SQLiteDatabase mDB;
    final private String TABLE_ORDER = "t_order";
    final private String TABLE_ORDER_DETAIL = "t_order_detail";
    public OrderDao(Context context) {
        mDataBaseHelper = new DataBaseHelper(context);
        mDB = mDataBaseHelper.getWritableDatabase();
    }

    public void clearOrder() {
        String sql = "DELETE FROM " + TABLE_ORDER;
        mDB.execSQL(sql);
    }

    public void clearOrderDetail() {
        String sql = "DELETE FROM " + TABLE_ORDER_DETAIL;
        mDB.execSQL(sql);
    }

    public void insertOrder(Order order) {
        ContentValues cv = new ContentValues();
        cv.put("order_id", order.getOrderId());
        cv.put("user_id", order.getUserId());
        cv.put("order_user_amount", order.getUserAmount());
        cv.put("order_sum", order.getOrderSum());
        cv.put("order_date", order.getOrderDate());
        mDB.insert(TABLE_ORDER, null, cv);
    }

    public void insertOrderDetail(OrderDetail orderDetail) {
        ContentValues cv = new ContentValues();
        cv.put("order_id", orderDetail.getOrderId());
        cv.put("meal_id", orderDetail.getMealId());
        cv.put("meal_amount", orderDetail.getMealAmount());
        mDB.insert(TABLE_ORDER_DETAIL, null, cv);
    }

    public Cursor getAllOrders() {
        String sql = "SELECT * FROM t_order";
        Cursor c = mDB.rawQuery(sql, null);
        return c;
    }

    public void closeDB() {
        mDB.close();
    }
}
