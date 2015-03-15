package name.walnut.kanjian.app.utils;

import name.walnut.kanjian.app.support.KanJianApplication;

import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;
import android.view.Gravity;
import android.widget.Toast;

import com.android.volley.Response.Listener;

public abstract class DefalutJSONListener implements Listener<JSONObject> {


    @Override
    public void onResponse(JSONObject obj) {

        try {
            if(obj.getBoolean("success")){
                JSONObject data = null;
                if(obj.has("data")){
                    data = obj.getJSONObject("data");
                }
                onSuccess(data);
            }else{
                beforeFailed();
                onFailed(obj.getString("errorMsg"));
            }
        } catch (JSONException e) {
            Log.e("DefalutJSONListener","系统错误", e);
        }

    }

    public abstract void onSuccess(JSONObject data);

    public void onFailed(String errorMsg) {
        Toast toast = Toast.makeText(KanJianApplication.INTANCE,
                errorMsg, Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.show();
    }

    public void beforeFailed() {};



}
