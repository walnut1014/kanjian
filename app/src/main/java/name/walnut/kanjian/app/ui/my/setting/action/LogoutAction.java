package name.walnut.kanjian.app.ui.my.setting.action;

import com.android.volley.VolleyError;

import name.walnut.kanjian.app.support.BaseResourceAction;

/**
 * 退出登录action
 */
public class LogoutAction extends BaseResourceAction{
    @Override
    public void onSuccess(Response response) {
        onResponse();
    }

    @Override
    public void onFailed(Response response) {
        onResponse();
    }

    @Override
    public void onErrorResponse(VolleyError volleyError) {
        onResponse();
    }

    private void onResponse() {
        //  退出当前账号

    }
}
