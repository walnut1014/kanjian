package name.walnut.kanjian.app.resource;

import com.android.volley.VolleyError;

import org.json.JSONObject;

import name.walnut.kanjian.app.resource.impl.Resource;

/**
 * Resource处理类接口
 * @author walnut
 */
@Deprecated
public interface ResourceAction {

    void onResponse(JSONObject object);

    void onErrorResponse(VolleyError volleyError);

    void setResource(Resource resource);

    Resource getResource();
}
