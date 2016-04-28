package ecnu.pb.wireless_order.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public abstract class TableManager<T> {
    private String table;

    public abstract void createTable(SQLiteDatabase db);

    public void dropTable(SQLiteDatabase db) {
        String sql = "DROP TABLE IF EXISTS " + table;
        db.execSQL(sql);
    }

    public abstract ContentValues createContentValues(T model);

    public abstract T createModel(SQLiteDatabase db, Cursor cursor);

    public void clear(Context context) {
        SQLiteDatabase db = DatabaseHelper.getDatabase(context);
        clear(db);
    }

    public void clear(SQLiteDatabase db) {
        db.delete(table, null, null);
    }

    public TableManager(String table) {
        this.table = table;
    }

    private String getTable() {
        return table;
    }
}