package ecnu.pb.wireless_order.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;

import butterknife.Bind;
import butterknife.ButterKnife;
import ecnu.pb.wireless_order.R;
import ecnu.pb.wireless_order.adapter.OrderAdapter;

public class OrderActivity extends AppCompatActivity{

    private OrderAdapter mAdapter;

    @Bind(R.id.lv_order)
    ListView listView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);
        ButterKnife.bind(this);

        init();
    }

    private void init() {
        mAdapter = new OrderAdapter(this);
        listView.setAdapter(mAdapter);
    }
}
