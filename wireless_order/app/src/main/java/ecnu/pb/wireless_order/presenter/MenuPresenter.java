package ecnu.pb.wireless_order.presenter;

import android.util.Log;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;

import ecnu.pb.wireless_order.model.MenuModel;
import ecnu.pb.wireless_order.net.RestAsyncTask;
import ecnu.pb.wireless_order.net.VolleyRequest;
import ecnu.pb.wireless_order.widget.ToastUtils;

public class MenuPresenter extends BasePresenter<MenuPresenter.View> {

    public MenuPresenter() {}

    public void getMenu(int type) {
        new MenuTask(type).execute();
    }

    private class MenuTask extends RestAsyncTask {

//        private MenuModel menuModel;
        private int type;

        public MenuTask(int type) {
            this.type = type;
        }

        @Override
        protected Integer doInBackground(Void... params) {
//            try {
//                menuModel = RestClient.createMenuService().getMenu(type);
//                return 200;
//            } catch (RetrofitError e) {
//                return e.getResponse() != null ? e.getResponse().getStatus() : 500;
//            }
            String url = "http://www.zhouzezhou.site/WirelessOrder/servlet/MenuServlet"
                    + "?type=" + type;
            try {
                VolleyRequest.newInstance().newGsonRequest(Request.Method.POST, url,
                        MenuModel.class, new Response.Listener<MenuModel>() {
                            @Override
                            public void onResponse(MenuModel menuModel) {
                                getView().showView(menuModel);
                            }
                        }, new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                Log.e("EEEE", error.toString());
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
//                case 200:
//                    getView().showView(menuModel);
//                    break;
//                case 500:
//                    ToastUtils.showToast(getContext(), "网络连接失败");
//                    break;
//            }
        }
    }

    public interface View extends BaseView {
        void showView(MenuModel menuModel);
    }
}
