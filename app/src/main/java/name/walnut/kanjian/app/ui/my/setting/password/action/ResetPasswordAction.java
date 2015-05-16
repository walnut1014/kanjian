package name.walnut.kanjian.app.ui.my.setting.password.action;

import com.android.volley.VolleyError;

import name.walnut.kanjian.app.R;
import name.walnut.kanjian.app.support.ActionBarFragment;
import name.walnut.kanjian.app.support.BaseResourceAction;
import name.walnut.kanjian.app.ui.util.ToastUtils;

/**
 * 忘记密码 重设密码
 */
public class ResetPasswordAction extends BaseResourceAction {
    @Override
    public void onSuccess(Response response) {
        dismissMessage();
        getActivity().finish();
        ToastUtils.toast(R.string.toast_reset_password_success);
    }

    @Override
    public void onFailed(Response response) {
        dismissMessage();
        ToastUtils.toast(response.getMessage());
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
