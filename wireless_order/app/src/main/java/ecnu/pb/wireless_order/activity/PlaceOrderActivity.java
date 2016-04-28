package ecnu.pb.wireless_order.activity;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import ecnu.pb.wireless_order.R;
import ecnu.pb.wireless_order.adapter.PlaceOrderAdapter;
import ecnu.pb.wireless_order.database.AccountManager;
import ecnu.pb.wireless_order.database.MenuManager;
import ecnu.pb.wireless_order.database.OrderManager;
import ecnu.pb.wireless_order.model.MealModel;
import ecnu.pb.wireless_order.presenter.PlaceOrderPresenter;

public class PlaceOrderActivity extends AppCompatActivity implements PlaceOrderPresenter.View {

    private PlaceOrderAdapter adapter;
    private List<MealModel> list = new ArrayList<>();
    private PlaceOrderPresenter mPresenter;

    @Bind(R.id.lv_place_list)
    ListView listView;

    @OnClick(R.id.txt_btn_placeOrder)
    void OnClickOrder() {
//        final EditText et = new EditText(this);
//        et.setInputType(InputType.TYPE_CLASS_PHONE);
//
//        new AlertDialog.Builder(this).setTitle("请输入就餐人数")
//                .setIcon(android.R.drawable.ic_dialog_info)
//                .setView(et)
//                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
//                    public void onClick(DialogInterface dialog, int which) {
//                        String input = et.getText().toString();
//                        if (input.equals("")) {
//                            Toast.makeText(getApplicationContext(), "输入内容不能为空！" + input, Toast.LENGTH_LONG).show();
//                        } else {
//                            String order = "{"
//                                    +   "\"userId\":" + AccountManager.getKeyUserId(getApplicationContext()) + ","
//                                    +   "\"userAmount\":" + 4 + ","
//                                    +   "\"orderSum\":" + OrderManager.getOrderSum(getApplicationContext()) + ","
//                                    +   "\"mealList\":" + "{" + "}"
//                                    +   "}";
//                            mPresenter.placeOrder(order);
//                        }
//                    }
//                })
//                .setNegativeButton("取消", null)
//                .show();

        List<MealModel> menu = MenuManager.createInstance().getMenu(this);
        int sum = 0;
        for (MealModel meal : menu) {
            sum = sum + meal.getMeal_price();
        }
        OrderManager.setOrderSum(this, sum);
        String order = "{"
                + "\"userId\":" + AccountManager.getKeyUserId(getApplicationContext()) + ","
                + "\"userAmount\":" + 4 + ","
                + "\"orderSum\":" + OrderManager.getOrderSum(getApplicationContext()) + ","
                + "\"mealList\":" + "{" + "}"
                + "}";
        mPresenter.placeOrder(order);

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_place_order);
        ButterKnife.bind(this);
        init();
    }

    private void init() {
        mPresenter = new PlaceOrderPresenter();
        mPresenter.attachView(this);

//        MenuData menuData = new MenuData();
//        list = menuData.getData();
        list = MenuManager.createInstance().getMenu(this);
        adapter = new PlaceOrderAdapter(this, list);
        listView.setAdapter(adapter);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mPresenter.detachView();
    }

    @Override
    public void showView() {
        setResult(1);
        finish();
    }

    @Override
    public Context getViewContext() {
        return this;
    }

    @Override
    public void destroyView() {
        finish();
    }
}
