package name.walnut.kanjian.app.ui.register.action;

import android.widget.Toast;

import com.android.volley.VolleyError;

import name.walnut.kanjian.app.R;
import name.walnut.kanjian.app.support.BaseResourceAction;
import name.walnut.kanjian.app.support.SMSController;
import name.walnut.kanjian.app.ui.register.RegisterFragment;
import name.walnut.kanjian.app.ui.register.VerifyCodeFragment;

/**
 * 注册 校验是否注册过
 */
public class RegisterSendAction extends BaseResourceAction{

    @Override
    public void onSuccess(Response response) {
        // 未注册过，发送短信验证码，跳转
        RegisterFragment fragment = (RegisterFragment) getFragment();
        fragment.switchFragment(new VerifyCodeFragment(fragment.getMobilephone()));
        SMSController.getChinaVerificationCode(fragment.getMobilephone());
    }

    @Override
    public void onFailed(Response response) {
        RegisterFragment fragment = (RegisterFragment) getFragment();
        Toast.makeText(
                getActivity(),
                getActivity().getString(R.string.toast_registered, fragment.getMobilephone()),
                Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onErrorResponse(VolleyError volleyError) {
        //TODO 如果出现服务器端的异常会进入这个方法
        System.out.println(volleyError);
    }
}
