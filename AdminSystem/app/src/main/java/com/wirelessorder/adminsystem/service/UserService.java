package com.wirelessorder.adminsystem.service;

import android.content.Context;
import android.database.Cursor;

import com.wirelessorder.adminsystem.dao.UserDao;
import com.wirelessorder.adminsystem.po.User;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by triplez on 16-3-30.
 */
public class UserService {
    private UserDao userDao;
    public UserService(Context context) {
        userDao = new UserDao(context);
    }

    public void insertUsers (JSONArray userArray) {
        userDao.clearUser();
        for (int i = 0; i < userArray.length(); i++) {
            try {
                JSONObject userJson = userArray.getJSONObject(i);
                User user = new User();
                user.setUserId(userJson.getInt("user_id"));
                user.setUserName(userJson.getString("user_name"));
                user.setUserPassword(userJson.getString("user_password"));
                user.setUserPhone(userJson.getString("user_phone"));
                userDao.insertUser(user);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    public int getUserAmount() {
        Cursor c = userDao.getAllUsers();
        return c.getCount();
    }

    public void closeDB() {
        userDao.closeDB();
    }
}
