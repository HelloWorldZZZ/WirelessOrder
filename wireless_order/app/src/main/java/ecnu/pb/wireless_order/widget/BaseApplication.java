package ecnu.pb.wireless_order.widget;

import android.app.Application;

import ecnu.pb.wireless_order.net.VolleyRequest;

public class BaseApplication extends Application {
    private static BaseApplication instance;

    public BaseApplication() {
        instance = this;
    }

    public static Application getContext() {
        return instance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        VolleyRequest.buildRequestQueue(this);
    }
}