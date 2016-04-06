package com.wirelessorder.adminsystem.func;

import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.util.LruCache;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.NetworkImageView;
import com.android.volley.toolbox.Volley;
import com.wirelessorder.adminsystem.R;
import com.wirelessorder.adminsystem.po.Meal;
import com.wirelessorder.adminsystem.service.MealService;

import java.util.ArrayList;

public class MenuActivity extends BaseActivity {
    private Context mContext;
    private boolean mHasChanged = false;
    private RecyclerView mRecyclerView;
    private ArrayList<Meal> mealList;
    private MealAdapter mealAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = this;
        setContentView(R.layout.activity_menu);
        setActionBarTitle(R.string.meal_manage);
        initMenu();
    }

    private void initMenu() {
        initMealList();
        mRecyclerView = (RecyclerView) findViewById(R.id.list);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mRecyclerView.setHasFixedSize(true);
        mealAdapter = new MealAdapter(mContext, mealList);
        mRecyclerView.setAdapter(mealAdapter);
        FloatingActionButton btnUpload = (FloatingActionButton) findViewById(R.id.upload);
        btnUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    private void initMealList() {
        mealList = new ArrayList<>();
        MealService mealService = new MealService(mContext);
        mealList = (ArrayList) mealService.getAllMeals();
    }

    private void showMealEditDialog(final Meal meal) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View markView = inflater.inflate(R.layout.meal_edit, null);
        final EditText editMealName = (EditText) markView.findViewById(R.id.m_meal_name);
        final EditText editMealPrice = (EditText) markView.findViewById(R.id.m_meal_price);
        final EditText editMealInfo = (EditText) markView.findViewById(R.id.m_meal_info);
        editMealName.setText(meal.getMealName());
        editMealPrice.setText(String.valueOf(meal.getMealPrice()));
        editMealInfo.setText(meal.getMealInfo());

        AlertDialog editDialog = new AlertDialog.Builder(mContext)
                .setTitle(getString(R.string.title_meal_edit))
                .setPositiveButton(getString(R.string.modify), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        meal.setMealName(editMealName.getText().toString());
                        meal.setMealPrice(Double.parseDouble(editMealPrice.getText().toString()));
                        meal.setMealInfo(editMealInfo.getText().toString());
                        mealAdapter.notifyDataSetChanged();
                        mHasChanged = true;
                    }
                })
                .setNegativeButton(getString(R.string.cancel), null)
                .setView(markView)
                .create();
        editDialog.show();
    }

    public class MealAdapter
            extends RecyclerView.Adapter<MealAdapter.ViewHolder>
    {

        private ArrayList<Meal> mealList;

        private Context mContext;

        public MealAdapter( Context context , ArrayList<Meal> mealList)
        {
            this.mContext = context;
            this.mealList = mealList;
        }

        @Override
        public ViewHolder onCreateViewHolder( ViewGroup viewGroup, int i )
        {
            // 给ViewHolder设置布局文件
            View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.card_meal, viewGroup, false);
            return new ViewHolder(v);
        }

        @Override
        public void onBindViewHolder( ViewHolder viewHolder, int i )
        {
            // 给ViewHolder设置元素
            final Meal p = mealList.get(i);
            RequestQueue requestQueue = Volley.newRequestQueue(mContext);
            ImageLoader imageLoader = new ImageLoader(requestQueue, new BitmapCache());
            viewHolder.mealName.setText(p.getMealName());
            viewHolder.mealPrice.setText("￥ " + String.valueOf(p.getMealPrice()));
            viewHolder.mealImage.setDefaultImageResId(R.mipmap.image_loading);
            viewHolder.mealImage.setErrorImageResId(R.mipmap.error);
            viewHolder.mealImage.setImageUrl(p.getMealImage(), imageLoader);
            viewHolder.rootView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    showMealEditDialog(p);
                }
            });
        }

        @Override
        public int getItemCount()
        {
            // 返回数据总数
            return mealList == null ? 0 : mealList.size();
        }

        // 重写的自定义ViewHolder
        public class ViewHolder
                extends RecyclerView.ViewHolder
        {
            public TextView mealName, mealPrice;
            public NetworkImageView mealImage;
            public View rootView;


            public ViewHolder( View v )
            {
                super(v);
                rootView = v;
                mealImage = (NetworkImageView) v.findViewById(R.id.mealImage);
                mealName = (TextView) v.findViewById(R.id.mealName);
                mealPrice = (TextView) v.findViewById(R.id.mealPrice);
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    public class BitmapCache implements ImageLoader.ImageCache {

        private LruCache<String, Bitmap> mCache;

        public BitmapCache() {
            int maxSize = 10 * 1024 * 1024;
            mCache = new LruCache<String, Bitmap>(maxSize) {
                @Override
                protected int sizeOf(String key, Bitmap bitmap) {
                    return bitmap.getRowBytes() * bitmap.getHeight();
                }
            };
        }

        @Override
        public Bitmap getBitmap(String url) {
            return mCache.get(url);
        }

        @Override
        public void putBitmap(String url, Bitmap bitmap) {
            mCache.put(url, bitmap);
        }

    }

}
