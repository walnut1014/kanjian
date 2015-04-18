package name.walnut.kanjian.app.ui;

import name.walnut.kanjian.app.R;
import name.walnut.kanjian.app.ui.my.relation.MyFragment;

/**
 * 餐厅和供应商首页tab
 */
public enum TabResource {

    MESSAGE(MyFragment.class, R.drawable.ic_tab_list),
    CAMERA(MyFragment.class, R.drawable.ic_tab_camera),
    MY(MyFragment.class, R.drawable.ic_tab_my),
    ;

    TabResource(Class<?> fragment, int resId) {
        this.fragment = fragment;
        this.resId = resId;
    }

    public Class getFragment() {
        return fragment;
    }

    public int getResId() {
        return resId;
    }

    private Class fragment;
    private int resId;
}