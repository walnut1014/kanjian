package name.walnut.kanjian.app.ui.password.action;

import android.widget.Toast;

import com.android.volley.VolleyError;

import name.walnut.kanjian.app.support.BaseResourceAction;

/**
 * 忘记密码 重设密码
 */
public class ForgetPasswordResetAction extends BaseResourceAction {
    @Override
    public void onSuccess(Response response) {
        Toast.makeText(getActivity(), "成功", Toast.LENGTH_LONG).show();
    }

    @Override
    public void onFailed(Response response) {
        Toast.makeText(getActivity(), response.getMessage(), Toast.LENGTH_LONG).show();
    }

    @Override
    public void onErrorResponse(VolleyError volleyError) {

    }
}
