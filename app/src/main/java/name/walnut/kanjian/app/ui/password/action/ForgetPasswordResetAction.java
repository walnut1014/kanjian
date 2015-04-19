package name.walnut.kanjian.app.ui.password.action;

import android.content.Intent;
import android.widget.Toast;

import com.android.volley.VolleyError;

import name.walnut.kanjian.app.R;
import name.walnut.kanjian.app.support.ActionBarFragment;
import name.walnut.kanjian.app.support.BaseResourceAction;
import name.walnut.kanjian.app.ui.Constants;
import name.walnut.kanjian.app.ui.util.ToastUtils;

/**
 * 忘记密码 重设密码
 */
public class ForgetPasswordResetAction extends BaseResourceAction {
    @Override
    public void onSuccess(Response response) {
        dismissMessage();
//        Toast.makeText(getActivity(), "成功", Toast.LENGTH_LONG).show();
        getActivity().finish();
        Intent intent = new Intent(Constants.Action.MAIN_ACTION);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        getFragment().startActivity(intent);
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
