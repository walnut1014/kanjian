package name.walnut.kanjian.app.ui.my.relation.friends;

import android.app.Fragment;

import com.android.volley.VolleyError;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
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
        List<FriendInfo> friendInfos = new ArrayList<>();
        try {
            JSONArray jsonArray = new JSONArray(response.getData());
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);

                FriendInfo friend = new FriendInfo();
                if(!jsonObject.isNull("headPhotoPath"))
                    friend.setAvatar(jsonObject.getString("headPhotoPath"));
                friend.setPhotoCount(jsonObject.getInt("photoCount"));
                friend.setMobilePhone(jsonObject.getString("mobilephone"));
                friend.setNickName(jsonObject.getString("nickName"));
                friend.setUserId(jsonObject.getLong("userId"));

                friendInfos.add(friend);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        onRequestResult(friendInfos);
    }

    @Override
    public void onFailed(Response response) {
        onRequestResult(null);
        ToastUtils.toast(response.getMessage());
    }

    @Override
    public void onErrorResponse(VolleyError volleyError) {
        onRequestResult(null);
        ToastUtils.toast(R.string.toast_error_network);
    }

    private void onRequestResult(List<FriendInfo> friendList) {
        Fragment fragment = getFragment();
        if (fragment != null && (fragment instanceof FriendsFragment)) {
            ((FriendsFragment) fragment).showFriends(friendList);
        }
    }

}
