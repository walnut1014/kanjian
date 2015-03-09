package name.walnut.kanjian.app.utils;

import com.android.volley.Request;
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
	public static void postJSON(String uri, Map<String, Object> param, DefalutJSONListener listener ){
		
		JsonObjectRequest jsonRequest = JsonRequestBuilder.newJsonRequest(Request.Method.POST, 
				uri, param, listener);
		
		RequestQueueContext.INSTANCE.getRequestQueue().add(jsonRequest);
	}

    /**
     * @param uri
     * @param params
     * @param listener
     */
    public static void upload(String uri, RequestParams params, final DefalutJSONListener listener ) {

        AsyncHttpClient client = new AsyncHttpClient();
        client.post(uri, params, new JsonHttpResponseHandler(){

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                listener.onResponse(response);
            }

        });
    }
}
