package name.walnut.kanjian.app.ui.register.action;

import android.content.Intent;

import com.android.volley.VolleyError;

import name.walnut.kanjian.app.R;
import name.walnut.kanjian.app.support.ActionBarFragment;
import name.walnut.kanjian.app.support.BaseResourceAction;
import name.walnut.kanjian.app.ui.Constants;
import name.walnut.kanjian.app.ui.register.FillNicknameFragment;
import name.walnut.kanjian.app.ui.register.RegisterActivity;
import name.walnut.kanjian.app.ui.util.ToastUtils;

/**
 * 注册 输入个人信息完成注册
 */
public class RegisterAction extends BaseResourceAction {
    @Override
    public void onSuccess(Response response) {
        dismissMessage();
        System.out.println(response.getData());
        FillNicknameFragment fragment = (FillNicknameFragment) getFragment();

        // TODO 注册返回信息缺少用户id
        RegisterActivity activity = (RegisterActivity) getActivity();
        activity.setAvatar(response.getData());
        activity.setNickName(fragment.getNickname());
        activity.onRegisterSuccess();   // 保存账号信息

        // 注册成功，弹出提示，跳转到主页面
        ToastUtils.toast(R.string.toast_register_succeed);
        getActivity().finish();

        Intent intent = new Intent(Constants.Action.MAIN_ACTION);
        getFragment().startActivity(intent);
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
