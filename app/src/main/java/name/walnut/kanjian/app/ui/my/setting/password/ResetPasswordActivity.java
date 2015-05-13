package name.walnut.kanjian.app.ui.my.setting.password;

import android.os.Bundle;

import name.walnut.kanjian.app.R;
import name.walnut.kanjian.app.support.ActionBarActivity;

/**
 * 重设密码activity
 */
public class ResetPasswordActivity extends ActionBarActivity {
    public ResetPasswordActivity() {
        super(R.id.container);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_base_fragment);
        switchFragment(new FillMobilePhoneFragment());
    }
}
