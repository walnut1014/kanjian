package name.walnut.kanjian.app.ui.password.action;

import android.widget.Toast;

import com.android.volley.VolleyError;

import name.walnut.kanjian.app.R;
import name.walnut.kanjian.app.support.ActionBarFragment;
import name.walnut.kanjian.app.support.BaseResourceAction;
import name.walnut.kanjian.app.support.SMSController;
import name.walnut.kanjian.app.ui.password.VerifyCodeFragment;
import name.walnut.kanjian.app.ui.password.ForgotPasswordFragment;

/**
 * 忘记密码 发送验证码
 */
public class ForgetPasswordSendAction extends BaseResourceAction {
    @Override
    public void onSuccess(Response response) {
        dismissMessage();
        // 发送验证码，跳转页面
        ForgotPasswordFragment fragment = (ForgotPasswordFragment) getFragment();
        fragment.switchFragment(new VerifyCodeFragment(fragment.getMobilephone()));
        SMSController.getChinaVerificationCode(fragment.getMobilephone());
    }

    @Override
    public void onFailed(Response response) {
        dismissMessage();
        Toast.makeText(getActivity(), response.getMessage(), Toast.LENGTH_LONG).show();
    }

    @Override
    public void onErrorResponse(VolleyError volleyError) {
        dismissMessage();
        Toast.makeText(getActivity(), R.string.toast_error_network, Toast.LENGTH_LONG).show();
    }

    private void dismissMessage() {
        ActionBarFragment fragment = (ActionBarFragment) getFragment();
        fragment.dismissMessage();
    }
}
