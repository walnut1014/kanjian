package name.walnut.kanjian.app.ui.my.setting.password.action;

import com.android.volley.VolleyError;

import name.walnut.kanjian.app.R;
import name.walnut.kanjian.app.support.ActionBarFragment;
import name.walnut.kanjian.app.support.BaseResourceAction;
import name.walnut.kanjian.app.ui.my.setting.password.FillMobilePhoneFragment;
import name.walnut.kanjian.app.ui.my.setting.password.VerifyCodeFragment;
import name.walnut.kanjian.app.ui.util.ToastUtils;

/**
 * 忘记密码 验证手机号action
 */
public class CurrMobilePhoneAction extends BaseResourceAction{
    @Override
    public void onSuccess(Response response) {
        dismissMessage();

        FillMobilePhoneFragment fragment = (FillMobilePhoneFragment) getFragment();
        fragment.switchFragment(VerifyCodeFragment.newInstance(fragment.getMobilephone()));

    }

    @Override
    public void onFailed(Response response) {
        ToastUtils.toast(response.getMessage());
        dismissMessage();
    }

    @Override
    public void onErrorResponse(VolleyError volleyError) {
        ToastUtils.toast(R.string.toast_error_network);
        dismissMessage();
    }

    private void dismissMessage() {
        ActionBarFragment fragment = (ActionBarFragment) getFragment();
        fragment.dismissMessage();
    }
}
