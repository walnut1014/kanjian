package name.walnut.kanjian.app.ui;

import name.walnut.kanjian.app.R;
import name.walnut.kanjian.app.ui.my.relation.MyFragment;

/**
 * 餐厅和供应商首页tab
 */
public enum TabResource {

    MESSAGE(MyFragment.class, R.drawable.ic_tab_list, R.string.tab_title_message),
    CAMERA(null, R.drawable.ic_tab_camera, R.string.tab_title_empty),
    MY(MyFragment.class, R.drawable.ic_tab_my, R.string.tab_title_my),
    ;

    TabResource(Class<?> fragment, int resId, int titleId) {
        this.fragment = fragment;
        this.resId = resId;
        this.titleId = titleId;
    }

    public Class getFragment() {
        return fragment;
    }

    public int getResId() {
        return resId;
    }

    public int getTitleId() {
        return titleId;
    }

    private Class fragment;
    private int resId;
    private int titleId;
}