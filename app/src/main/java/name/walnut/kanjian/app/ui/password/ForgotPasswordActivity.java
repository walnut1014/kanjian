package name.walnut.kanjian.app.ui.password;

import android.os.Bundle;

import name.walnut.kanjian.app.R;
import name.walnut.kanjian.app.support.ActionBarActivity;

public class ForgotPasswordActivity extends ActionBarActivity {
    public ForgotPasswordActivity() {
        super(R.id.container);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base_fragment);

        switchFragment(new ForgotPasswordFragment());
    }

}
