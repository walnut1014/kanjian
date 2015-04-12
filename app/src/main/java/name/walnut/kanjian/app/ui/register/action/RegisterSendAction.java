package name.walnut.kanjian.app.ui.register.action;

import android.widget.Toast;

import com.android.volley.VolleyError;

import name.walnut.kanjian.app.R;
import name.walnut.kanjian.app.support.ActionBarFragment;
import name.walnut.kanjian.app.support.BaseResourceAction;
import name.walnut.kanjian.app.support.KanJianApplication;
import name.walnut.kanjian.app.ui.register.RegisterFragment;
import name.walnut.kanjian.app.ui.register.VerifyCodeFragment;

/**
 * 注册 校验是否注册过
 */
public class RegisterSendAction extends BaseResourceAction{

    @Override
    public void onSuccess(Response response) {
        dismissMessage();
        // 未注册过，发送短信验证码，跳转
        RegisterFragment fragment = (RegisterFragment) getFragment();
        fragment.switchFragment(VerifyCodeFragment.newInstance(fragment.getMobilephone()));
    }

    @Override
    public void onFailed(Response response) {
        dismissMessage();
        RegisterFragment fragment = (RegisterFragment) getFragment();
        Toast.makeText(
                getActivity(),
                getActivity().getString(R.string.toast_registered, fragment.getMobilephone()),
                Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onErrorResponse(VolleyError volleyError) {
        dismissMessage();

        Toast.makeText(KanJianApplication.INTANCE, R.string.toast_error_network, Toast.LENGTH_LONG).show();
    }

    private void dismissMessage() {
        ActionBarFragment fragment = (ActionBarFragment) getFragment();
        fragment.dismissMessage();
    }
}
