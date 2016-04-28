package ecnu.pb.wireless_order.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import ecnu.pb.wireless_order.R;
import ecnu.pb.wireless_order.adapter.OrderDetailAdapter;
import ecnu.pb.wireless_order.database.MenuManager;
import ecnu.pb.wireless_order.database.OrderManager;
import ecnu.pb.wireless_order.model.MealModel;
import ecnu.pb.wireless_order.model.OrderModel;

public class OrderDetailActivity extends AppCompatActivity {

    public static final String ORDER_DETAIL = ".order";
    public static final String ORDER_AGAIN = ".again";

    private OrderModel orderModel = new OrderModel();
    private Bundle bundle;
    private List<MealModel> orderList = new ArrayList<>();
    private OrderDetailAdapter adapter;

    @Bind(R.id.txt_order_number)
    TextView mNumber;
    @Bind(R.id.txt_order_time)
    TextView mTime;
    @Bind(R.id.txt_order_people)
    TextView mPeople;
    @Bind(R.id.txt_order_total)
    TextView mTotal;
    @Bind(R.id.txt_btn_pay)
    TextView mBtn;
    @Bind(R.id.lv_order_detail_list)
    ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_detail);
        ButterKnife.bind(this);

        init();
    }

    private void init() {
//        bundle = getIntent().getExtras();
//        orderModel = (OrderModel) bundle.getSerializable(ORDER_DETAIL);
//        orderModel = new OrderModel(101123, "2016.4.1", 4, 0, 355, null);
//        if (orderModel != null) {
//            if (orderModel.getStatus() == 0 ) {
                mBtn.setText("结算");
                mBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        startActivity(new Intent(OrderDetailActivity.this, PayActivity.class));
                    }
                });
//            } else {
//                mBtn.setText("再来一单");
//                mBtn.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        Intent intent = new Intent(OrderDetailActivity.this, PlaceOrderActivity.class);
//                        intent.putExtra(ORDER_AGAIN, bundle);
//                        startActivity(intent);
//                    }
//                });
//            }
            mNumber.setText(OrderManager.getOrderId(this)+"");
            mTime.setText(OrderManager.getOrderDate(this));
            mPeople.setText(OrderManager.getCount(this)+"人");
            mTotal.setText(OrderManager.getOrderSum(this)+"元");
//            orderList = orderModel.getList();
//            MenuData menuData = new MenuData();
            orderList = MenuManager.createInstance().getMenu(this);
//        } else {
//            ToastUtils.showToast(this, "null");
//        }

        adapter = new OrderDetailAdapter(this, orderList);
        listView.setAdapter(adapter);
        setListViewHeightBasedOnChildren(listView);
    }

    public void setListViewHeightBasedOnChildren(ListView listView) {
        // 获取ListView对应的Adapter
        ListAdapter listAdapter = listView.getAdapter();
        if (listAdapter == null) {
            return;
        }

        int totalHeight = 0;
        for (int i = 0, len = listAdapter.getCount(); i < len; i++) {
            // listAdapter.getCount()返回数据项的数目
            View listItem = listAdapter.getView(i, null, listView);
            // 计算子项View 的宽高
            listItem.measure(0, 0);
            // 统计所有子项的总高度
            totalHeight += listItem.getMeasuredHeight();
        }

        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = totalHeight+ (listView.getDividerHeight() * (listAdapter.getCount() - 1));
        // listView.getDividerHeight()获取子项间分隔符占用的高度
        // params.height最后得到整个ListView完整显示需要的高度
        listView.setLayoutParams(params);
    }
}
