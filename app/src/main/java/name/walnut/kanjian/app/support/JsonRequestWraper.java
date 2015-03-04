package name.walnut.kanjian.app.support;

import org.json.JSONObject;

import com.android.volley.NetworkResponse;
import com.android.volley.Response;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.toolbox.JsonObjectRequest;

public class JsonRequestWraper extends JsonObjectRequest {

	

	public JsonRequestWraper(int method, String url, JSONObject jsonRequest,
			Listener<JSONObject> listener, ErrorListener errorListener) {
		super(method, url, jsonRequest, listener, errorListener);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected Response<JSONObject> parseNetworkResponse(NetworkResponse response) {
		
		if(response.headers.containsKey("Set-Cookie")){
			
		}
		return super.parseNetworkResponse(response);
	}

}
