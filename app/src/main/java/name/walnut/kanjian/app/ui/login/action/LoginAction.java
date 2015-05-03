package name.walnut.kanjian.app.ui.login.action;

import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.VolleyError;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import name.walnut.kanjian.app.R;
import name.walnut.kanjian.app.account.Account;
import name.walnut.kanjian.app.support.ActionBarFragment;
import name.walnut.kanjian.app.support.BaseResourceAction;
import name.walnut.kanjian.app.ui.Constants;
import name.walnut.kanjian.app.ui.login.LoginAlertDialogFragment;
import name.walnut.kanjian.app.ui.util.ToastUtils;
import name.walnut.kanjian.app.utils.Logger;


public class LoginAction extends BaseResourceAction {

    @Override
    public void onSuccess(Response response) {
        dismissMessage();

        Logger.e(response.getData());

        try {
            JSONObject jsonObject = new JSONObject(response.getData());
            // 缓存个人信息到本地

            long id = jsonObject.optLong("id");
            String avatarPath = jsonObject.optString("headPhotoPath");
            String nickname = jsonObject.optString("nickName");
            String mobilePhone = jsonObject.optString("mobilephone");

            Account.INSTANCE.setAccount(id, nickname, avatarPath, mobilePhone);
        } catch (JSONException ex) {
            Log.e("系统错误","解析json异常",ex);
        }

        getActivity().finish();
        Intent intent = new Intent(Constants.Action.MAIN_ACTION);
        getFragment().startActivity(intent);
    }

    @Override
    public void onFailed(Response response) {
        dismissMessage();

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
                ToastUtils.toast(response.getMessage());
                break;
            default:
                ToastUtils.toast(response.getMessage());
                break;
        }
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

    public static final String MESSAGE_CODE_UNREGISTER = "-1";
    public static final String MESSAGE_CODE_UNMATCH = "-2";
}
