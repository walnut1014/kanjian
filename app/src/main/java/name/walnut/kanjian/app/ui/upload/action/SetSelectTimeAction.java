package name.walnut.kanjian.app.ui.upload.action;


import com.android.volley.VolleyError;

import name.walnut.kanjian.app.R;
import name.walnut.kanjian.app.support.BaseResourceAction;
import name.walnut.kanjian.app.ui.upload.UploadFragment;
import name.walnut.kanjian.app.ui.util.ToastUtils;

/**
 *
 * 用户选择照片处理类 action
 *
 */
public class SetSelectTimeAction extends BaseResourceAction {
    @Override
    public void onSuccess(Response response) {
    }

    @Override
    public void onFailed(Response response) {
        ToastUtils.toast(response.getMessage());
        UploadFragment fragment = (UploadFragment) getFragment();
        if (fragment != null) {
            fragment.stopAnimation();
        }
    }

    @Override
    public void onErrorResponse(VolleyError volleyError) {
        ToastUtils.toast(R.string.toast_error_network);

        UploadFragment fragment = (UploadFragment) getFragment();
        if (fragment != null) {
            fragment.stopAnimation();
        }
    }
}
