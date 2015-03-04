package name.walnut.kanjian.app.service.impl;

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

	
	public LoginServiceImpl(ActionBarActivity activity){
		super(activity);
	}
	
	@Override
	public void login(String phonemobile, String password) {
		
		Map<String, Object> param = new HashMap<>(1);
		param.put("mobilephone", phonemobile);
		param.put("password", password);
		RequestUtils.postJSON("passport/login", param, new DefalutJSONListener() {
			
			@Override
			public void onSuccess(Object data) {
				
				KanJianApplication.isLogin = true;
				LoginServiceImpl.this.activity.doneMessage();
				
				if(KanJianApplication.isLogin){
					((LoginActivity)activity).intoMain();
				}
			}

			@Override
			public void beforeFailed() {
				LoginServiceImpl.this.activity.doneMessage();
			}
		});
		
		this.activity.showMessage("ÕýÔÚµÇÂ½");
	}

}
