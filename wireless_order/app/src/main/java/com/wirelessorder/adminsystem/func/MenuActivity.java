package com.wirelessorder.adminsystem.func;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.util.LruCache;
import android.support.v7.app.AlertDialog;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import ecnu.pb.wireless_order.R;
import com.wirelessorder.adminsystem.po.Meal;
import com.wirelessorder.adminsystem.service.MealService;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class MenuActivity extends BaseActivity {
    private Context mContext;
    private RecyclerView mRecyclerView;
    private ArrayList<Meal> mealList;
    private MealAdapter mealAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = this;
        setContentView(R.layout.activity_admin_menu);
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
        FloatingActionButton btnUpload = (FloatingActionButton) findViewById(R.id.add);
        btnUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivityForResult(new Intent(mContext, MealAddActivity.class), 1);
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
                        updateMeal(meal);
                    }
                })
                .setNegativeButton(getString(R.string.cancel), null)
                .setView(markView)
                .create();
        editDialog.show();
    }

    private void showDeleteDialog(final Meal meal) {
        AlertDialog deleteDialog = new AlertDialog.Builder(mContext)
                .setTitle(getString(R.string.meal_manage))
                .setMessage("确定删除" + meal.getMealName() + "?")
                .setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        deleteMeal(meal);
                    }
                })
                .setNegativeButton(R.string.cancel, null)
                .create();
        deleteDialog.show();
    }

    private void deleteMeal(final Meal meal) {
        final ProgressDialog pDialog = ProgressDialog.show(mContext, null, "删除菜品中...", true, true);
        pDialog.setCancelable(false);
        String url = "http://www.zhouzezhou.site/WirelessOrder/servlet/MealDeleteServlet";
        RequestQueue requestQueue = Volley.newRequestQueue(mContext);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String s) {
                        pDialog.dismiss();
                        Toast.makeText(mContext, "删除成功", Toast.LENGTH_SHORT).show();
                        mealList.remove(meal);
                        MealService mealService = new MealService(mContext);
                        mealService.deleteMeal(meal.getMealId());
                        mealService.closeDB();
                        mealAdapter.notifyDataSetChanged();
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                pDialog.dismiss();
                Toast.makeText(mContext, "删除失败", Toast.LENGTH_SHORT).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> map = new HashMap<String, String>();
                map.put("meal_id", String.valueOf(meal.getMealId()));
                return map;
            }
        };
        requestQueue.add(stringRequest);
    }

    private void updateMeal(final Meal meal) {
        final ProgressDialog pDialog = ProgressDialog.show(mContext, null, "修改菜品", true, true);
        pDialog.setCancelable(false);
        String url = "http://www.zhouzezhou.site/WirelessOrder/servlet/MealModifyServlet";
        RequestQueue requestQueue = Volley.newRequestQueue(mContext);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String s) {
                        pDialog.dismiss();
                        Toast.makeText(mContext, "修改成功", Toast.LENGTH_SHORT).show();
                        mealAdapter.notifyDataSetChanged();
                        MealService mealService = new MealService(mContext);
                        mealService.updateMeal(meal);
                        mealService.closeDB();
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                pDialog.dismiss();
                Toast.makeText(mContext, "修改失败", Toast.LENGTH_SHORT).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> map = new HashMap<String, String>();
                map.put("meal_id", String.valueOf(meal.getMealId()));
                map.put("meal_name", meal.getMealName());
                map.put("meal_price", String.valueOf(meal.getMealPrice()));
                map.put("meal_info", meal.getMealInfo());
                return map;
            }
        };
        requestQueue.add(stringRequest);
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
            viewHolder.deleteBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    showDeleteDialog(p);
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
            public ImageView deleteBtn;
            public View rootView;


            public ViewHolder( View v )
            {
                super(v);
                rootView = v;
                mealImage = (NetworkImageView) v.findViewById(R.id.mealImage);
                mealName = (TextView) v.findViewById(R.id.mealName);
                mealPrice = (TextView) v.findViewById(R.id.mealPrice);
                deleteBtn = (ImageView) v.findViewById(R.id.delete);
            }
        }
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            Meal meal = (Meal) data.getSerializableExtra("newMeal");
            mealList.add(meal);
        }
    }

}
