package ecnu.pb.wireless_order.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import ecnu.pb.wireless_order.R;
import ecnu.pb.wireless_order.database.OrderManager;

public class PayActivity extends AppCompatActivity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pay);
        TextView textView = (TextView) findViewById(R.id.pay_bill);
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AlertDialog.Builder(PayActivity.this)
                        .setTitle("提示")
                        .setMessage("是否支付")
                        .setNegativeButton("取消", null)
                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                OrderManager.setOrderStatus(getApplicationContext(), 1);
                                dialog.dismiss();
                                PayActivity.this.finish();
                            }
                        })
                        .show();
            }
        });
    }
}
