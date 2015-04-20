package name.walnut.kanjian.app.ui.my.relation.request.action;

import com.android.volley.VolleyError;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import name.walnut.kanjian.app.R;
import name.walnut.kanjian.app.support.BaseResourceAction;
import name.walnut.kanjian.app.ui.my.relation.request.FriendRequest;
import name.walnut.kanjian.app.ui.my.relation.request.FriendRequestFragment;
import name.walnut.kanjian.app.ui.util.ToastUtils;
import name.walnut.kanjian.app.utils.Logger;

/**
 * 好友请求 action
 */
public class RelationListAction extends BaseResourceAction {
    @Override
    public void onSuccess(Response response) {
        Logger.e(response.getData());
        List<FriendRequest> requestList = new ArrayList<>();
        try {
            JSONArray array = new JSONArray(response.getData());
            for (int i = 0; i < array.length(); i++) {
                JSONObject jsonObject = array.getJSONObject(i);
                FriendRequest request = new FriendRequest();
                request.setMobilePhone(jsonObject.getString("mobilephone"));
                request.setStatusStr(jsonObject.getString("relationStatus"));
                requestList.add(request);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        onRequestResult(requestList);
    }

    @Override
    public void onFailed(Response response) {
        ToastUtils.toast(response.getMessage());
        onRequestResult(null);
    }

    @Override
    public void onErrorResponse(VolleyError volleyError) {
        ToastUtils.toast(R.string.toast_error_network);
        onRequestResult(null);
    }

    // 请求结果
    private void onRequestResult(List<FriendRequest> requestList) {
        FriendRequestFragment fragment = (FriendRequestFragment) getFragment();
        fragment.show(requestList);
    }
}
