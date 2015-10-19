package name.walnut.kanjian.app.service.impl;

import java.util.HashMap;
import java.util.Map;

import name.walnut.kanjian.app.service.BaseService;
import name.walnut.kanjian.app.service.LoginService;

public class LoginServiceImpl extends BaseService implements LoginService {

    @Override
    public void login(String phonemobile, String password) {

        Map<String, Object> param = new HashMap<>(2);
        param.put("mobilephone", phonemobile);
        param.put("password", password);
    }

}
