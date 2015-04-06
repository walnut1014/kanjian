package name.walnut.kanjian.app.ui.login.action;

import android.content.Intent;
import android.widget.Toast;

import com.android.volley.VolleyError;

import name.walnut.kanjian.app.support.BaseResourceAction;
import name.walnut.kanjian.app.ui.Constants;
import name.walnut.kanjian.app.ui.login.LoginAlertDialogFragment;
import name.walnut.kanjian.app.utils.Logger;


public class LoginAction extends BaseResourceAction {

    @Override
    public void onSuccess(Response response) {
        getActivity().finish();
        Intent intent = new Intent(Constants.Action.MAIN_ACTION);
        getFragment().startActivity(intent);
    }

    @Override
    public void onFailed(Response response) {

        //{"message":"您的手机号13622309539还未注册","data":-1,"success":false}
        //{"message":"登陆密码错误","data":-2,"success":false}
        Logger.d(response.getMessage());
        // 账号未注册
        switch (response.getData()) {
            case MESSAGE_CODE_UNREGISTER:
                LoginAlertDialogFragment.showDialog(getFragment().getFragmentManager(), response.getMessage());
                break;
            case MESSAGE_CODE_UNMATCH:
                // 密码不正确
                Toast.makeText(getActivity(), response.getMessage(), Toast.LENGTH_LONG).show();
                break;
            default:
                Toast.makeText(getActivity(), response.getMessage(), Toast.LENGTH_LONG).show();
                break;
        }
    }

    @Override
    public void onErrorResponse(VolleyError volleyError) {
    }


    public static final String MESSAGE_CODE_UNREGISTER = "-1";
    public static final String MESSAGE_CODE_UNMATCH = "-2";
}
