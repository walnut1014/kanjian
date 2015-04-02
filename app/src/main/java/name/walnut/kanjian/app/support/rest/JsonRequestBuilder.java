package name.walnut.kanjian.app.support.rest;

import java.util.Map;

import org.json.JSONObject;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.Response.Listener;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

public class JsonRequestBuilder {
	
	
	public static JsonObjectRequest newJsonRequest(int method, String url, Map<String,?> param,
				Listener<JSONObject> listener, Response.ErrorListener errorListener) {


		if(method == Request.Method.GET){
			url = url + convertParamToUrlSearch(param);
			return new JsonRequestWraper(method, url, null, listener, errorListener);
		}else {
			return new JsonRequestWraper(method, url, param == null ? null : new JSONObject(param),
					listener, errorListener);
		}
		
	}

	private static String convertParamToUrlSearch(Map<String, ?> param) {
		StringBuilder result = new StringBuilder("?");
		for(String s : param.keySet()){
			result.append(s);
			result.append("=");
			result.append(param.get(s).toString()+"&");
		}
		return result.toString();
	}
	

	public static class DefaultErrorListener implements Response.ErrorListener {

		@Override
		public void onErrorResponse(VolleyError error) {
			System.out.println(error);
		}
	}

}
