package name.walnut.kanjian.app.ui.my.relation;

import com.android.volley.VolleyError;

import name.walnut.kanjian.app.support.BaseResourceAction;
import name.walnut.kanjian.app.utils.Logger;

/**
 * 获取当前用户信息
 */
public class CurrentUserAction extends BaseResourceAction {
    @Override
    public void onSuccess(Response response) {
        Logger.e(response.getData());
    }

    @Override
    public void onFailed(Response response) {
    }

    @Override
    public void onErrorResponse(VolleyError volleyError) {
    }
}
