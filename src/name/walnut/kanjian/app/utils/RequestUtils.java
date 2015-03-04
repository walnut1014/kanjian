package name.walnut.kanjian.app.utils;

import java.util.Map;

import name.walnut.kanjian.app.support.JsonRequestBuilder;
import name.walnut.kanjian.app.support.RequestQueueContext;

import com.android.volley.Request;
import com.android.volley.toolbox.JsonObjectRequest;

public class RequestUtils {
	public static void postJSON(String uri, Map<String, Object> param, DefalutJSONListener listener ){
		
		JsonObjectRequest jsonRequest = JsonRequestBuilder.newJsonRequest(Request.Method.POST, 
				uri, param, listener);
		
		RequestQueueContext.INSTANCE.getRequestQueue().add(jsonRequest);
	}
}
