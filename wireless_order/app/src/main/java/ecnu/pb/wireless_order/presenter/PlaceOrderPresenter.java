package ecnu.pb.wireless_order.presenter;

import android.util.Log;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;

import java.text.SimpleDateFormat;

import ecnu.pb.wireless_order.database.OrderManager;
import ecnu.pb.wireless_order.model.OrderModel;
import ecnu.pb.wireless_order.net.RestAsyncTask;
import ecnu.pb.wireless_order.net.VolleyRequest;
import ecnu.pb.wireless_order.widget.ToastUtils;

public class PlaceOrderPresenter extends BasePresenter<PlaceOrderPresenter.View> {

    public PlaceOrderPresenter() {
    }

    public void placeOrder(String order) {
        new OrderTask(order).execute();
    }

    private class OrderTask extends RestAsyncTask {

        private OrderModel orderModel;
        private String order;

        public OrderTask(String order) {
            this.order = order;
        }

        @Override
        protected Integer doInBackground(Void... params) {
//            try {
//                orderModel = RestClient.createOrderService().placeOrder(order);
//                return 200;
//            } catch (RetrofitError e) {
//                return e.getResponse() != null ? e.getResponse().getStatus() : 500;
//            }
            String url = "http://www.zhouzezhou.site/WirelessOrder/servlet/NewOrderServlet"
                    + "?order=" + order;
            try {
                VolleyRequest.newInstance().newGsonRequest(Request.Method.POST, url,
                        OrderModel.class, new Response.Listener<OrderModel>() {
                            @Override
                            public void onResponse(OrderModel orderModel) {
                                if (orderModel != null) {
                                    OrderManager.saveOrder(getContext(),
                                            orderModel.getOrder_id(),
                                            orderModel.getOrder_date(),
                                            orderModel.getUser_amount());
                                } else {
                                    SimpleDateFormat sDateFormat = new SimpleDateFormat("yyyy-MM-dd    hh:mm:ss");
                                    String date = sDateFormat.format(new java.util.Date());
                                    OrderManager.saveOrder(getContext(), 10003, date, 4);
                                }
                                getView().showView();
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
//                    getView().showView();
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
