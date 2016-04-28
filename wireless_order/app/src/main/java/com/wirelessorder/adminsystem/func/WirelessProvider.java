package com.wirelessorder.adminsystem.func;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.net.Uri;

import com.wirelessorder.adminsystem.service.MealService;
import com.wirelessorder.adminsystem.service.UserService;

/**
 * Created by triplez on 16-4-10.
 */
public class WirelessProvider extends ContentProvider {
    private UserService userService;
    private MealService mealService;
    private static final String AUTHORITY = "com.wirelessorder.adminsystem.wirelessprovider";
    private static final UriMatcher uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
    private static final int USER_HANDLE = 1;
    private static final int MEAL_HANDLE = 2;
    public static final Uri CONTENT_URI =
            Uri.parse("content://com.wirelessorder.adminsystem.wirelessprovider/meals");

    static {
        uriMatcher.addURI(AUTHORITY, "users", USER_HANDLE);
        uriMatcher.addURI(AUTHORITY, "meals", MEAL_HANDLE);
    }
    @Override
    public boolean onCreate() {
        return false;
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        Cursor cursor = null;
        switch (uriMatcher.match(uri)) {
            case USER_HANDLE:
                userService = new UserService(getContext());
                String userName = selectionArgs[0];
                String password = selectionArgs[1];
                cursor = userService.getMatchUser(userName, password);
                break;
            case MEAL_HANDLE:
                mealService = new MealService(getContext());
                if (selectionArgs == null) {
                    cursor = mealService.getAllMealsCursor();
                } else {
                    String type = selectionArgs[0];
                    cursor = mealService.getMealsByType(type);
                }
                break;
            default:
                break;
        }
        return cursor;
    }

    @Override
    public String getType(Uri uri) {
        return null;
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {
        return null;
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        return 0;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        return 0;
    }
}
