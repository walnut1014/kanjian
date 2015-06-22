package name.walnut.kanjian.app.ui.main.action;

import com.android.volley.VolleyError;

import name.walnut.kanjian.app.support.BaseResourceAction;
import name.walnut.kanjian.app.utils.Logger;

/**
 * 首页看照片action
 */
public class FetchPhotosFlowAction extends BaseResourceAction {
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
