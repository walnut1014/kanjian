package name.walnut.kanjian.app.service;

import name.walnut.kanjian.app.support.ActionBarActivity;

public class BaseService {
	
	public BaseService(ActionBarActivity activity){
		this.activity = activity;
	}
	
	protected ActionBarActivity activity;
}
