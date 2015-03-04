package name.walnut.kanjian.app.support;

import android.content.Context;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

public enum RequestQueueContext {
	INSTANCE;
	
	public void initRequestQueue(Context context) {
		this.requestQueue = Volley.newRequestQueue(context);
	}
	
	public RequestQueue getRequestQueue() {
		return requestQueue;
	}

	private RequestQueue requestQueue;
}
