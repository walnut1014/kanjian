package name.walnut.kanjian.app.ui.register.action;

import android.widget.Toast;

import com.android.volley.VolleyError;

import name.walnut.kanjian.app.R;
import name.walnut.kanjian.app.support.ActionBarActivity;
import name.walnut.kanjian.app.support.ActionBarFragment;
import name.walnut.kanjian.app.support.BaseResourceAction;
import name.walnut.kanjian.app.support.SMSController;
import name.walnut.kanjian.app.ui.register.RegisterFragment;
import name.walnut.kanjian.app.ui.register.VerifyCodeFragment;

/**
 * TODO 校验是否注册过
 * 注册 输入手机号码获取验证码
 */
public class RegisterSendAction extends BaseResourceAction{

    @Override
    public void onSuccess(String data) {
        // 未注册过，发送短信验证码，跳转
        RegisterFragment fragment = (RegisterFragment) getFragment();
        fragment.switchFragment(new VerifyCodeFragment(fragment.getMobilephone()));
        SMSController.getChinaVerificationCode(fragment.getMobilephone());
    }

    @Override
    public void onFailed(String errorMsg) {
        RegisterFragment fragment = (RegisterFragment) getFragment();
        Toast.makeText(
                getActivity(),
                getActivity().getString(R.string.toast_registered, fragment.getMobilephone()),
                Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onErrorResponse(VolleyError volleyError) {

    }
}
