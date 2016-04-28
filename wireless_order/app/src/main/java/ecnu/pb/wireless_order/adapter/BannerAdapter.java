package ecnu.pb.wireless_order.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.zanlabs.widget.infiniteviewpager.InfinitePagerAdapter;

import butterknife.Bind;
import butterknife.ButterKnife;
import ecnu.pb.wireless_order.R;

public class BannerAdapter extends InfinitePagerAdapter {
    private Context mContext;
    private int[] pics = {R.mipmap.img0, R.mipmap.img1,R.mipmap.img2,
        R.mipmap.img3,R.mipmap.img4, R.mipmap.img5};

    public BannerAdapter(Context context) {
        mContext = context;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup container) {
        View view;
        if (convertView != null) {
            view = convertView;
        } else {
            view = LayoutInflater.from(mContext).inflate(R.layout.pager_item_banner, container, false);
        }

        ViewHolder holder = new ViewHolder(view);
        holder.imageView.setImageResource(pics[position]);
        holder.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(mContext, ""+position, Toast.LENGTH_SHORT).show();
            }
        });
        holder.textView.setText(position+"");
        return view;
    }

    @Override
    public int getItemCount() {
        return pics.length;
    }


    class ViewHolder {
        @Bind(R.id.imageView)
        ImageView imageView;
        @Bind(R.id.txt_title)
        TextView textView;

        public ViewHolder(View view) {
            ButterKnife.bind(this, view);
            ButterKnife.bind(this, view);
        }
    }
}
