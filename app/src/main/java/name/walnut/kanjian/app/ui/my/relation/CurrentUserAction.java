package name.walnut.kanjian.app.ui.my.relation;

import android.util.Log;

import com.android.volley.VolleyError;

import org.json.JSONException;
import org.json.JSONObject;

import name.walnut.kanjian.app.account.Account;
import name.walnut.kanjian.app.account.AccountBean;
import name.walnut.kanjian.app.support.BaseResourceAction;
import name.walnut.kanjian.app.utils.Logger;

/**
 * 获取当前用户信息
 */
public class CurrentUserAction extends BaseResourceAction {
    @Override
    public void onSuccess(Response response) {
        Logger.e(response.getData());

        try {
            JSONObject jsonObject = new JSONObject(response.getData());
            // 缓存个人信息到本地

            long id = jsonObject.optLong("id");
            String avatarPath = jsonObject.optString("headPhotoPath");
            String nickname = jsonObject.optString("nickName");
            String mobilePhone = jsonObject.optString("mobilephone");
            int photoCount = jsonObject.optInt("photoCount", 0);

            AccountBean accountBean = new AccountBean();
            accountBean.setHeadPhotoPath(avatarPath);
            accountBean.setMobilePhone(mobilePhone);
            accountBean.setNickname(nickname);
            accountBean.setPhotoCount(photoCount);
            accountBean.setId(id);

            Account.INSTANCE.setAccount(accountBean);

            MyFragment fragment = (MyFragment) getFragment();
            if (fragment != null && !fragment.isDetached()) {
                fragment.showAccount();
            }
        } catch (JSONException ex) {
            Log.e("系统错误", "解析json异常", ex);
        }

    }

    @Override
    public void onFailed(Response response) {
    }

    @Override
    public void onErrorResponse(VolleyError volleyError) {
    }
}
