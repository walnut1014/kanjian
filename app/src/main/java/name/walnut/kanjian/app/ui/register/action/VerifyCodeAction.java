package name.walnut.kanjian.app.ui.register.action;

import android.widget.Toast;

import com.android.volley.VolleyError;

import name.walnut.kanjian.app.R;
import name.walnut.kanjian.app.support.BaseResourceAction;
import name.walnut.kanjian.app.ui.register.FillPasswordFragment;
import name.walnut.kanjian.app.ui.register.VerifyCodeFragment;

/**
 * 注册 校验验证码
 */
public class VerifyCodeAction extends BaseResourceAction {
    @Override
    public void onSuccess(Response response) {

        VerifyCodeFragment fragment = (VerifyCodeFragment) getFragment();
        fragment.switchFragment(new FillPasswordFragment(response.getData()));
    }

    @Override
    public void onFailed(Response response) {
        Toast.makeText(getActivity(), R.string.toast_verify_error, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onErrorResponse(VolleyError volleyError) {
    }
}
