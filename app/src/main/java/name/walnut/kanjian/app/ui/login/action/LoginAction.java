package name.walnut.kanjian.app.ui.login.action;

import android.content.Intent;
import android.widget.Toast;

import com.android.volley.VolleyError;

import org.json.JSONObject;

import name.walnut.kanjian.app.R;
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

        //TODO
        //{"message":"您的手机号13622309539还未注册","data":-1,"success":false}
        //{"message":"登陆密码错误","data":-2,"success":false}
        Logger.d(errorMsg);
        // 账号未注册

        // 密码不正确
        Toast.makeText(getActivity(), errorMsg, Toast.LENGTH_LONG).show();
    }

    @Override
    public void onErrorResponse(VolleyError volleyError) {
    }

}
