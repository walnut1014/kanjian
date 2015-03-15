package name.walnut.kanjian.app.service.impl;

import com.android.volley.VolleyError;

import java.util.HashMap;
import java.util.Map;

import name.walnut.kanjian.app.service.BaseService;
import name.walnut.kanjian.app.service.LoginService;
import name.walnut.kanjian.app.support.ActionBarActivity;
import name.walnut.kanjian.app.support.KanJianApplication;
import name.walnut.kanjian.app.ui.login.LoginActivity;
import name.walnut.kanjian.app.utils.DefalutJSONListener;
import name.walnut.kanjian.app.utils.RequestUtils;

public class LoginServiceImpl extends BaseService implements LoginService {

    @Override
    public void login(String phonemobile, String password) {

        Map<String, Object> param = new HashMap<>(2);
        param.put("mobilephone", phonemobile);
        param.put("password", password);
    }

}
