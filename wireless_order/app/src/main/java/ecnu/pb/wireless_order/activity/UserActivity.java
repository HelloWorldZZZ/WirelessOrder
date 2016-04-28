package ecnu.pb.wireless_order.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import ecnu.pb.wireless_order.R;
import ecnu.pb.wireless_order.database.AccountManager;
import ecnu.pb.wireless_order.database.MenuManager;
import ecnu.pb.wireless_order.database.OrderManager;

public class UserActivity extends AppCompatActivity {

    @Bind(R.id.img_photo)
    ImageView mPhoto;
    @Bind(R.id.txt_name)
    TextView mName;

    @OnClick(R.id.txt_btn_signout)
    void OnSignOut() {
        AccountManager.signout(this);
        OrderManager.clearOrder(this);
        MenuManager.createInstance().clear(this);
        finish();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);
        ButterKnife.bind(this);
        init();
    }

    private void init() {
        mPhoto.setImageResource(R.mipmap.ic_photo_90dp);
        mName.setText(AccountManager.getUserName(this));
    }
}
