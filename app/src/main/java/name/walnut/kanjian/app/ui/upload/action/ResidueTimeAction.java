package name.walnut.kanjian.app.ui.upload.action;


import android.net.Uri;

import com.android.volley.VolleyError;

import java.util.List;

import name.walnut.kanjian.app.R;
import name.walnut.kanjian.app.support.BaseResourceAction;
import name.walnut.kanjian.app.support.KJAlertDialogFragment;
import name.walnut.kanjian.app.ui.upload.UploadFragment;
import name.walnut.kanjian.app.ui.util.ToastUtils;
import name.walnut.kanjian.app.utils.Logger;
import name.walnut.kanjian.app.views.KJAlertDialog;

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

        int hour = (int) ((residueTime / 1000) / 60 / 60); // 剩余小时
        int minute = (int) ((residueTime / 1000) / 60 % 60); // 剩余分钟
        boolean reselect = (hour == 0 && minute == 0); // 能否重新挑选
        if (fragment != null) {
            fragment.setResidueTime(residueTime);
            fragment.stopAnimation();
            if (reselect) {
                showReselectDialog(fragment);
            } else {
                showWaitDialog(fragment, hour, minute);
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

    // 已到24小时，重新挑选图片
    public void showReselectDialog(final UploadFragment fragment) {
        new KJAlertDialogFragment()
                .setContent(fragment.getString(R.string.dialog_upload_title_reselect))
                .setPositiveText(fragment.getString(R.string.dialog_upload_reselect_positive))
                .setNegativeText(fragment.getString(R.string.dialog_upload_button_negative))
                .setPositiveClickListener(new KJAlertDialog.OnKJClickListener() {
                    @Override
                    public void onClick(KJAlertDialog dialog) {
                        startReselectPhoto(fragment);
                    }
                })
                .show(fragment.getFragmentManager());
    }


    // 时间未够，等待
    public void showWaitDialog(UploadFragment fragment, int hour, int minute) {
        new KJAlertDialogFragment()
                .setContent(fragment.getString(R.string.dialog_upload_title_wait, hour, minute))
                .setPositiveText(fragment.getString(R.string.dialog_upload_wait_positive))
                .show(fragment.getFragmentManager());
    }

    // 重新挑选图片
    public void startReselectPhoto(UploadFragment fragment) {

        // TODO 重新挑选图片
        List<Uri> uris = fragment.getImagePath();
        for (Uri uri : uris) {
            Logger.e("选中的图片路径：" + uri);
        }
        fragment.onImageReselect(uris);

    }
}
