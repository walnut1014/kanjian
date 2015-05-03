package name.walnut.kanjian.app.ui.my.relation.request.action;

import android.app.Fragment;

import com.android.volley.VolleyError;

import name.walnut.kanjian.app.R;
import name.walnut.kanjian.app.support.BaseResourceAction;
import name.walnut.kanjian.app.ui.my.relation.request.FriendRequestFragment;
import name.walnut.kanjian.app.ui.util.ToastUtils;

/**
 * 接受好友请求action
 */
public class AgreeInviteAction extends BaseResourceAction {
    @Override
    public void onSuccess(Response response) {
        onResult(true);
    }

    @Override
    public void onFailed(Response response) {
        ToastUtils.toast(response.getMessage());
        onResult(false);
    }

    @Override
    public void onErrorResponse(VolleyError volleyError) {
        ToastUtils.toast(R.string.toast_error_network);
        onResult(false);
    }

    private void onResult(boolean success) {
        Fragment fragment = getFragment();
        if (fragment != null && fragment instanceof FriendRequestFragment) {
            ((FriendRequestFragment) fragment).onAgreeInviteResult(success);
        }
    }
}
