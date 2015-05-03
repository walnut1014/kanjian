package name.walnut.kanjian.app.ui.my.relation.newfriends.action;

import com.android.volley.VolleyError;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import name.walnut.kanjian.app.R;
import name.walnut.kanjian.app.support.BaseResourceAction;
import name.walnut.kanjian.app.ui.my.relation.newfriends.Friend;
import name.walnut.kanjian.app.ui.my.relation.newfriends.SearchResultFragment;
import name.walnut.kanjian.app.ui.util.ToastUtils;
import name.walnut.kanjian.app.utils.ContactsUtils;
import name.walnut.kanjian.app.utils.Logger;

/**
 * 查询好友action
 */
public class QueryUserAction extends BaseResourceAction {

    @Override
    public void onSuccess(Response response) {
        Logger.e(response.getData());

        List<Friend> friendList = new ArrayList<>();
        List<String> phones = new ArrayList<>();    // 手机号s

        if (response.getData() != null) {
            try {
                JSONArray jsonArray = new JSONArray(response.getData());
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject jsonObject = jsonArray.getJSONObject(i);

                    long id = jsonObject.getLong("id");
                    String avatar = jsonObject.getString("headPhotoPath");
                    String nickName = jsonObject.getString("nickName");
                    String mobilePhone = jsonObject.optString("mobilephone");
                    int relationStatus = jsonObject.getInt("relationStatus");

                    Friend friend = new Friend();
                    friend.setId(id);
                    friend.setMobilePhone(mobilePhone);
                    friend.setAvatar(avatar);
                    friend.setNickName(nickName);
                    friend.setRelation(relationStatus);

                    friendList.add(friend);
                    phones.add(mobilePhone);
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        if (phones.size() > 0) {
            // 根据手机号查询通讯录信息
            String[] phoneArray = new String[phones.size()];
            Map<String, String> nameMap = ContactsUtils.getContactsByPhone(getActivity(), phones.toArray(phoneArray));

            for (Friend request : friendList) {
                String name = nameMap.get(request.getMobilePhone());
                request.setContactsName(name);
            }
        }

        onRequestResult(friendList);
    }

    @Override
    public void onFailed(Response response) {
        ToastUtils.toast(response.getMessage());
        onRequestResult(null);
    }

    @Override
    public void onErrorResponse(VolleyError volleyError) {
        Logger.e(volleyError.getMessage());
        ToastUtils.toast(R.string.toast_error_network);
        onRequestResult(null);
    }

    private void onRequestResult(List<Friend> friends) {
        SearchResultFragment fragment = (SearchResultFragment) getFragment();
        fragment.showSearchResult(friends);
    }
}
