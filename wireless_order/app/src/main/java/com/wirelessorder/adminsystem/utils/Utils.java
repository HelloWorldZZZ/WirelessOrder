package com.wirelessorder.adminsystem.utils;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.wirelessorder.adminsystem.service.AdminService;
import com.wirelessorder.adminsystem.service.MealService;
import com.wirelessorder.adminsystem.service.OrderService;
import com.wirelessorder.adminsystem.service.UserService;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by triplez on 16-3-29.
 */
public class Utils {
    public static String encrypt(String s) {
        MessageDigest md = null;
        try {
            md = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        md.update(s.getBytes()); 				// MD5加密算法只是对字符数组而不是字符串进行加密计算，得到要加密的对象
        byte[] bs = md.digest(); 				// 进行加密运算并返回字符数组
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < bs.length; i++) { 	// 字节数组转换成十六进制字符串，形成最终的密文
            int v = bs[i] & 0xff;
            if (v < 16) {
                sb.append(0);
            }
            sb.append(Integer.toHexString(v));
        }
        return sb.toString();
    }

    public static boolean hasNetwork(Context context) {
        ConnectivityManager connectivity;
        try {
            connectivity = (ConnectivityManager) context
                    .getSystemService(Context.CONNECTIVITY_SERVICE);
        } catch (Exception e) {
            return false;
        }
        NetworkInfo[] info = null;
        try {
            info = connectivity.getAllNetworkInfo();
        } catch (Exception e) {

        }
        if (info != null) {
            for (int i = 0; i < info.length; i++) {
                if (info[i].getState() == NetworkInfo.State.CONNECTED) {
                    return true;
                }
            }
        }

        return false;
    }

    public static String getDate(){
        Date date = new Date(System.currentTimeMillis());
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String time = dateFormat.format(date);
        return time;
    }

    public static void syncData(Context mContext, String result) {
        try {
            JSONObject resultJson = new JSONObject(result);

            JSONArray userArray = resultJson.getJSONArray("t_user");
            JSONArray adminArray = resultJson.getJSONArray("t_admin");
            JSONArray mealArray = resultJson.getJSONArray("t_meal");
            JSONArray orderArray = resultJson.getJSONArray("t_order");
            JSONArray orderDetailArray = resultJson.getJSONArray("t_order_detail");

            UserService userService = new UserService(mContext);
            AdminService adminService = new AdminService(mContext);
            MealService mealService = new MealService(mContext);
            OrderService orderService = new OrderService(mContext);

            userService.insertUsers(userArray);
            adminService.insertAdmins(adminArray);
            mealService.insertMeals(mealArray);
            orderService.insertOrders(orderArray);
            orderService.insertOrderDetails(orderDetailArray);

            userService.closeDB();
            adminService.closeDB();
            mealService.closeDB();
            orderService.closeDB();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public static Bitmap zoomBitmap(Bitmap bmp, float size) {
        Bitmap bitmap = Bitmap.createBitmap(bmp.getWidth(), bmp.getHeight(), bmp.getConfig());
        Matrix matrix = new Matrix();
        final float WIDTH = size;
        float scaleWidth = WIDTH / (float) (bitmap.getWidth());
        matrix.postScale(scaleWidth, scaleWidth);
        bitmap = Bitmap.createBitmap(bmp, 0, 0, bmp.getWidth(), bmp.getHeight(), matrix, true);
        return bitmap;
    }
}
