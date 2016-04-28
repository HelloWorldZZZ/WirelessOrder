package ecnu.pb.wireless_order.net;

import android.content.Context;

import com.android.volley.AuthFailureError;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class VolleyRequest {
	private static RequestQueue mRequestQueue;

	private VolleyRequest() {
	}
	public static void buildRequestQueue(Context context) {
		mRequestQueue = Volley.newRequestQueue(context);
	}

	public static VolleyRequest newInstance() {
		if (mRequestQueue == null) {
			throw new NullPointerException(
					"Call buildRequestQueue method first.");
		}
		return new VolleyRequest();
	}

	public <T> GsonRequest<T> newGsonRequest(int method, String url,
			Class<T> clazz, Response.Listener<T> listener,
			Response.ErrorListener errorListener) {
		GsonRequest<T> request = new GsonRequest<T>(method, url, clazz,
				listener, errorListener);
		mRequestQueue.add(request);
		return request;
	}

	public JsonObjectRequest newJsonRequest(int method, String url,
			JSONObject object, Response.Listener<JSONObject> listener,
			Response.ErrorListener errorListener) {
		JsonObjectRequest request;
		request = new JsonObjectRequest(method, url, object, listener,
				errorListener)
		{
			@Override
			public Map<String, String> getHeaders() throws AuthFailureError {
				HashMap<String, String> headers = new HashMap<String, String>();
				headers.put("Accept", "application/json");
				headers.put("Content-Type", "application/json; charset=UTF-8");
				return super.getHeaders();
			}
		};
		mRequestQueue.add(request);
		return request;

	}
}
