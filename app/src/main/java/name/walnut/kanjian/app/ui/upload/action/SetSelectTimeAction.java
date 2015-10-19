package name.walnut.kanjian.app.ui.upload.action;


import com.android.volley.VolleyError;

import name.walnut.kanjian.app.R;
import name.walnut.kanjian.app.support.ActionBarFragment;
import name.walnut.kanjian.app.support.BaseResourceAction;
import name.walnut.kanjian.app.support.AppContext;
import name.walnut.kanjian.app.ui.upload.UploadFragment;
import name.walnut.kanjian.app.ui.upload.UploadImageCache;
import name.walnut.kanjian.app.ui.util.ToastUtils;

/**
 *
 * 设置选择图片的时间
 *
 */
public class SetSelectTimeAction extends BaseResourceAction {
    @Override
    public void onSuccess(Response response) {

        dismissMessage();

        UploadImageCache.INSTANCE.cacheImage(AppContext.INSTANCE);

        UploadFragment fragment = (UploadFragment) getFragment();
        if (fragment != null) {
            fragment.setImage(UploadImageCache.INSTANCE.getImageUris());
        }
    }

    @Override
    public void onFailed(Response response) {
        ToastUtils.toast(response.getMessage());

        dismissMessage();
        UploadImageCache.INSTANCE.clearTmpCache();
    }

    @Override
    public void onErrorResponse(VolleyError volleyError) {
        ToastUtils.toast(R.string.toast_error_network);

        dismissMessage();
        UploadImageCache.INSTANCE.clearTmpCache();
    }

    private void dismissMessage() {
        ActionBarFragment fragment = (ActionBarFragment) getFragment();
        if (fragment != null) {
            fragment.dismissMessage();
        }
    }
}
