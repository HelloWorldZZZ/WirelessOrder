package ecnu.pb.wireless_order.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import ecnu.pb.wireless_order.R;
import ecnu.pb.wireless_order.database.MenuManager;
import ecnu.pb.wireless_order.model.MealModel;
import ecnu.pb.wireless_order.widget.ToastUtils;
import ecnu.pb.wireless_order.widget.VolleyLoadPicture;

public class MenuDetailActivity extends AppCompatActivity {

    public static final String MENU_DETAIL = ".menu";
    private MealModel mealModel;

    @Bind(R.id.img_background)
    ImageView imageView;
    @Bind(R.id.txt_name)
    TextView mName;
    @Bind(R.id.txt_price)
    TextView mPrice;
    @Bind(R.id.txt_intro)
    TextView mIntro;

    @OnClick(R.id.txt_btn_order)
    void OnClickOrder() {
        MenuManager.createInstance().save(this, mealModel);
        finish();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_detail);
        ButterKnife.bind(this);
        init();
    }

    private void init() {
        Bundle bundle = getIntent().getExtras();
        mealModel = bundle.getParcelable("menu");
        if (mealModel != null) {
            mName.setText(mealModel.getMeal_name());
            mPrice.setText("$"+mealModel.getMeal_price());
            mIntro.setText(mealModel.getMeal_intro());
            VolleyLoadPicture vlp = new VolleyLoadPicture(this, imageView);
            vlp.getmImageLoader().get(mealModel.getMeal_image_url(), vlp.getOne_listener());
        } else {
            ToastUtils.showToast(this, "null");
        }
//        int photo = getIntent().getIntExtra("photo", R.mipmap.img0);
//        int position = getIntent().getIntExtra("position", 1);
//        mName.setText("菜品"+position);
//        mPrice.setText("$ " + position + 10);
//        mIntro.setText("菜品特色介绍");
//        imageView.setImageResource(photo);
    }
}
