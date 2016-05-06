package ecnu.pb.wireless_order.fragment;

import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;

import com.wirelessorder.adminsystem.dao.MealDao;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import ecnu.pb.wireless_order.R;
import ecnu.pb.wireless_order.adapter.MenuFragmentAdapter;
import ecnu.pb.wireless_order.model.MealModel;
import ecnu.pb.wireless_order.model.MenuModel;
import ecnu.pb.wireless_order.presenter.MenuPresenter;

public class RecommendFragment extends Fragment implements MenuPresenter.View{

    private int[] pics = {R.mipmap.img0, R.mipmap.img1, R.mipmap.img2,
            R.mipmap.img3, R.mipmap.img4, R.mipmap.img5};

    private MenuFragmentAdapter mAdapter;

    private MenuPresenter presenter;

    public RecommendFragment() {

    }

    @Bind(R.id.gv_menu)
    GridView gridView;

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        MealDao mealDao = new MealDao(getActivity());
        Cursor c = mealDao.getMealsByType("1");
        List<MealModel> list = new ArrayList<>();
        while (c.moveToNext()) {
            int mealId = c.getInt(c.getColumnIndex("meal_id"));
            int mealType = c.getInt(c.getColumnIndex("meal_type_id"));
            String mealName = c.getString(c.getColumnIndex("meal_name"));
            int mealPrice = c.getInt(c.getColumnIndex("meal_price"));
            String mealImage = c.getString(c.getColumnIndex("meal_image"));
            String mealInfo = c.getString(c.getColumnIndex("meal_info"));
            MealModel mealModel = new MealModel(mealId, mealType, mealName, mealPrice, mealImage, mealInfo, 1);
            list.add(mealModel);
        }
        mAdapter = new MenuFragmentAdapter(getActivity(), list);
        gridView.setAdapter(mAdapter);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
//        presenter.detachView();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_recommend, container, false);
        ButterKnife.bind(this, view);
//        mAdapter = new MenuFragmentAdapter(getActivity(), pics);
//        gridView.setAdapter(mAdapter);
        return view;
    }

    @Override
    public void showView(MenuModel menuModel) {
        mAdapter = new MenuFragmentAdapter(getActivity(), menuModel.getMealModels());
        gridView.setAdapter(mAdapter);
    }

    @Override
    public Context getViewContext() {
        return getActivity();
    }

    @Override
    public void destroyView() {
        getActivity().finish();
    }
}
