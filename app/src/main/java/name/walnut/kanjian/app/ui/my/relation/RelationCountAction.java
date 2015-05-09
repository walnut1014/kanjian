package name.walnut.kanjian.app.ui.my.relation;

import android.util.Log;

import com.android.volley.VolleyError;

import org.json.JSONException;
import org.json.JSONObject;

import name.walnut.kanjian.app.R;
import name.walnut.kanjian.app.account.Account;
import name.walnut.kanjian.app.support.BaseResourceAction;
import name.walnut.kanjian.app.ui.util.ToastUtils;
import name.walnut.kanjian.app.utils.Logger;

/**
 * 获取好友数和好友请求数 action
 */
public class RelationCountAction extends BaseResourceAction {
    @Override
    public void onSuccess(Response response) {

        Logger.e(response.getData());
        try {
            //设置未读消息数以及好友个数
            JSONObject jsonObject = new JSONObject(response.getData());
            int friendCount = jsonObject.getInt("friendsCount");
            int unreadCount = jsonObject.getInt("unreadCount");

            MyFragment fragment = (MyFragment) getFragment();
            fragment.showRelationCount(friendCount, unreadCount);

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onFailed(Response response) {
        ToastUtils.toast(response.getMessage());
    }

    @Override
    public void onErrorResponse(VolleyError volleyError) {
        ToastUtils.toast(R.string.toast_error_network);
    }
}
