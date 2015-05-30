package name.walnut.kanjian.app.ui.upload.action;

import com.android.volley.VolleyError;

import name.walnut.kanjian.app.R;
import name.walnut.kanjian.app.support.ActionBarFragment;
import name.walnut.kanjian.app.support.BaseResourceAction;
import name.walnut.kanjian.app.ui.util.ToastUtils;

/**
 * 上传图片
 */
public class UploadPhotoAction extends BaseResourceAction {
    @Override
    public void onSuccess(Response response) {
        // 上传成功
        dismissMessage();

        ToastUtils.toast("上传成功");
    }

    @Override
    public void onFailed(Response response) {
        ToastUtils.toast(response.getMessage());
        dismissMessage();
    }

    @Override
    public void onErrorResponse(VolleyError volleyError) {
        ToastUtils.toast(R.string.toast_error_network);
        dismissMessage();
    }

    private void dismissMessage() {
        ActionBarFragment fragment = (ActionBarFragment) getFragment();
        if (fragment != null) {
            fragment.dismissMessage();
        }
    }
}
