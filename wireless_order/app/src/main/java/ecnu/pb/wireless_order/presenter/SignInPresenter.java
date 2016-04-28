package ecnu.pb.wireless_order.presenter;

import android.util.Log;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;

import ecnu.pb.wireless_order.database.AccountManager;
import ecnu.pb.wireless_order.model.User;
import ecnu.pb.wireless_order.model.UserModel;
import ecnu.pb.wireless_order.net.RestAsyncTask;
import ecnu.pb.wireless_order.net.VolleyRequest;
import ecnu.pb.wireless_order.widget.ToastUtils;

public class SignInPresenter extends BasePresenter<SignInPresenter.View> {

    public SignInPresenter() {
    }

    public void signIn(String name, String password) {
        new SignInTask(name, password).execute();
    }

    private class SignInTask extends RestAsyncTask {

        private UserModel userModel;
        private String name;
        private String password;

        public SignInTask(String name, String password) {
            this.name = name;
            this.password = password;
        }

        @Override
        protected Integer doInBackground(Void... params) {
//            try {

            String url = "http://www.zhouzezhou.site/WirelessOrder/servlet//UserLoginServlet"
                    + "?userName=" + name
                    + "&password=" + password;
            try {
                VolleyRequest.newInstance().newGsonRequest(Request.Method.POST, url,
                        User.class, new Response.Listener<User>() {
                            @Override
                            public void onResponse(User user) {
                                if (user.getLoginSuccess() == 1) {
                                    AccountManager.setKeyUserId(getContext(), user.getUser_id());
                                    getView().showView();
                                } else {
                                    ToastUtils.showToast(getContext(), "登录失败，用户名或密码错误");
                                }
                            }
                        }, new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                ToastUtils.showToast(getContext(), "网络连接失败");
                            }
                        });
            } catch (Exception e) {
                Log.e("ERROR", e.toString());
            }
            return 0;

//                userModel = RestClient.createUserService().signin(name, password);
//                return userModel.getLoginSuccess();
//            } catch (RetrofitError e) {
//                return e.getResponse() != null ? e.getResponse().getStatus() : 500;
//            }
        }

        @Override
        protected void onPostExecute(Integer status) {
            super.onPostExecute(status);

        }

    }


    public interface View extends BaseView {
        void showView();
    }
}
