package ecnu.pb.wireless_order.net;

import android.os.AsyncTask;

import retrofit.RetrofitError;

public abstract class RestAsyncTask extends AsyncTask<Void, String, Integer> {
    public RestAsyncTask() {
        super();
    }

    @Override
    protected abstract Integer doInBackground(Void... params);

    @Override
    protected void onPostExecute(Integer status) {
        onCancelled();
    }

    protected Integer handleRetrofitError(RetrofitError e) {
        return e.getResponse() != null ? e.getResponse().getStatus() : 500;
    }
}
