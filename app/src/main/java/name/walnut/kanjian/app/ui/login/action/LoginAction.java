package name.walnut.kanjian.app.ui.login.action;

import com.android.volley.VolleyError;

import org.json.JSONObject;

import name.walnut.kanjian.app.resource.impl.DefaultResourceAction;

/**
 * Created by user on 2015/3/15.
 */
public class LoginAction extends DefaultResourceAction {

    @Override
    public void onResponse(JSONObject object) {

        System.out.println(object);
        System.out.println(this.getActivity()+ "sdfsd");

        System.out.println("sdfsdf");
    }

    @Override
    public void onErrorResponse(VolleyError volleyError) {
        System.out.println(volleyError);
        this.getFragment();
    }

}
