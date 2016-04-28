package com.wirelessorder.adminsystem.func;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import ecnu.pb.wireless_order.R;
import com.wirelessorder.adminsystem.utils.Utils;

public class WelcomeActivity extends AppCompatActivity {
    private ProgressDialog pDialog;
    private Context mContext;
    private SharedPreferences mSp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = this;
        setContentView(R.layout.activity_welcome);
        mSp = getSharedPreferences("app_info", MODE_PRIVATE);
        final boolean isFirstTime = mSp.getBoolean("firstStartup", true);
        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                if (isFirstTime) {
                    if (!Utils.hasNetwork(mContext)) {
                        Toast.makeText(mContext, getString(R.string.network_error),
                                Toast.LENGTH_SHORT).show();
                        finish();
                    }
                    pDialog = ProgressDialog.show(mContext, null, "同步数据库", true, true);
                    pDialog.setCancelable(false);
                    pDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
                        @Override
                        public void onDismiss(DialogInterface dialog) {
                            loadMainActivity();
                        }
                    });
                    getDataFromServer();
                } else {
                    loadMainActivity();
                }
            }

        }, 2500);
    }

    private void getDataFromServer() {
        String url = "http://www.zhouzezhou.site/WirelessOrder/servlet/DBSyncServlet";
        RequestQueue requestQueue = Volley.newRequestQueue(mContext);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String s) {
                        Utils.syncData(mContext, s);
                        pDialog.dismiss();
                        mSp.edit().putBoolean("firstStartup", false).commit();
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                Toast.makeText(mContext, "同步失败", Toast.LENGTH_SHORT).show();
                finish();
            }
        });
        requestQueue.add(stringRequest);
    }

    private void loadMainActivity() {
        Intent intent = new Intent(mContext, LoginActivity.class);
        startActivity(intent);
        WelcomeActivity.this.finish();
    }
}
