package name.walnut.kanjian.app.resource.impl;

import android.app.Activity;
import android.app.Fragment;

import com.android.volley.VolleyError;

import org.json.JSONObject;

import name.walnut.kanjian.app.resource.ResourceAction;

/**
 * Created by user on 2015/3/15.
 */
public abstract class DefaultResourceAction implements ResourceAction {

    protected Fragment getFragment() {
        return fragment;
    }

    protected void setFragment(Fragment fragment) {
        this.fragment = fragment;
    }

    protected Activity getActivity() {
        return fragment.getActivity();
    }

    private Fragment fragment;
}
