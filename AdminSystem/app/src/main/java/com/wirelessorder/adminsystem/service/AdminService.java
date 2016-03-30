package com.wirelessorder.adminsystem.service;

import android.content.Context;

import com.wirelessorder.adminsystem.dao.AdminDao;
import com.wirelessorder.adminsystem.po.Admin;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by triplez on 16-3-30.
 */
public class AdminService {
    private AdminDao adminDao;
    public AdminService(Context context) {
        adminDao = new AdminDao(context);
    }

    public void insertAdmins (JSONArray adminArray) {
        adminDao.clearAdmin();
        for (int i = 0; i < adminArray.length(); i++) {
            try {
                JSONObject adminJson = adminArray.getJSONObject(i);
                Admin admin = new Admin();
                admin.setAdminId(adminJson.getInt("admin_id"));
                admin.setAdminName(adminJson.getString("admin_name"));
                admin.setAdminPassword(adminJson.getString("admin_password"));
                adminDao.insertAdmin(admin);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
}
