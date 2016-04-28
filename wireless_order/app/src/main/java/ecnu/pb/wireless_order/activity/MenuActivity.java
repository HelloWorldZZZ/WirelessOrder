package ecnu.pb.wireless_order.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import ecnu.pb.wireless_order.R;
import ecnu.pb.wireless_order.adapter.MenuTabAdapter;

public class MenuActivity extends AppCompatActivity implements ViewPager.OnPageChangeListener {

    private MenuTabAdapter adapter;

    @Bind(R.id.txt_btn_recommendTab) TextView mRecommend;
    @Bind(R.id.txt_btn_otherTab) TextView mOther;
    @Bind(R.id.txt_btn_vipTab) TextView mVip;
    @Bind(R.id.viewPager) ViewPager mViewPager;

    @OnClick(R.id.txt_btn_recommendTab)
    void onClickRecommendTab() {
        mViewPager.setCurrentItem(0, true);
    }

    @OnClick(R.id.txt_btn_otherTab)
    void onClickOtherTab() {
        mViewPager.setCurrentItem(1, true);
    }

    @OnClick(R.id.txt_btn_vipTab)
    void onClickVipTab() {
        mViewPager.setCurrentItem(2, true);
    }

    @OnClick(R.id.txt_btn_order)
    void onClickOrder() {
        startActivityForResult(new Intent(this, PlaceOrderActivity.class), 1);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case 1:
                if (resultCode == 1) {
                    finish();
                }
                break;
            default:
                break;
        }

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        ButterKnife.bind(this);
        init();
    }

    private void init() {
        adapter = new MenuTabAdapter(getSupportFragmentManager());
        mViewPager.setAdapter(adapter);
        mViewPager.addOnPageChangeListener(this);
        mViewPager.setOffscreenPageLimit(2);
        onPageSelected(0);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        mRecommend.setSelected(false);
        mOther.setSelected(false);
        mVip.setSelected(false);
        switch (position) {
            case 0:
                mRecommend.setSelected(true);
                break;
            case 1:
                mOther.setSelected(true);
                break;
            case 2:
                mVip.setSelected(true);
                break;
        }
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

}
