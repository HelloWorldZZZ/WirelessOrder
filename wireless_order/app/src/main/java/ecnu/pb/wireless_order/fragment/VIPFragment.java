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

public class VIPFragment extends Fragment implements MenuPresenter.View {
    private int[] pics = {R.mipmap.img7, R.mipmap.img1, R.mipmap.img5,
            R.mipmap.img2, R.mipmap.img9, R.mipmap.img4};

    private MenuFragmentAdapter mAdapter;

    private MenuPresenter presenter;

    public VIPFragment() {
    }

    @Bind(R.id.gv_menu)
    GridView gridView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_recommend, container, false);
        ButterKnife.bind(this, view);
        init();
        return view;
    }

    private void init() {
        presenter = new MenuPresenter();
        presenter.attachView(this);
        presenter.getMenu(2);
    }

    @Override
    public void showView(MenuModel menuModel) {
        mAdapter = new MenuFragmentAdapter(getActivity(), menuModel.getMealModels());
        gridView.setAdapter(mAdapter);
    }

    @Override
    public Context getViewContext() {
        return null;
    }

    @Override
    public void destroyView() {
        presenter.detachView();
    }
}
