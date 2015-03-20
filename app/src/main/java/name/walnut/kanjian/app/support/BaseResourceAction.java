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
            if(obj.getBoolean("success")){
                String data = null;
                if(obj.has("data")){
                    data = obj.getString("data");
                }
                onSuccess(data);
            }else{
                onFailed(obj.optString("message"));
            }
        } catch (JSONException e) {
            Log.e("DefaultJSONListener", "系统错误", e);
        }

    }

    public abstract void onSuccess(String data);

    public abstract void onFailed(String errorMsg);

    public abstract void onErrorResponse(VolleyError volleyError);
}
