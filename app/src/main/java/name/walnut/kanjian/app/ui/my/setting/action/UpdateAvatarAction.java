package name.walnut.kanjian.app.ui.my.setting.action;

import com.android.volley.VolleyError;

import name.walnut.kanjian.app.R;
import name.walnut.kanjian.app.account.Account;
import name.walnut.kanjian.app.support.ActionBarFragment;
import name.walnut.kanjian.app.support.BaseResourceAction;
import name.walnut.kanjian.app.ui.my.setting.SettingFragment;
import name.walnut.kanjian.app.ui.util.ToastUtils;

/**
 * 更新头像action
 */
public class UpdateAvatarAction extends BaseResourceAction {
    @Override
    public void onSuccess(Response response) {
        dismissMessage();

        // 修改头像成功，保存新路径，更新界面头像
        String head = response.getData();
        Account.INSTANCE.setHeadPhotoPath(head);

        SettingFragment fragment = (SettingFragment) getFragment();
        fragment.updateAvatar();
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
