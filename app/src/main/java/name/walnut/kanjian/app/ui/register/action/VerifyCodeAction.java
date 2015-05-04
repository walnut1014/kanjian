package name.walnut.kanjian.app.ui.register.action;

import com.android.volley.VolleyError;

import name.walnut.kanjian.app.R;
import name.walnut.kanjian.app.support.ActionBarFragment;
import name.walnut.kanjian.app.support.BaseResourceAction;
import name.walnut.kanjian.app.ui.register.FillPasswordFragment;
import name.walnut.kanjian.app.ui.register.VerifyCodeFragment;
import name.walnut.kanjian.app.ui.util.ToastUtils;

/**
 * 注册 校验验证码
 */
public class VerifyCodeAction extends BaseResourceAction {
    @Override
    public void onSuccess(Response response) {
        dismissMessage();

        String token = response.getData();

        // 界面跳转
        VerifyCodeFragment fragment = (VerifyCodeFragment) getFragment();
        fragment.switchFragment(FillPasswordFragment.newInstance(token));
    }

    @Override
    public void onFailed(Response response) {
        dismissMessage();
        ToastUtils.toast(R.string.toast_verify_error);
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
