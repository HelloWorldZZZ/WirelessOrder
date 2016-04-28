package ecnu.pb.wireless_order.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;

import butterknife.Bind;
import butterknife.ButterKnife;
import ecnu.pb.wireless_order.R;
import ecnu.pb.wireless_order.net.RestAsyncTask;
import retrofit.RetrofitError;

public class SplashActivity extends AppCompatActivity {

    private static final long DEFAULT_DELAY = 1500;

    @Bind(R.id.img_logo)
    ImageView mLogo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        ButterKnife.bind(this);
        init();
    }

    private void init() {
        startMainDelayed(DEFAULT_DELAY);
    }

    private void startMainDelayed(long delay) {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                startMain();
            }
        }, delay);
    }

    private void startMain() {
        startActivity(new Intent(this, MainActivity.class));
        finish();
    }

    private class InitClientTask extends RestAsyncTask {
        private long duration;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            duration = System.currentTimeMillis();
        }

        @Override
        protected Integer doInBackground(Void... params) {
            try {
//                AccountManager.initAccountFromRemote(SplashActivity.this);
                return 200;
            } catch (RetrofitError e) {
                return e.getResponse() != null ? e.getResponse().getStatus() : 500;
            }
        }

        @Override
        protected void onCancelled() {
            super.onCancelled();
            duration = System.currentTimeMillis() - duration;
        }

        @Override
        protected void onPostExecute(Integer status) {
            super.onPostExecute(status);
            switch (status) {
                case 200:
                    startMainDelayed(Math.max(DEFAULT_DELAY - duration, 0));
                    break;
                default:
                    startMainDelayed(Math.max(DEFAULT_DELAY - duration, 0));
                    break;
            }
        }
    }
}
