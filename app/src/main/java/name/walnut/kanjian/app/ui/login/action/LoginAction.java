package name.walnut.kanjian.app.ui.login.action;

import com.android.volley.VolleyError;

import org.json.JSONObject;

import name.walnut.kanjian.app.resource.ResourceAction;

/**
 * Created by user on 2015/3/15.
 */
public class LoginAction implements ResourceAction {
    @Override
    public void onResponse(JSONObject object) {
        System.out.println(object);
    }

    @Override
    public void onErrorResponse(VolleyError volleyError) {
        System.out.println(volleyError);
    }
}
