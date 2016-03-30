package com.wirelessorder.adminsystem.func;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.wirelessorder.adminsystem.R;
import com.wirelessorder.adminsystem.service.AdminService;
import com.wirelessorder.adminsystem.service.MealService;
import com.wirelessorder.adminsystem.service.OrderService;
import com.wirelessorder.adminsystem.service.UserService;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

public class WelcomeActivity extends AppCompatActivity {
    private ProgressDialog pDialog;
    private Context mContext;

    private Thread DBSyncThread = new Thread(new Runnable() {
        @Override
        public void run() {
            getDataFromServer();
        }
    });

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = this;
        setContentView(R.layout.activity_welcome);
        final SharedPreferences mSp = getSharedPreferences("app_info", MODE_PRIVATE);
        final boolean isFirstTime = mSp.getBoolean("firstStartup", true);
        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                if (isFirstTime) {
                    pDialog = ProgressDialog.show(mContext, null, "同步数据库", true, true);
                    pDialog.setCancelable(false);
                    pDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
                        @Override
                        public void onDismiss(DialogInterface dialog) {
                            loadMainActivity();
                        }
                    });
                    new Thread(DBSyncThread).start();
                    mSp.edit().putBoolean("firstStartup", false).commit();
                } else {
                    loadMainActivity();
                }
            }

        }, 2500);
    }

    private void loadMainActivity() {
        Intent intent = new Intent(mContext, MainActivity.class);
        startActivity(intent);
        WelcomeActivity.this.finish();
    }

    private void getDataFromServer() {
        String url = "http://www.zhouzezhou.site/WirelessOrder/servlet/DBSyncServlet";
        HttpClient client = new DefaultHttpClient();
        HttpPost httpPost = new HttpPost(url);
        HttpResponse response;
        try {
            response = client.execute(httpPost);
            if (response != null && response.getStatusLine().getStatusCode() == 200) {
                HttpEntity httpEntity = response.getEntity();
                String result = EntityUtils.toString(httpEntity, HTTP.UTF_8);
                insertDB(result);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void insertDB(String result) {
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

            pDialog.dismiss();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
