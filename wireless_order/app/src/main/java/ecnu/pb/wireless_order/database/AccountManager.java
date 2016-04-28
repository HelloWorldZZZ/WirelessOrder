package ecnu.pb.wireless_order.database;


import android.content.Context;
import android.content.SharedPreferences;

public class AccountManager {
    private static final String PREFERENCES_NAME = "account_pref";
    private static final String KEY_MOBILE = "mobile";
    private static final String KEY_PASSWORD = "password";
    private static final String KEY_TOKEN = "token";
    private static final String KEY_USER_ID = "user_id";
    private static final String KEY_USER_NAME = "user_name";

    private static SharedPreferences preferences;

    public static boolean isSignin(Context context) {
        return getSharedPreferences(context).getString(KEY_TOKEN, null) != null;
    }

    public static void signin(Context context, String name, String password) {
        getSharedPreferences(context).edit()
                .putString(KEY_USER_NAME, name)
                .putString(KEY_PASSWORD, password)
                .putString(KEY_TOKEN, "token")
                .apply();
    }

    public static void signup(Context context, String mobile, String password, String name) {
        getSharedPreferences(context).edit()
                .putString(KEY_MOBILE, mobile)
                .putString(KEY_PASSWORD, password)
                .putString(KEY_USER_NAME, name)
                .putString(KEY_TOKEN, "token")
                .apply();
    }

    public static void setKeyUserId(Context context, int id) {
        getSharedPreferences(context).edit()
                .putInt(KEY_USER_ID, id)
                .apply();
    }

    public static void signout(Context context) {
        getSharedPreferences(context)
                .edit()
                .clear()
                .apply();
    }

    public static String getToken(Context context) {
        return "token " + getSharedPreferences(context).getString(KEY_TOKEN, null);
    }

    public static String getMobile(Context context) {
        return getSharedPreferences(context).getString(KEY_MOBILE, null);
    }

    public static String getPassword(Context context) {
        return getSharedPreferences(context).getString(KEY_PASSWORD, null);
    }

    public static int getKeyUserId(Context context) {
        return getSharedPreferences(context).getInt(KEY_USER_ID, 0);
    }

    public static String getUserName(Context context) {
        return getSharedPreferences(context).getString(KEY_USER_NAME, null);
    }

    private static SharedPreferences getSharedPreferences(Context context) {
        if (preferences == null) {
            preferences = context.getSharedPreferences(PREFERENCES_NAME, Context.MODE_PRIVATE);
        }
        return preferences;
    }

}
