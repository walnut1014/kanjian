package name.walnut.kanjian.app.support.rest;

import android.content.Context;
import android.content.SharedPreferences;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

public enum RequestQueueContext {
	INSTANCE;
	
	public void init(Context context) {
        this.context = context;
		this.requestQueue = Volley.newRequestQueue(context);

        // 初始化sessionId
        SharedPreferences preferences = context.getSharedPreferences(FILE_SESSION, Context.MODE_PRIVATE);
        sessionId = preferences.getString(KEY_SESSION_ID, sessionId);
	}
	
	public RequestQueue getRequestQueue() {
		return requestQueue;
	}

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
        // 持久化储存
        SharedPreferences preferences = context.getSharedPreferences(FILE_SESSION, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(KEY_SESSION_ID, sessionId).apply();
    }

	private RequestQueue requestQueue;

    private String sessionId;

    private Context context;

    public static final String KEY_SESSION_ID = "sessionId";

    public static final String FILE_SESSION = "session";
}
