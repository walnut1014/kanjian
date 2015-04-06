package name.walnut.kanjian.app.ui.password.action;

import android.widget.Toast;

import com.android.volley.VolleyError;

import name.walnut.kanjian.app.support.BaseResourceAction;
import name.walnut.kanjian.app.support.SMSController;
import name.walnut.kanjian.app.ui.password.VerifyCodeFragment;
import name.walnut.kanjian.app.ui.password.ForgotPasswordFragment;
import name.walnut.kanjian.app.utils.Logger;

/**
 * 忘记密码 发送验证码
 */
public class ForgetPasswordSendAction extends BaseResourceAction {
    @Override
    public void onSuccess(Response response) {
        Logger.e("ForgetPasswordSendAction");
        Logger.d(response.getData());
        // 发送验证码，跳转页面
        ForgotPasswordFragment fragment = (ForgotPasswordFragment) getFragment();
        fragment.switchFragment(new VerifyCodeFragment(fragment.getMobilephone()));
        SMSController.getChinaVerificationCode(fragment.getMobilephone());
    }

    @Override
    public void onFailed(Response response) {
        Toast.makeText(getActivity(), response.getMessage(), Toast.LENGTH_LONG).show();
    }

    @Override
    public void onErrorResponse(VolleyError volleyError) {
        onSuccess(null);
    }
}
