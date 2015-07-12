package name.walnut.kanjian.app.ui.login;

import name.walnut.kanjian.app.R;
import name.walnut.kanjian.app.support.ActionBarActivity;
import name.walnut.kanjian.app.ui.Constants;

import android.content.Intent;
import android.os.Bundle;

public class LoginActivity extends ActionBarActivity {

	public LoginActivity() {
		super(R.id.login_container);
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.setContentView(R.layout.activity_login);

		switchFragment(new LoginFragment());
	}
	
	private final static String MAIN_ACTION = "kanjian.intent.action.MAIN";

}
