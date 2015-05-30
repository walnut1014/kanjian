package name.walnut.kanjian.app.ui.upload.action;

import android.content.Intent;

import com.android.volley.VolleyError;

import name.walnut.kanjian.app.R;
import name.walnut.kanjian.app.support.ActionBarFragment;
import name.walnut.kanjian.app.support.BaseResourceAction;
import name.walnut.kanjian.app.ui.Constants;
import name.walnut.kanjian.app.ui.register.FillNicknameFragment;
import name.walnut.kanjian.app.ui.register.RegisterActivity;
import name.walnut.kanjian.app.ui.upload.UploadFragment;
import name.walnut.kanjian.app.ui.util.ToastUtils;

/**
 * 注册 输入个人信息完成注册
 */
public class ResidueTimeAction extends BaseResourceAction {
    @Override
    public void onSuccess(Response response) {
        UploadFragment fragment =(UploadFragment) this.getFragment();
        fragment.setResidueTime(Long.valueOf(response.getData()));
    }

    @Override
    public void onFailed(Response response) {
        ToastUtils.toast(response.getMessage());
    }

    @Override
    public void onErrorResponse(VolleyError volleyError) {
        ToastUtils.toast(R.string.toast_error_network);
    }

}
