package name.walnut.kanjian.app.ui.my.setting.password.action;

import com.android.volley.VolleyError;

import name.walnut.kanjian.app.R;
import name.walnut.kanjian.app.support.ActionBarFragment;
import name.walnut.kanjian.app.support.BaseResourceAction;
import name.walnut.kanjian.app.ui.my.setting.password.FillPasswordFragment;
import name.walnut.kanjian.app.ui.my.setting.password.VerifyCodeFragment;
import name.walnut.kanjian.app.ui.util.ToastUtils;

/**
 * 输入验证码action
 */
public class VerifyCodeAction extends BaseResourceAction {
    @Override
    public void onSuccess(Response response) {
        dismissMessage();

        VerifyCodeFragment verifyCodeFragment = (VerifyCodeFragment) getFragment();
        verifyCodeFragment.switchFragment(
                FillPasswordFragment.newInstance(response.getData()));
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
        fragment.dismissMessage();
    }
}
