package name.walnut.kanjian.app.ui.my.relation.request.action;

import android.app.Fragment;

import com.android.volley.VolleyError;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import name.walnut.kanjian.app.R;
import name.walnut.kanjian.app.support.BaseResourceAction;
import name.walnut.kanjian.app.support.KanJianApplication;
import name.walnut.kanjian.app.ui.my.relation.request.FriendRequest;
import name.walnut.kanjian.app.ui.my.relation.request.FriendRequestFragment;
import name.walnut.kanjian.app.ui.util.ToastUtils;
import name.walnut.kanjian.app.utils.ContactsUtils;
import name.walnut.kanjian.app.utils.Logger;

/**
 * 好友请求 action
 */
public class RelationListAction extends BaseResourceAction {
    @Override
    public void onSuccess(Response response) {
        Logger.e(response.getData());
        List<FriendRequest> requestList = new ArrayList<>();
        List<String> phones = new ArrayList<>();    // 手机号s
        try {
            JSONArray array = new JSONArray(response.getData());
            for (int i = 0; i < array.length(); i++) {
                JSONObject jsonObject = array.getJSONObject(i);
                String phone = jsonObject.getString("mobilephone");

                FriendRequest request = new FriendRequest();
                request.setId(jsonObject.getLong("id"));
                request.setAvatar(jsonObject.optString("headPhotoPath"));
                request.setMobilePhone(phone);
                request.setNickName(jsonObject.optString("nickName"));
                request.setAgree(jsonObject.getBoolean("agree"));
                request.setInvited(jsonObject.getBoolean("invited"));

                requestList.add(request);
                phones.add(phone);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        if (phones.size() > 0) {
            String[] phoneArray = new String[phones.size()];
            Map<String, String> nameMap = ContactsUtils.getContactsByPhone(KanJianApplication.INSTANCE,
                    phones.toArray(phoneArray));

            for (FriendRequest request : requestList) {
                String name = nameMap.get(request.getMobilePhone());
                request.setContactsName(name);
            }
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
        Fragment fragment = getFragment();
        if (fragment != null && fragment instanceof FriendRequestFragment) {
            ((FriendRequestFragment) fragment).show(requestList);
        }
    }
}
