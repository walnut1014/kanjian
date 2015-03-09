package name.walnut.kanjian.app.support;

import java.util.Map;

import org.json.JSONObject;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.Response.Listener;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

public class JsonRequestBuilder {
	
	
	public static JsonObjectRequest newJsonRequest(int method, String uri, Map<String,Object> param,
				Listener<JSONObject> listener, Response.ErrorListener errorListener) {
		
		String url = getUrl(uri);
		if(method == Request.Method.GET){
			url = url + convertParamToUrlSearch(param);
			return new JsonRequestWraper(method, url, null, listener, errorListener);
		}else {
			return new JsonRequestWraper(method, url, param == null ? null : new JSONObject(param),
					listener, errorListener);
		}
		
	}
	
	private static String getUrl(String uri) {
		if(!uri.startsWith("/"))
			uri = "/" + uri;
		return CONTEXT_PATH + uri;
	}
	
	private static String convertParamToUrlSearch(Map<String, Object> param) {
		StringBuilder result = new StringBuilder("?");
		for(String s : param.keySet()){
			result.append(s);
			result.append("&");
			result.append(param.get(s).toString());
		}
		return result.toString();
	}
	

	public static class DefaultErrorListener implements Response.ErrorListener {

		@Override
		public void onErrorResponse(VolleyError error) {
			System.out.println(error);
		}
	}
	
	
	private final static String CONTEXT_PATH = "http://192.168.1.6:8080";
}
