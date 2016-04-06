package com.wirelessorder.adminsystem.func;

import android.app.ProgressDialog;
import android.content.ActivityNotFoundException;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Looper;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.wirelessorder.adminsystem.R;
import com.wirelessorder.adminsystem.po.Meal;
import com.wirelessorder.adminsystem.service.MealService;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.protocol.HTTP;
import org.apache.http.protocol.HttpContext;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.charset.Charset;

public class MealAddActivity extends BaseActivity {
    private final int GALLERY_RESULT = 0;
    private ImageView mealImage;
    private boolean hasImage = false;
    private String imagePath;
    private Meal meal;
    private Context mContext;
    private ProgressDialog pDialog;

    private Thread uploadThread = new Thread(new Runnable() {
        @Override
        public void run() {
            Looper.prepare();
            doPost(meal, imagePath);
            Looper.loop();
        }
    });

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = this;
        meal = new Meal();
        setContentView(R.layout.activity_meal_add);
        setActionBarTitle(R.string.meal_add);
        initView();
    }

    private void initView() {
        mealImage = (ImageView) findViewById(R.id.meal_image);
        mealImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent galleryIntent = new Intent(
                        Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                try {
                    startActivityForResult(galleryIntent, GALLERY_RESULT);
                } catch (ActivityNotFoundException e) {
                    //do nothing
                }
            }
        });
        final RadioGroup mealType = (RadioGroup) findViewById(R.id.meal_type);
        final EditText mealName = (EditText) findViewById(R.id.meal_name);
        final EditText mealPrice = (EditText) findViewById(R.id.meal_price);
        final EditText mealInfo = (EditText) findViewById(R.id.meal_info);
        Button addButton = (Button) findViewById(R.id.addMeal);
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!mealName.getText().toString().isEmpty() && !mealPrice.getText().toString().isEmpty()
                        && !mealInfo.getText().toString().isEmpty() && hasImage) {
                    int type = getMealType(mealType);
                    meal.setMealType(type);
                    meal.setMealName(mealName.getText().toString());
                    meal.setMealPrice(Double.parseDouble(mealPrice.getText().toString()));
                    meal.setMealInfo(mealInfo.getText().toString());
                    pDialog = ProgressDialog.show(mContext, null, "添加上传菜品中...", true, true);
                    pDialog.setCancelable(false);
                    new Thread(uploadThread).start();
                } else {
                    Toast.makeText(mContext, "请填写完整信息", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private int getMealType(RadioGroup group) {
        int mealType = 0;
        switch (group.getCheckedRadioButtonId()) {
            case R.id.cold:
                mealType = 1;
                break;
            case R.id.hot:
                mealType = 2;
                break;
            case R.id.main:
                mealType = 3;
                break;
        }
        return mealType;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            ContentResolver resolver = getContentResolver();
            Uri originalUri = data.getData();
            imagePath = getFilePath(originalUri);
            try {
                Bitmap photo = MediaStore.Images.Media.getBitmap(resolver, originalUri);
                if (photo != null) {
                    mealImage.setImageBitmap(photo);
                    hasImage = true;
                }
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private String getFilePath(Uri uri) {
        String res = null;
        String[] proj = { MediaStore.Images.Media.DATA };
        Cursor cursor = getContentResolver().query(uri, proj, null, null, null);
        if(cursor.moveToFirst()){;
            int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            res = cursor.getString(column_index);
        }
        cursor.close();
        return res;
    }

    private void doPost(Meal meal, String imagePath) {
        final ProgressDialog pDialog = ProgressDialog.show(mContext, null, "添加上传菜品中...", true, true);
        pDialog.setCancelable(false);
        String url = "http://www.zhouzezhou.site/WirelessOrder/servlet/MealAddServlet";
        HttpContext localContext = new BasicHttpContext();
        HttpClient httpclient = new DefaultHttpClient();
        HttpPost httpPost = new HttpPost(url);
        MultipartEntity entity = new MultipartEntity();
        try {
            entity.addPart("mealType", new StringBody(String.valueOf(meal.getMealType())));
            entity.addPart("mealName", new StringBody(meal.getMealName(), Charset.forName("UTF-8")));
            entity.addPart("mealPrice", new StringBody(String.valueOf(meal.getMealPrice())));
            entity.addPart("mealInfo", new StringBody(meal.getMealInfo(), Charset.forName("UTF-8")));
            entity.addPart("mealImage", new FileBody(new File(imagePath)));
            httpPost.setEntity(entity);

            HttpResponse response = httpclient.execute(httpPost, localContext);
            HttpEntity resEntity = response.getEntity();
            if (resEntity != null) {
                String result = EntityUtils.toString(resEntity, HTTP.UTF_8);
                JSONObject mealJson = new JSONObject(result);
                Meal newMeal = new Meal(mealJson.getInt("mealType"), mealJson.getString("mealName"),
                        mealJson.getDouble("mealPrice"), mealJson.getString("mealImage"),
                        mealJson.getString("mealInfo"));
                newMeal.setMealId(mealJson.getInt("mealId"));
                MealService mealService = new MealService(mContext);
                mealService.insertMeal(newMeal);
                pDialog.dismiss();
                Toast.makeText(mContext, "上传成功", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent();
                intent.putExtra("newMeal", newMeal);
                MealAddActivity.this.setResult(RESULT_OK, intent);
                finish();
            }
        } catch (Exception e) {
            Toast.makeText(mContext, "上传失败", Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
    }

}
