package ecnu.pb.wireless_order.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {
    private static final String DB_NAME = "wireless_order.db";
    private static final int DB_VERSION = 1;

    private static SQLiteOpenHelper instance;

    public static SQLiteDatabase getDatabase(Context context) {
        return getInstance(context).getWritableDatabase();
    }

    public static void clear(Context context) {
        SQLiteDatabase db = getDatabase(context);
        db.beginTransaction();
        try {
            MenuManager.createInstance().clear(db);

            db.setTransactionSuccessful();
        } finally {
            db.endTransaction();
        }
    }

    private static SQLiteOpenHelper getInstance(Context context) {
        if (instance == null) {
            return instance = new DatabaseHelper(context);
        }
        return instance;
    }

    private DatabaseHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.beginTransaction();
        try {
            createTable(db);

            db.setTransactionSuccessful();
        } finally {
            db.endTransaction();
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.beginTransaction();
    }

    public void createTable(SQLiteDatabase db) {
        MenuManager.createInstance().createTable(db);
    }
}
