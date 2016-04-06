package com.wirelessorder.adminsystem.func;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.wirelessorder.adminsystem.R;
import com.wirelessorder.adminsystem.service.MealService;
import com.wirelessorder.adminsystem.service.OrderService;
import com.wirelessorder.adminsystem.service.UserService;

public class MainActivity extends ActionBarActivity {
    private Context mContext;
    private DrawerLayout mDrawerLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = this;
        setContentView(R.layout.activity_main);
        initView();
    }

    private void initView() {
        Toolbar mToolbar = (Toolbar) findViewById(R.id.toolbar);
        mToolbar.setTitleTextColor(Color.WHITE);
        setSupportActionBar(mToolbar);
        //设置抽屉DrawerLayout
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, mToolbar,
                R.string.drawer_open, R.string.drawer_close);
        mDrawerToggle.syncState();//初始化状态
        mDrawerLayout.setDrawerListener(mDrawerToggle);
        NavigationView mNavigationView = (NavigationView) findViewById(R.id.navigation_view);
        mNavigationView.setNavigationItemSelectedListener(new AdminNavigationListener());
        initInfoView();
    }

    private void initInfoView() {
        LinearLayout infoView = (LinearLayout) this.findViewById(R.id.infoPage);
        TextView userAmount = (TextView) infoView.findViewById(R.id.user_amount);
        TextView mealAmount = (TextView) infoView.findViewById(R.id.meal_amount);
        TextView orderAmount = (TextView) infoView.findViewById(R.id.order_amount);
        TextView orderSum = (TextView) infoView.findViewById(R.id.order_sum);
        UserService userService = new UserService(mContext);
        MealService mealService = new MealService(mContext);
        OrderService orderService = new OrderService(mContext);
        userAmount.setText(String.valueOf(userService.getUserAmount()));
        mealAmount.setText(String.valueOf(mealService.getMealAmount()));
        orderAmount.setText(String.valueOf(orderService.getOrderAmount()));
        orderSum.setText(String.valueOf(orderService.getAllOrderSum()));
        userService.closeDB();
        mealService.closeDB();
        orderService.closeDB();
    }

    private class AdminNavigationListener implements NavigationView.OnNavigationItemSelectedListener {

        @Override
        public boolean onNavigationItemSelected(MenuItem menuItem) {
            switch (menuItem.getItemId()) {
                case R.id.meal:
                    Intent intent = new Intent(mContext, MenuActivity.class);
                    startActivity(intent);
                    break;
                case R.id.update:
                    break;
                case R.id.logout:
                    showExitDialog();
                    break;
            }
            menuItem.setChecked(true);//点击了把它设为选中状态
            mDrawerLayout.closeDrawers();//关闭抽屉
            return true;
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            showExitDialog();
        }
        return super.onKeyDown(keyCode, event);
    }

    private void showExitDialog() {
        AlertDialog exitDialog = new AlertDialog.Builder(mContext)
                .setTitle(getString(R.string.app_name))
                .setMessage("退出系统并注销?")
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                    }
                })
                .setNegativeButton("取消", null)
                .create();
        exitDialog.show();
    }

}
