package name.walnut.kanjian.app.ui.register;

import name.walnut.kanjian.app.R;
import name.walnut.kanjian.app.account.Account;
import name.walnut.kanjian.app.account.AccountBean;
import name.walnut.kanjian.app.support.ActionBarActivity;
import android.os.Bundle;

public class RegisterActivity extends ActionBarActivity {

	public RegisterActivity() {
		super(R.id.register_container);
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.setContentView(R.layout.activity_register);

        switchFragment(new RegisterFragment());
	}

    private String mobilephone; // 注册手机号

    public String getMobilephone() {
        return mobilephone;
    }

    public void setMobilephone(String mobilephone) {
        this.mobilephone = mobilephone;
    }

}
