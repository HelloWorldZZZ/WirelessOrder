package ecnu.pb.wireless_order.database;

import android.content.Context;
import android.content.SharedPreferences;

public class OrderManager {
    private static final String PREFERENCES_NAME = "order";
    private static final String ORDER_ID = "id";
    private static final String ORDER_DATE = "date";
    private static final String USER_COUNT = "count";
    private static final String ORDER_SUM = "sum";
    private static final String ORDER_STATUS = "status";

    private static SharedPreferences preferences;

    public static void saveOrder(Context context, int id, String date, int count) {
        getSharedPreferences(context).edit()
                .putInt(ORDER_ID, id)
                .putString(ORDER_DATE, date)
                .putInt(USER_COUNT, count)
                .apply();
    }

    public static void setOrderSum(Context context, int sum) {
        getSharedPreferences(context).edit()
                .putInt(ORDER_SUM, sum)
                .apply();
    }

    public static void setOrderStatus(Context context, int status) {
        getSharedPreferences(context).edit()
                .putInt(ORDER_STATUS, status)
                .apply();
    }

    public static void clearOrder(Context context) {
        getSharedPreferences(context)
                .edit()
                .clear()
                .apply();
    }

    public static int getOrderId(Context context) {
        return getSharedPreferences(context).getInt(ORDER_ID, 0);
    }

    public static String getOrderDate(Context context) {
        return getSharedPreferences(context).getString(ORDER_DATE, null);
    }

    public static int getCount(Context context) {
        return getSharedPreferences(context).getInt(USER_COUNT, 1);
    }

    public static int getOrderSum(Context context) {
        return getSharedPreferences(context).getInt(ORDER_SUM, 100);
    }

    public static int getOrderStatus(Context context) {
        return getSharedPreferences(context).getInt(ORDER_STATUS, 0);
    }

    private static SharedPreferences getSharedPreferences(Context context) {
        if (preferences == null) {
            preferences = context.getSharedPreferences(PREFERENCES_NAME, Context.MODE_PRIVATE);
        }
        return preferences;
    }

}
