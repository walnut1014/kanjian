package name.walnut.kanjian.app.ui;

import com.android.volley.VolleyError;

import name.walnut.kanjian.app.R;
import name.walnut.kanjian.app.support.BaseResourceAction;
import name.walnut.kanjian.app.support.AppContext;
import name.walnut.kanjian.app.ui.util.ToastUtils;
import name.walnut.kanjian.app.utils.Logger;

/**
 * 登录是否有效
 */
public class AuthAvailableAction extends BaseResourceAction{
    @Override
    public void onSuccess(Response response) {
        Logger.e(response.getData());
        boolean available = Boolean.parseBoolean(response.getData());
        if (!available) {
            AppContext.restart();
        }
    }

    @Override
    public void onFailed(Response response) {
        Logger.e(response.getMessage());
    }

    @Override
    public void onErrorResponse(VolleyError volleyError) {
        Logger.e(volleyError + "");
        ToastUtils.toast(R.string.toast_error_network);
    }
}
