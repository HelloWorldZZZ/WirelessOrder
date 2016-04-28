package ecnu.pb.wireless_order.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;
import java.util.List;

import ecnu.pb.wireless_order.fragment.OtherFragment;
import ecnu.pb.wireless_order.fragment.RecommendFragment;
import ecnu.pb.wireless_order.fragment.VIPFragment;
import ecnu.pb.wireless_order.model.MealModel;

public class MenuTabAdapter extends FragmentPagerAdapter {
    private RecommendFragment recommendFragment;
    private OtherFragment otherFragment;
    private VIPFragment vipFragment;
    private List<MealModel> mealModels;

    public MenuTabAdapter(FragmentManager fragmentManager) {
        super(fragmentManager);
        mealModels = new ArrayList<>();
    }

    public void setMenu(List<MealModel> menu) {
        this.mealModels = menu;
    }

    @Override
    public Fragment getItem(int position) {
        if (position == 0) {
            if (recommendFragment == null) {
                recommendFragment = new RecommendFragment();
            }
            return recommendFragment;
        } else if (position == 1) {
            if (otherFragment == null) {
                otherFragment = new OtherFragment();
            }
            return otherFragment;
        } else {
            if (vipFragment == null) {
                vipFragment = new VIPFragment();
            }
            return vipFragment;
        }
    }

    @Override
    public int getCount() {
        return 3;
    }
}
