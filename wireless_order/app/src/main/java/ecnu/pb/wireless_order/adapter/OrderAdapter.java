package ecnu.pb.wireless_order.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import butterknife.Bind;
import butterknife.ButterKnife;
import ecnu.pb.wireless_order.R;
import ecnu.pb.wireless_order.activity.MenuActivity;
import ecnu.pb.wireless_order.activity.OrderDetailActivity;
import ecnu.pb.wireless_order.activity.PayActivity;
import ecnu.pb.wireless_order.activity.PlaceOrderActivity;
import ecnu.pb.wireless_order.database.OrderManager;

public class OrderAdapter extends BaseAdapter implements View.OnClickListener {

    private Context context;

    private String[] status = {"订单未完成", "订单已完成"};

    public OrderAdapter(Context context) {
        this.context = context;
    }

    @Override
    public int getCount() {
        return 1;
//        return status.length;
    }

    @Override
    public Object getItem(int position) {
        return position;
//        return status[position];
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view;
        if (convertView != null) {
            view = convertView;
        } else {
            view = LayoutInflater.from(context).inflate(R.layout.order_list_item, null);
        }

        ViewHolder holder = new ViewHolder(view);
        if (OrderManager.getOrderStatus(context) == 1) {
            holder.txtStatus.setText(status[1]);
            holder.btnMore.setVisibility(View.VISIBLE);
            holder.btnOrder.setVisibility(View.GONE);
            holder.btnPay.setVisibility(View.GONE);
        } else {
            holder.txtStatus.setText(status[0]);
            holder.btnPay.setVisibility(View.VISIBLE);
            holder.btnMore.setVisibility(View.GONE);
            holder.btnOrder.setVisibility(View.VISIBLE);
        }

        holder.txtNumber.setText(OrderManager.getOrderId(context) + "");
        holder.txtTime.setText(OrderManager.getOrderDate(context));
        holder.txtTotal.setText(OrderManager.getOrderSum(context) + "");
        holder.btnOrder.setOnClickListener(this);
        holder.btnMore.setOnClickListener(this);
        holder.btnPay.setOnClickListener(this);
        holder.layoutOrder.setOnClickListener(this);
        return view;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.txt_btn_order:
                // 加菜
                context.startActivity(new Intent(context, MenuActivity.class));
                break;
            case R.id.txt_btn_pay:
                context.startActivity(new Intent(context, PayActivity.class));
                break;
            case R.id.txt_btn_onemore:
                // 再来一单
                context.startActivity(new Intent(context, PlaceOrderActivity.class));
                break;
            case R.id.layout_order:
                context.startActivity(new Intent(context, OrderDetailActivity.class));
                break;
            default:
                break;
        }
    }

    class ViewHolder {
        @Bind(R.id.txt_order_status)
        TextView txtStatus;

        @Bind(R.id.txt_order_number)
        TextView txtNumber;

        @Bind(R.id.txt_order_time)
        TextView txtTime;

        @Bind(R.id.txt_order_total)
        TextView txtTotal;

        @Bind(R.id.txt_btn_order)
        TextView btnOrder;

        @Bind(R.id.txt_btn_onemore)
        TextView btnMore;

        @Bind(R.id.txt_btn_pay)
        TextView btnPay;

        @Bind(R.id.layout_order)
        LinearLayout layoutOrder;

        public ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
