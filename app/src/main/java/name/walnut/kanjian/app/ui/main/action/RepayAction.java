package name.walnut.kanjian.app.ui.main.action;

import com.android.volley.VolleyError;

import name.walnut.kanjian.app.support.BaseResourceAction;
import name.walnut.kanjian.app.utils.Logger;

/**
 * 消息回复action
 */
public class RepayAction extends BaseResourceAction{
    @Override
    public void onSuccess(Response response) {
        Logger.e(response.getData());
    }

    @Override
    public void onFailed(Response response) {
        Logger.e(response.getMessage());
    }

    @Override
    public void onErrorResponse(VolleyError volleyError) {
        Logger.e(volleyError.getMessage());
    }
}
