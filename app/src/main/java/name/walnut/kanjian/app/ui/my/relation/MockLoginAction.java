package name.walnut.kanjian.app.ui.my.relation;

import android.content.Intent;

import com.android.volley.VolleyError;

import name.walnut.kanjian.app.R;
import name.walnut.kanjian.app.support.ActionBarFragment;
import name.walnut.kanjian.app.support.BaseResourceAction;
import name.walnut.kanjian.app.ui.Constants;
import name.walnut.kanjian.app.ui.login.LoginAlertDialogFragment;
import name.walnut.kanjian.app.ui.util.ToastUtils;
import name.walnut.kanjian.app.utils.Logger;


public class MockLoginAction extends BaseResourceAction {

    @Override
    public void onSuccess(Response response) {

    }

    @Override
    public void onFailed(Response response) {

    }

    @Override
    public void onErrorResponse(VolleyError volleyError) {

    }


    public static final String MESSAGE_CODE_UNREGISTER = "-1";
    public static final String MESSAGE_CODE_UNMATCH = "-2";
}
