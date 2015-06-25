package name.walnut.kanjian.app.ui.login.action;

import android.app.Fragment;
import android.content.Intent;
import android.util.Log;

import com.android.volley.VolleyError;

import org.json.JSONException;
import org.json.JSONObject;

import name.walnut.kanjian.app.R;
import name.walnut.kanjian.app.account.Account;
import name.walnut.kanjian.app.account.AccountBean;
import name.walnut.kanjian.app.support.ActionBarFragment;
import name.walnut.kanjian.app.support.BaseResourceAction;
import name.walnut.kanjian.app.support.KJAlertDialogFragment;
import name.walnut.kanjian.app.ui.Constants;
import name.walnut.kanjian.app.ui.util.ToastUtils;
import name.walnut.kanjian.app.views.KJAlertDialog;


public class LoginAction extends BaseResourceAction {

    @Override
    public void onSuccess(Response response) {
        dismissMessage();

        try {
            JSONObject jsonObject = new JSONObject(response.getData());
            // 缓存个人信息到本地

            long id = jsonObject.optLong("id");
            String avatarPath = jsonObject.optString("headPhotoPath");
            String nickname = jsonObject.optString("nickName");
            String mobilePhone = jsonObject.optString("mobilephone");
            int photoCount = jsonObject.optInt("photoCount", 0);

            AccountBean accountBean = new AccountBean();
            accountBean.setHeadPhotoPath(avatarPath);
            accountBean.setMobilePhone(mobilePhone);
            accountBean.setNickname(nickname);
            accountBean.setPhotoCount(photoCount);
            accountBean.setId(id);

            Account.INSTANCE.setAccount(accountBean);
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
        // 账号未注册
        final Fragment fragment = getFragment();
        switch (response.getData()) {
            case MESSAGE_CODE_UNREGISTER:
                final KJAlertDialogFragment dialogFragment = new KJAlertDialogFragment()
                        .setContent(response.getMessage())
                        .setNegativeText(fragment.getString(R.string.dialog_login_alert_cancel))
                        .setPositiveText(fragment.getString(R.string.dialog_login_alert_continue))
                        .setPositiveClickListener(new KJAlertDialog.OnKJClickListener() {
                            @Override
                            public void onClick(KJAlertDialog dialog) {
                                getActivity().finish();
                                Intent intent = new Intent(Constants.Action.REGISTER_ACTION);
                                fragment.startActivity(intent);
                            }
                        });
                dialogFragment.show(fragment.getFragmentManager());
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
