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

public class SignUpPresenter extends BasePresenter<SignUpPresenter.View> {

    public SignUpPresenter() {}

    public void signUp(String phone, String name, String password) {
        new SignUpTask(phone, name, password).execute();
    }

    private class SignUpTask extends RestAsyncTask {

        private UserModel userModel;
        private String phone;
        private String name;
        private String password;

        public SignUpTask(String phone , String name, String password) {
            this.phone = phone;
            this.name = name;
            this.password = password;
        }

        @Override
        protected Integer doInBackground(Void... params) {
//            try {
//                userModel = RestClient.createUserService().signup(name, password, phone);
//                return userModel.getRegSuccess();
//            } catch (RetrofitError e) {
//                return e.getResponse() != null ? e.getResponse().getStatus() : 500;
//            }
            String url = "http://www.zhouzezhou.site/WirelessOrder/servlet//UserRegServlet"
                    + "?userName=" + name
                    + "&password=" + password
                    + "&phone=" + phone;
            try {
                VolleyRequest.newInstance().newGsonRequest(Request.Method.POST, url,
                        User.class, new Response.Listener<User>() {
                            @Override
                            public void onResponse(User user) {
                                if (user.getRegSuccess() == 1) {
                                    AccountManager.setKeyUserId(getContext(), user.getUser_id());
                                    getView().showView();
                                } else {
                                    ToastUtils.showToast(getContext(), "注册失败，用户名已存在");
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
        }

        @Override
        protected void onPostExecute(Integer status) {
            super.onPostExecute(status);
//            switch (status) {
//                case 1:
//                    getView().showView(userModel);
//                    break;
//                case 0:
//                    ToastUtils.showToast(getContext(), "注册失败，用户名已存在");
//                    break;
//                case 500:
//                    ToastUtils.showToast(getContext(), "网络连接失败");
//                    break;
//            }
        }
    }

    public interface View extends BaseView {
        void showView();
    }
}
