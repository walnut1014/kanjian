package name.walnut.kanjian.app.ui.register.action;

import android.content.Intent;

import com.android.volley.VolleyError;

import name.walnut.kanjian.app.R;
import name.walnut.kanjian.app.support.ActionBarFragment;
import name.walnut.kanjian.app.support.BaseResourceAction;
import name.walnut.kanjian.app.ui.Constants;
import name.walnut.kanjian.app.ui.register.RegisterActivity;
import name.walnut.kanjian.app.ui.register.RegisterFragment;
import name.walnut.kanjian.app.ui.register.VerifyCodeFragment;
import name.walnut.kanjian.app.ui.util.ToastUtils;

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

        // 保存手机号码到activity中
        RegisterActivity activity = (RegisterActivity) getActivity();
        activity.setMobilephone(fragment.getMobilephone());
    }

    @Override
    public void onFailed(Response response) {
        dismissMessage();
        RegisterFragment fragment = (RegisterFragment) getFragment();
        ToastUtils.toast(getActivity().getString(R.string.toast_registered, fragment.getMobilephone()));

        getActivity().getFragmentManager().getBackStackEntryCount();
        getActivity().finish();
        Intent intent = new Intent(Constants.Action.LOGIN_ACTION);
        intent.putExtra("mobilephone", fragment.getMobilephone());
        getActivity().startActivity(intent);
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
