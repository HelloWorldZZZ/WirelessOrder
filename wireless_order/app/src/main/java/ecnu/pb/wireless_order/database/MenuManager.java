package ecnu.pb.wireless_order.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import ecnu.pb.wireless_order.model.MealModel;
import ecnu.pb.wireless_order.model.MenuModel;

public class MenuManager extends TableManager<MealModel> {
    public static final String TABLE_NAME = "menu";

    private static final String COL_MENU_ID = "menu_id";
    private static final String COL_MENU_NAME = "menu_name";
    private static final String COL_MENU_PHOTO = "menu_photo";
    private static final String COL_MENU_PRICE = "menu_price";
    private static final String COL_MENU_TYPE = "type";
    private static final String COL_MENU_INTRO = "intro";
    private static final String COL_MENU_COUNT = "count";

    public static MenuManager createInstance() {
        return new MenuManager();
    }

    private MenuManager() {
        super(TABLE_NAME);
    }

    @Override
    public void createTable(SQLiteDatabase db) {
        String sql;
        sql = "CREATE TABLE " + TABLE_NAME + "(_id int PRIMARY_KEY AUTO_INCREMENT," +
                COL_MENU_ID + " int UNIQUE," +
                COL_MENU_NAME + " varchar," +
                COL_MENU_PHOTO + " varchar," +
                COL_MENU_PRICE + " int," +
                COL_MENU_INTRO + " varchar," +
                COL_MENU_TYPE + " int," +
                COL_MENU_COUNT + " int)";
        db.execSQL(sql);
    }

    @Override
    public ContentValues createContentValues(MealModel menu) {
        ContentValues values = new ContentValues();
        values.put(COL_MENU_ID, menu.getMeal_id());
        values.put(COL_MENU_NAME, menu.getMeal_name());
        values.put(COL_MENU_PHOTO, menu.getMeal_image_url());
        values.put(COL_MENU_PRICE, menu.getMeal_price());
        values.put(COL_MENU_TYPE, menu.getMeal_type());
        values.put(COL_MENU_INTRO, menu.getMeal_intro());
        values.put(COL_MENU_COUNT, 1);
        return values;
    }

    @Override
    public MealModel createModel(SQLiteDatabase db, Cursor cursor) {
        int id = cursor.getInt(cursor.getColumnIndex(COL_MENU_ID));
        String name = cursor.getString(cursor.getColumnIndex(COL_MENU_NAME));
        int price = cursor.getInt(cursor.getColumnIndex(COL_MENU_PRICE));
        String photo = cursor.getString(cursor.getColumnIndex(COL_MENU_PHOTO));
        int type = cursor.getInt(cursor.getColumnIndex(COL_MENU_TYPE));
        String intro = cursor.getString(cursor.getColumnIndex(COL_MENU_INTRO));
        int count = cursor.getInt(cursor.getColumnIndex(COL_MENU_COUNT));
        return new MealModel(id, type, name, price,  photo, intro, count);
    }


    public void saveAll(Context context, MenuModel menuModel) {
        SQLiteDatabase db = DatabaseHelper.getDatabase(context);
        db.beginTransaction();
        try {
            for (MealModel mealModel : menuModel.getMealModels()) {
                save(db, mealModel);
            }
            db.setTransactionSuccessful();
        } finally {
            db.endTransaction();
        }
    }

    public void save(Context context, MealModel mealModel) {
        SQLiteDatabase db = DatabaseHelper.getDatabase(context);
        save(db, mealModel);
    }

    public void save(SQLiteDatabase db, MealModel mealModel) {
        db.insert(TABLE_NAME, null, createContentValues(mealModel));
    }

    public MealModel getmenuById(Context context, int menuId) {
        SQLiteDatabase db = DatabaseHelper.getDatabase(context);
        String where = COL_MENU_ID + "=" + menuId;
        Cursor cursor = db.query(TABLE_NAME, null, where, null, null, null, null);
        if (cursor.moveToFirst()) {
            return createModel(db, cursor);
        }
        return null;
    }

    public List<MealModel> getMenu(Context context) {
        SQLiteDatabase db = DatabaseHelper.getDatabase(context);
        List<MealModel> all = new ArrayList<>();
        String sql = "SELECT * FROM " + TABLE_NAME;
        Cursor cursor = db.rawQuery(sql, null); 	//执行查询语句
        MealModel mealModel = null;
        while (cursor.moveToNext()) {
            int meal_id = cursor.getInt(1);
            mealModel = getmenuById(context, meal_id);
            all.add(mealModel);
        }
        cursor.close();
        db.close();
        return all;
    }
}

