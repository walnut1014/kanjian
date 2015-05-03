package name.walnut.kanjian.app.ui.my.relation.newfriends.action;

import android.app.Fragment;

import com.android.volley.VolleyError;

import name.walnut.kanjian.app.R;
import name.walnut.kanjian.app.support.BaseResourceAction;
import name.walnut.kanjian.app.ui.my.relation.newfriends.NewFriendFragment;
import name.walnut.kanjian.app.ui.my.relation.newfriends.SearchResultFragment;
import name.walnut.kanjian.app.ui.util.ToastUtils;
import name.walnut.kanjian.app.utils.Logger;

/**
 * 好友邀请 action
 */
public class InviteFriendAction extends BaseResourceAction {
    @Override
    public void onSuccess(Response response) {
        Logger.e(response.getData());
        onResult(true);
    }

    @Override
    public void onFailed(Response response) {
        onResult(false);
        ToastUtils.toast(response.getMessage());
    }

    @Override
    public void onErrorResponse(VolleyError volleyError) {
        Logger.e(volleyError.getMessage());
        onResult(false);
        ToastUtils.toast(R.string.toast_error_network);
    }

    public void onResult(boolean success) {
        Fragment fragment = getFragment();
        if (fragment == null) return;

        if (fragment instanceof SearchResultFragment) {
            SearchResultFragment searchResultFragment = (SearchResultFragment) fragment;
            searchResultFragment.onInviteResult(success);

        } else if (fragment instanceof NewFriendFragment) {
            NewFriendFragment newFriendFragment = (NewFriendFragment) fragment;
            newFriendFragment.onInviteResult(success);
        }
    }
}
