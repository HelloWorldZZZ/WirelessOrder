package ecnu.pb.wireless_order.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.zanlabs.widget.infiniteviewpager.InfiniteViewPager;
import com.zanlabs.widget.infiniteviewpager.indicator.CirclePageIndicator;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import ecnu.pb.wireless_order.R;
import ecnu.pb.wireless_order.adapter.BannerAdapter;
import ecnu.pb.wireless_order.database.AccountManager;

public class MainActivity extends AppCompatActivity {

    private BannerAdapter bannerAdapter;

    @Bind(R.id.toolbar)
    Toolbar toolbar;

    @Bind(R.id.ivp_banners)
    InfiniteViewPager infiniteViewPager;

    @Bind(R.id.circlePageIndicator)
    CirclePageIndicator circlePageIndicator;

    @OnClick(R.id.hgb_userBtn)
    void OnClickUser() {
        if (AccountManager.isSignin(this)) {
            startActivity(new Intent(this, UserActivity.class));
        } else {
            startActivity(new Intent(this, SignInActivity.class));
        }
    }

    @OnClick(R.id.hgb_menuBtn)
    void OnClickMenu() {
        startActivity(new Intent(this, MenuActivity.class));
    }

    @OnClick(R.id.hgb_orderBtn)
    void OnClickOrder() {
        startActivity(new Intent(this, OrderActivity.class));
    }

    @OnClick(R.id.hgb_payBtn)
    void OnClickPay() {
        startActivity(new Intent(this, PayActivity.class));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        init();
    }

    private void init() {
        toolbar.setTitle(R.string.title_main);
        bannerAdapter = new BannerAdapter(this);
        infiniteViewPager.setAdapter(bannerAdapter);
        circlePageIndicator.setViewPager(infiniteViewPager);
        infiniteViewPager.setAutoScrollTime(5000);
        infiniteViewPager.startAutoScroll();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        return super.onOptionsItemSelected(item);
    }

}
