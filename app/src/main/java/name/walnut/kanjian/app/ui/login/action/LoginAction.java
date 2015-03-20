package name.walnut.kanjian.app.ui.login.action;

import android.content.Intent;

import com.android.volley.VolleyError;

import org.json.JSONObject;

import name.walnut.kanjian.app.resource.impl.DefaultResourceAction;
import name.walnut.kanjian.app.support.BaseResourceAction;
import name.walnut.kanjian.app.ui.Constants;
import name.walnut.kanjian.app.utils.Logger;


public class LoginAction extends BaseResourceAction {

    @Override
    public void onSuccess(String data) {
        getActivity().finish();
        Intent intent = new Intent(Constants.Action.MAIN_ACTION);
        getFragment().startActivity(intent);
    }

    @Override
    public void onFailed(String errorMsg) {
        Logger.d(errorMsg);
        // 账号未注册

        // 密码不正确
    }

    @Override
    public void onErrorResponse(VolleyError volleyError) {
    }

}
