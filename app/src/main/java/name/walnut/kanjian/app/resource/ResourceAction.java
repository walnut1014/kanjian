package name.walnut.kanjian.app.resource;

import com.android.volley.VolleyError;

import org.json.JSONObject;

/**
 * Resource处理类接口
 * @author walnut
 */
public interface ResourceAction {

    void onResponse(JSONObject object);

    void onErrorResponse(VolleyError volleyError);
}
