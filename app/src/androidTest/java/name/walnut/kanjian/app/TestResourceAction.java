package name.walnut.kanjian.app;

import com.android.volley.VolleyError;

import org.json.JSONObject;

import name.walnut.kanjian.app.resource.ResourceAction;

/**
 * Created by user on 2015/3/16.
 */
public abstract class TestResourceAction implements ResourceAction {
    @Override
    public void onResponse(JSONObject object) {

    }

    @Override
    public void onErrorResponse(VolleyError volleyError) {

    }
}
