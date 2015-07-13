package name.walnut.kanjian.app.ui.register.action;

import android.content.Intent;

import com.android.volley.VolleyError;

import org.json.JSONException;
import org.json.JSONObject;

import name.walnut.kanjian.app.R;
import name.walnut.kanjian.app.account.Account;
import name.walnut.kanjian.app.account.AccountBean;
import name.walnut.kanjian.app.support.ActionBarFragment;
import name.walnut.kanjian.app.support.BaseResourceAction;
import name.walnut.kanjian.app.ui.Constants;
import name.walnut.kanjian.app.ui.register.FillNicknameFragment;
import name.walnut.kanjian.app.ui.register.RegisterActivity;
import name.walnut.kanjian.app.ui.util.ToastUtils;
import name.walnut.kanjian.app.utils.Logger;

/**
 * 注册 输入个人信息完成注册
 */
public class RegisterAction extends BaseResourceAction {
    @Override
    public void onSuccess(Response response) {
        dismissMessage();
        Logger.e(response.getData());

        FillNicknameFragment fragment = (FillNicknameFragment) getFragment();

        String nickName = fragment.getNickname();

        try {
            JSONObject jsonObject = new JSONObject(response.getData());
            String mobilephone = jsonObject.optString("mobilephone");
            String headPhoto = jsonObject.optString("headPhotoPath");
            long id = jsonObject.optLong("id");
            int photoCount = jsonObject.optInt("photoCount");

            AccountBean accountBean = new AccountBean();
            accountBean.setMobilePhone(mobilephone);
            accountBean.setNickname(nickName);
            accountBean.setHeadPhotoPath(headPhoto);
            accountBean.setId(id);
            accountBean.setPhotoCount(photoCount);

            Account.INSTANCE.setAccount(accountBean);

        } catch (JSONException e) {
            e.printStackTrace();
        }

        // 注册成功，弹出提示，跳转到主页面
        ToastUtils.toast(R.string.toast_register_succeed);
        getActivity().finish();

        Intent intent = new Intent(Constants.Action.MAIN_ACTION);
        getFragment().startActivity(intent);
    }

    @Override
    public void onFailed(Response response) {
        ToastUtils.toast(response.getMessage());
        dismissMessage();
    }

    @Override
    public void onErrorResponse(VolleyError volleyError) {
        ToastUtils.toast(R.string.toast_error_network);
        dismissMessage();
    }

    private void dismissMessage() {
        ActionBarFragment fragment = (ActionBarFragment) getFragment();
        fragment.dismissMessage();
    }
}
