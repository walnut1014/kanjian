package name.walnut.kanjian.app.support;

import android.util.Log;

import com.android.volley.VolleyError;

import org.json.JSONException;
import org.json.JSONObject;

import name.walnut.kanjian.app.resource.impl.DefaultResourceAction;
import name.walnut.kanjian.app.utils.Logger;

public abstract class BaseResourceAction extends DefaultResourceAction {

    @Override
    public void onResponse(JSONObject obj) {
        Logger.d(obj.toString());
        try {
            Response response = new Response();
            response.success = obj.getBoolean("success");
            if(obj.has("data")){
                response.data = obj.getString("data");
            }
            if(obj.has("message")){
                response.message = obj.getString("message");
            }
            if (response.success) {
                onSuccess(response);
            } else {
                onFailed(response);
            }
        } catch (JSONException e) {
            Log.e("DefaultJSONListener", "系统错误", e);
        }

    }

    public abstract void onSuccess(Response response);

    public abstract void onFailed(Response response);

    public abstract void onErrorResponse(VolleyError volleyError);

    public static class Response {
        private String data;
        private boolean success;
        private String message;

        public String getData() {
            return data;
        }

        public String getMessage() {
            return message;
        }

        public boolean isSuccess() {
            return success;
        }
    }
}
