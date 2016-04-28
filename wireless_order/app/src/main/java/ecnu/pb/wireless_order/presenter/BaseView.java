package ecnu.pb.wireless_order.presenter;

import android.content.Context;

public interface BaseView {
    Context getViewContext();

    void destroyView();
}
