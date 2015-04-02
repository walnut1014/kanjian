package name.walnut.kanjian.app.ui.password.action;

import com.android.volley.VolleyError;

import name.walnut.kanjian.app.support.BaseResourceAction;
import name.walnut.kanjian.app.ui.password.VerifyCodeFragment;
import name.walnut.kanjian.app.ui.password.ForgotPasswordFragment;
import name.walnut.kanjian.app.utils.Logger;

/**
 * 忘记密码 发送验证码
 */
public class ForgetPasswordSendAction extends BaseResourceAction {
    @Override
    public void onSuccess(Response response) {
        // TODO bug, 跳转到验证码界面返回后，无法再次跳转，确认不执行此处
        Logger.e("ForgetPasswordSendAction");
        Logger.d(response.getData());
        ForgotPasswordFragment fragment = (ForgotPasswordFragment) getFragment();
//        fragment.switchFragment(new FillPasswordFragment());
        fragment.switchFragment(new VerifyCodeFragment(fragment.getMobilephone()));
    }

    @Override
    public void onFailed(Response response) {
        onSuccess(null);
    }

    @Override
    public void onErrorResponse(VolleyError volleyError) {
        onSuccess(null);
    }
}
