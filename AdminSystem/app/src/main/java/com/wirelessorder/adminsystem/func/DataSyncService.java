package com.wirelessorder.adminsystem.func;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.wirelessorder.adminsystem.utils.Utils;

public class DataSyncService extends Service {
    private Context mContext;
    public DataSyncService() {
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mContext = this;
        getDataFromServer();
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    private void getDataFromServer() {
        String url = "http://www.zhouzezhou.site/WirelessOrder/servlet/DBSyncServlet";
        RequestQueue requestQueue = Volley.newRequestQueue(mContext);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String s) {
                        Log.d("zzz", "sync success");
                        Utils.syncData(mContext, s);
                        stopSelf();
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                Log.d("zzz","sync fail");
                stopSelf();
            }
        });
        requestQueue.add(stringRequest);
    }
}
