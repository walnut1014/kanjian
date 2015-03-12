package name.walnut.kanjian.app.support;

import org.json.JSONObject;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.Response;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.toolbox.JsonObjectRequest;

import java.util.HashMap;
import java.util.Map;

public class JsonRequestWraper extends JsonObjectRequest {

	

	public JsonRequestWraper(int method, String url, JSONObject jsonRequest,
			Listener<JSONObject> listener, ErrorListener errorListener) {
		super(method, url, jsonRequest, listener, errorListener);
	}

    @Override
    public Map<String, String> getHeaders() throws AuthFailureError {

        Map<String, String> map = new HashMap<>();
        map.put("Cookie", "JSESSIONID="+ RequestQueueContext.INSTANCE.getSessionId() + ";");
        return map;
    }

    @Override
	protected Response<JSONObject> parseNetworkResponse(NetworkResponse response) {
		
		if(response.headers.containsKey("Set-Cookie")){
			String cookie = response.headers.get("Set-Cookie");
            System.out.println(cookie);
            String sessionId = cookie.split(";")[0].split("=")[1];
            RequestQueueContext.INSTANCE.setSessionId(sessionId);
        }
		return super.parseNetworkResponse(response);
	}

    private String cookie;

}
