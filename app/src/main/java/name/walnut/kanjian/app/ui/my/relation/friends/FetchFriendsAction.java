package name.walnut.kanjian.app.ui.my.relation.friends;

import com.android.volley.VolleyError;

import java.util.List;

import name.walnut.kanjian.app.R;
import name.walnut.kanjian.app.support.BaseResourceAction;
import name.walnut.kanjian.app.ui.util.ToastUtils;
import name.walnut.kanjian.app.utils.Logger;

/**
 * 获取好友列表action
 */
public class FetchFriendsAction extends BaseResourceAction {
    @Override
    public void onSuccess(Response response) {
        Logger.e(response.getData());
    }

    @Override
    public void onFailed(Response response) {
        ToastUtils.toast(response.getMessage());
    }

    @Override
    public void onErrorResponse(VolleyError volleyError) {
        ToastUtils.toast(R.string.toast_error_network);
    }

    private void onRequestResult(List<Friend> friendList) {
        FriendsFragment fragment = (FriendsFragment) getFragment();
        fragment.showFriends(friendList);
    }

}
