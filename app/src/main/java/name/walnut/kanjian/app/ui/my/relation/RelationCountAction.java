package name.walnut.kanjian.app.ui.my.relation;

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

        try {

            //TODO 头像路径 “headPhoto/"+headPhotoPath
            /*JSONObject jsonObject = new JSONObject(response.getData());
            int friendCount = jsonObject.getInt("friendCount");
            int unreadCount = jsonObject.getInt("unreadCount");
            String nickname = jsonObject.getString("nickName");
            String avatarPath = jsonObject.getString("headPhotoPath");

            Account.INSTANCE.setAccount(friendCount, unreadCount, nickname, avatarPath, "");*/
            //TODO 设置未读消息数以及好友个数
            JSONObject jsonObject = new JSONObject(response.getData());

            MyFragment fragment = (MyFragment) getFragment();
            fragment.showAccount();

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
