package name.walnut.kanjian.app.ui.main.action;

import android.util.Log;

import com.android.volley.VolleyError;

import name.walnut.kanjian.app.R;
import name.walnut.kanjian.app.support.BaseResourceAction;
import name.walnut.kanjian.app.ui.main.PhotosFlowFragment;
import name.walnut.kanjian.app.ui.util.ToastUtils;
import name.walnut.kanjian.app.utils.Logger;

/**
 * 删除照片
 */
public class DeleteMessageAction extends BaseResourceAction{
    @Override
    public void onSuccess(Response response) {
        Logger.e(response.getData());
        ToastUtils.toast(R.string.message_delete_success);

        PhotosFlowFragment fragment = (PhotosFlowFragment) getFragment();
        if (fragment != null && !fragment.isDetached()) {
            fragment.onDeleteSuccess();
        }
    }

    @Override
    public void onFailed(Response response) {
        Logger.e(response+"");
        ToastUtils.toast(response.getMessage());
        onResult();
    }

    @Override
    public void onErrorResponse(VolleyError volleyError) {
        Logger.e(volleyError+"");
        ToastUtils.toast(R.string.toast_error_network);
        onResult();
    }

    private void onResult() {
        PhotosFlowFragment fragment = (PhotosFlowFragment) getFragment();
        if (fragment != null && !fragment.isDetached()) {
            fragment.onDeleteFailed();
        }
    }
}
