package name.walnut.kanjian.app.service.impl;

import java.util.HashMap;
import java.util.Map;

import name.walnut.kanjian.app.service.RegisterService;
import name.walnut.kanjian.app.service.ServiceException;
import name.walnut.kanjian.app.support.JsonRequestBuilder;
import name.walnut.kanjian.app.support.RequestQueueContext;

import org.json.JSONObject;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response.Listener;
import com.android.volley.toolbox.JsonObjectRequest;

public class RegisterServiceImpl implements RegisterService {
	
	@Override
	public void sendVeriCode(String mobilephone) {
		if(!mobilephone.matches("^13\\d{9}$"))
			throw new ServiceException("手机号码不正确");
		
		request(mobilephone);
	}
	
	private void request(String mobilephone) {
		
		RequestQueue requestQueue = RequestQueueContext.INSTANCE.getRequestQueue();
		Map<String, Object> map = new HashMap<>(1);
		map.put("mobilephone", mobilephone);
		
		JsonObjectRequest jsonRequest = JsonRequestBuilder.newJsonRequest(Request.Method.POST, 
				"passport/register/veriCode", map,
				new Listener<JSONObject>() {

					@Override
					public void onResponse(JSONObject arg0) {
						// TODO Auto-generated method stub
						
					}
				});
		
		requestQueue.add(jsonRequest);
	}
}
