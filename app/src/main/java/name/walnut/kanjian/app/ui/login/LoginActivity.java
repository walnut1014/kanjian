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


        Intent intent = new Intent(Constants.Action.MAIN_ACTION);
        startActivity(intent);

		switchFragment(new LoginFragment());
	}
	
	public void intoMain() {
		Intent intent = new Intent(MAIN_ACTION);
		startActivity(intent);
		finish();
	}
	
	private final static String MAIN_ACTION = "kanjian.intent.action.MAIN";

}
