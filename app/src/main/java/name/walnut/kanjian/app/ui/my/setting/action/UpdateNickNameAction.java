package name.walnut.kanjian.app.ui.my.setting.action;

import com.android.volley.VolleyError;

import name.walnut.kanjian.app.R;
import name.walnut.kanjian.app.account.Account;
import name.walnut.kanjian.app.support.ActionBarFragment;
import name.walnut.kanjian.app.support.BaseResourceAction;
import name.walnut.kanjian.app.ui.my.setting.UpdateNickNameFragment;
import name.walnut.kanjian.app.ui.util.ToastUtils;

/**
 * 更新昵称action
 */
public class UpdateNickNameAction extends BaseResourceAction{
    @Override
    public void onSuccess(Response response) {
        dismissMessage();

        // 修改昵称成功，保存并返回
        UpdateNickNameFragment fragment = (UpdateNickNameFragment) getFragment();
        Account.INSTANCE.setNickname(fragment.getNickName());

        fragment.getActionBarActivity().onBackPressed();
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
