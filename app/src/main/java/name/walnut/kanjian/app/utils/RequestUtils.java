package name.walnut.kanjian.app.utils;

import com.android.volley.Request;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.apache.http.Header;
import org.json.JSONObject;

import java.util.Map;

import name.walnut.kanjian.app.support.JsonRequestBuilder;
import name.walnut.kanjian.app.support.RequestQueueContext;

public class RequestUtils {
	public static void postJSON(String uri, Map<String, Object> param, DefalutJSONListener listener, JsonRequestBuilder.DefaultErrorListener errorListener){
		
		JsonObjectRequest jsonRequest = JsonRequestBuilder.newJsonRequest(Request.Method.POST, 
				uri, param, listener, errorListener);
		
		RequestQueueContext.INSTANCE.getRequestQueue().add(jsonRequest);
	}

    /**
     * @param uri
     * @param params
     * @param listener
     */
    public static void upload(String uri, RequestParams params, final DefalutJSONListener listener, final JsonRequestBuilder.DefaultErrorListener errorListener) {

        AsyncHttpClient client = new AsyncHttpClient();
        client.post(uri, params, new JsonHttpResponseHandler(){

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                listener.onResponse(response);
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                super.onFailure(statusCode, headers, responseString, throwable);
                errorListener.onErrorResponse(new VolleyError(responseString, throwable));
            }
        });
    }
}
