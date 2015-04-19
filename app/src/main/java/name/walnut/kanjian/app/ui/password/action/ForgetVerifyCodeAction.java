package name.walnut.kanjian.app.ui.password.action;

import android.widget.Toast;

import com.android.volley.VolleyError;

import name.walnut.kanjian.app.R;
import name.walnut.kanjian.app.support.ActionBarFragment;
import name.walnut.kanjian.app.support.BaseResourceAction;
import name.walnut.kanjian.app.ui.password.FillPasswordFragment;
import name.walnut.kanjian.app.ui.password.VerifyCodeFragment;
import name.walnut.kanjian.app.ui.util.ToastUtils;

/**
 * 忘记密码 验证验证码
 */
public class ForgetVerifyCodeAction extends BaseResourceAction {
    @Override
    public void onSuccess(Response response) {
        dismissMessage();
        VerifyCodeFragment fragment = (VerifyCodeFragment) getFragment();
        fragment.switchFragment(new FillPasswordFragment(response.getData()));
    }

    @Override
    public void onFailed(Response response) {
        dismissMessage();
        ToastUtils.toast(response.getMessage());
    }

    @Override
    public void onErrorResponse(VolleyError volleyError) {
        dismissMessage();
        ToastUtils.toast(R.string.toast_error_network);
    }


    private void dismissMessage() {
        ActionBarFragment fragment = (ActionBarFragment) getFragment();
        fragment.dismissMessage();
    }
}
