package name.walnut.kanjian.app.resource.impl;

import android.app.Activity;
import android.app.Fragment;

import name.walnut.kanjian.app.resource.ResourceAction;

/**
 * Created by user on 2015/3/15.
 */
public abstract class DefaultResourceAction implements ResourceAction {

    protected Fragment getFragment() {
        return fragment;
    }

    public void setFragment(Fragment fragment) {
        this.fragment = fragment;
    }

    protected Activity getActivity() {
        return fragment.getActivity();
    }

    @Override
    public void setResource(Resource resource) {
        this.resource = resource;
    }

    @Override
    public Resource getResource() {
        return resource;
    }

    private Resource resource;
    private Fragment fragment;
}
