package name.walnut.kanjian.app.ui.password.action;

import android.widget.Toast;

import com.android.volley.VolleyError;

import name.walnut.kanjian.app.support.BaseResourceAction;
import name.walnut.kanjian.app.ui.password.FillPasswordFragment;
import name.walnut.kanjian.app.ui.password.VerifyCodeFragment;

/**
 * 忘记密码 验证验证码
 */
public class ForgetVerifyCodeAction extends BaseResourceAction {
    @Override
    public void onSuccess(Response response) {
        VerifyCodeFragment fragment = (VerifyCodeFragment) getFragment();
        fragment.switchFragment(new FillPasswordFragment(response.getData()));
    }

    @Override
    public void onFailed(Response response) {
        Toast.makeText(getActivity(), response.getMessage(), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onErrorResponse(VolleyError volleyError) {
    }
}
