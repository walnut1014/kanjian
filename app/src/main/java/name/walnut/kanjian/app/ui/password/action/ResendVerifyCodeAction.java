package name.walnut.kanjian.app.ui.password.action;

import android.widget.Toast;

import com.android.volley.VolleyError;

import name.walnut.kanjian.app.support.BaseResourceAction;
import name.walnut.kanjian.app.ui.password.VerifyCodeFragment;

/**
 * @deprecated
 * 忘记密码 重新发送验证码
 */
public class ResendVerifyCodeAction extends BaseResourceAction{
    @Override
    public void onSuccess(String data) {
        VerifyCodeFragment fragment = (VerifyCodeFragment) getFragment();
        fragment.startCountdown();
    }

    @Override
    public void onFailed(String errorMsg) {
        Toast.makeText(getActivity(), errorMsg, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onErrorResponse(VolleyError volleyError) {

    }
}
