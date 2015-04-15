package name.walnut.kanjian.app.ui.register.action;

import android.content.Intent;
import android.widget.Toast;

import com.android.volley.VolleyError;

import name.walnut.kanjian.app.R;
import name.walnut.kanjian.app.support.ActionBarFragment;
import name.walnut.kanjian.app.support.BaseResourceAction;
import name.walnut.kanjian.app.support.KanJianApplication;
import name.walnut.kanjian.app.ui.Constants;
import name.walnut.kanjian.app.ui.register.RegisterFragment;

/**
 * 注册 输入个人信息完成注册
 */
public class RegisterAction extends BaseResourceAction {
    @Override
    public void onSuccess(Response response) {
        dismissMessage();

        // 注册成功，弹出提示，跳转到主页面
        Toast.makeText(getActivity(), R.string.toast_register_succeed, Toast.LENGTH_LONG).show();
        getActivity().finish();

        Intent intent = new Intent(Constants.Action.MAIN_ACTION);
        getFragment().startActivity(intent);
    }

    @Override
    public void onFailed(Response response) {
        Toast.makeText(getActivity(), response.getMessage(), Toast.LENGTH_LONG).show();
        dismissMessage();
    }

    @Override
    public void onErrorResponse(VolleyError volleyError) {
        Toast.makeText(KanJianApplication.INTANCE, R.string.toast_error_network, Toast.LENGTH_LONG).show();
        dismissMessage();
    }

    private void dismissMessage() {
        ActionBarFragment fragment = (ActionBarFragment) getFragment();
        fragment.dismissMessage();
    }
}
