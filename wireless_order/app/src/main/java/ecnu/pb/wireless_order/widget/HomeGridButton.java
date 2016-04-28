package ecnu.pb.wireless_order.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import butterknife.Bind;
import butterknife.ButterKnife;
import ecnu.pb.wireless_order.R;

public class HomeGridButton extends FrameLayout {
    public HomeGridButton(Context context) {
        super(context);
        init(null);
    }

    public HomeGridButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs);
    }

    public HomeGridButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(attrs);
    }

    @Bind(R.id.imageView)
    ImageView mImageView;
    @Bind(R.id.txt_title)
    TextView mTitle;

    private void init(AttributeSet attrs) {
        LayoutInflater.from(getContext()).inflate(R.layout.widget_home_grid_button, this);
        ButterKnife.bind(this);

        if (attrs != null) {
            int imageRes = 0;
            String title = null;

            TypedArray array = getContext().obtainStyledAttributes(attrs, R.styleable.HomeGridButton);
            for (int i = 0; i < array.getIndexCount(); i++) {
                int index = array.getIndex(i);
                switch (index) {
                    case R.styleable.HomeGridButton_hgb_image:
                        imageRes = array.getResourceId(index, 0);
                        break;
                    case R.styleable.HomeGridButton_hgb_title:
                        title = array.getString(index);
                        break;
                }
            }
            array.recycle();

            if (imageRes != 0) {
                mImageView.setImageResource(imageRes);
            }
            mTitle.setText(title);
        }
    }
}
