package name.walnut.kanjian.app.ui.my.relation.newfriends.action;

import android.util.Log;

import com.android.volley.VolleyError;

import name.walnut.kanjian.app.R;
import name.walnut.kanjian.app.resource.ResourceAction;
import name.walnut.kanjian.app.support.BaseResourceAction;
import name.walnut.kanjian.app.ui.util.ToastUtils;
import name.walnut.kanjian.app.utils.Logger;

/**
 * 查询好友action
 */
public class QueryUserAction extends BaseResourceAction {
    @Override
    public void onSuccess(Response response) {

    }

    @Override
    public void onFailed(Response response) {
        ToastUtils.toast(response.getMessage());
    }

    @Override
    public void onErrorResponse(VolleyError volleyError) {
        Logger.e(volleyError.getMessage());
        ToastUtils.toast(R.string.toast_error_network);
    }
}
