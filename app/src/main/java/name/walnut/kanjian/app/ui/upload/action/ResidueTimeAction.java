package name.walnut.kanjian.app.ui.upload.action;


import com.android.volley.VolleyError;

import name.walnut.kanjian.app.R;
import name.walnut.kanjian.app.support.BaseResourceAction;
import name.walnut.kanjian.app.ui.upload.UploadFragment;
import name.walnut.kanjian.app.ui.util.ToastUtils;

/**
 *
 * 获得用户还剩多长时间可以上传照片 action
 *
 */
public class ResidueTimeAction extends BaseResourceAction {
    @Override
    public void onSuccess(Response response) {
        UploadFragment fragment = (UploadFragment) this.getFragment();
        long residueTime = Long.valueOf(response.getData());

        // TODO 解析json，获取hour和minute
        int hour = (int) ((residueTime / 1000) / 60 / 60); // 剩余小时
        int minute = (int) ((residueTime / 1000) / 60 % 60); // 剩余分钟
        boolean reselect = (hour == 0 && minute == 0); // 能否重新挑选
        if (fragment != null) {
            fragment.setResidueTime(residueTime);
            fragment.stopAnimation();
            if (reselect) {
                fragment.showReselectDialog();
            } else {
                fragment.showWaitDialog(hour, minute);
            }
        }
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
