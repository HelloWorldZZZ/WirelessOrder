package ecnu.pb.wireless_order.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import ecnu.pb.wireless_order.R;
import ecnu.pb.wireless_order.activity.MenuDetailActivity;
import ecnu.pb.wireless_order.database.MenuManager;
import ecnu.pb.wireless_order.model.MealModel;
import ecnu.pb.wireless_order.widget.VolleyLoadPicture;

public class MenuFragmentAdapter extends BaseAdapter {

    private int[] pics = {R.mipmap.img0, R.mipmap.img1, R.mipmap.img2,
            R.mipmap.img3, R.mipmap.img4, R.mipmap.img5};

    private Context context;

    public MenuFragmentAdapter(Context context, int[] pics) {

        this.context = context;
        this.pics = pics;
    }

    private List<MealModel> mealModels = new ArrayList<>();

    public MenuFragmentAdapter(Context context, List<MealModel> mealModels) {
        this.context = context;
        this.mealModels = mealModels;
    }

    @Override
    public int getCount() {

        return mealModels.size();
//        return pics.length;

    }

    @Override
    public Object getItem(int position) {

        return mealModels.get(position);
//        return pics[position];
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, final ViewGroup parent) {
        View view;
        if (convertView != null) {
            view = convertView;
        } else {
            view = LayoutInflater.from(context).inflate(R.layout.menu_item, null);
        }

        final MealModel mealModel = mealModels.get(position);

        ViewHolder holder = new ViewHolder(view);

        VolleyLoadPicture vlp = new VolleyLoadPicture(context, holder.imageView);
        vlp.getmImageLoader().get(mealModel.getMeal_image_url(), vlp.getOne_listener());
        holder.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, MenuDetailActivity.class);
                Bundle bundle = new Bundle();
                bundle.putParcelable("menu", mealModel);
                intent.putExtras(bundle);
                context.startActivity(intent);
            }
        });
        holder.name.setText(mealModel.getMeal_name());
        holder.price.setText("$" + mealModel.getMeal_price());
        holder.checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                MenuManager.createInstance().save(context, mealModel);
            }
        });
        return view;
    }

    class ViewHolder {
        @Bind(R.id.img_menu)
        ImageView imageView;
        @Bind(R.id.txt_menu_name)
        TextView name;
        @Bind(R.id.txt_menu_price)
        TextView price;
        @Bind(R.id.cb_choose)
        CheckBox checkBox;

        public ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}