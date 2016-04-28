package ecnu.pb.wireless_order.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;

import butterknife.Bind;
import butterknife.ButterKnife;
import ecnu.pb.wireless_order.R;
import ecnu.pb.wireless_order.adapter.MenuFragmentAdapter;
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
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        presenter = new MenuPresenter();
        presenter.attachView(this);
        presenter.getMenu(0);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        presenter.detachView();
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
        return getContext();
    }

    @Override
    public void destroyView() {
        getActivity().finish();
    }
}
