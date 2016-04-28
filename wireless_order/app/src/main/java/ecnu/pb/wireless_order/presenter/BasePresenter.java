package ecnu.pb.wireless_order.presenter;

import android.content.Context;

public abstract class BasePresenter<V extends BaseView> {
    private V mView;

    public void attachView(V view) {
        mView = view;
    }

    public void detachView() {
    }

    protected Context getContext() {
        return getView().getViewContext();
    }

    protected V getView() {
        if (mView == null) {
            throw new NullPointerException();
        }
        return mView;
    }
}

