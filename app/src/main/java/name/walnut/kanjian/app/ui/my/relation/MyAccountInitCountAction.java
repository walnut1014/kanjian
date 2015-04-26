package name.walnut.kanjian.app.ui.my.relation;

import com.android.volley.VolleyError;

import org.json.JSONException;
import org.json.JSONObject;

import name.walnut.kanjian.app.R;
import name.walnut.kanjian.app.support.BaseResourceAction;
import name.walnut.kanjian.app.ui.util.ToastUtils;
import name.walnut.kanjian.app.utils.Logger;

/**
 * 获取好友数和好友请求数 action
 */
public class MyAccountInitCountAction extends BaseResourceAction {
    @Override
    public void onSuccess(Response response) {
        Logger.e(response.getData());

        try {

            //TODO 头像路径 “headPhoto/"+headPhotoPath
            JSONObject jsonObject = new JSONObject(response.getData());
            int friendCount = jsonObject.getInt("friendCount");
            int unreadCount = jsonObject.getInt("unreadCount");

            MyFragment fragment = (MyFragment) getFragment();
            fragment.showRelation(friendCount, unreadCount);

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
