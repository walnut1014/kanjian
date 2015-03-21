package name.walnut.kanjian.app.ui.register.action;

import android.widget.Toast;

import com.android.volley.VolleyError;

import name.walnut.kanjian.app.R;
import name.walnut.kanjian.app.support.ActionBarActivity;
import name.walnut.kanjian.app.support.ActionBarFragment;
import name.walnut.kanjian.app.support.BaseResourceAction;
import name.walnut.kanjian.app.ui.register.RegisterFragment;
import name.walnut.kanjian.app.ui.register.VerifyCodeFragment;

/**
 * 注册 输入手机号码获取验证码
 */
public class RegisterSendAction extends BaseResourceAction{

    @Override
    public void onSuccess(String data) {
        RegisterFragment fragment = (RegisterFragment) getFragment();
        fragment.switchFragment(new VerifyCodeFragment(fragment.mobilephone));
    }

    @Override
    public void onFailed(String errorMsg) {
        RegisterFragment fragment = (RegisterFragment) getFragment();
        Toast.makeText(
                getActivity(),
                getActivity().getString(R.string.toast_registered, fragment.mobilephone),
                Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onErrorResponse(VolleyError volleyError) {

    }
}
