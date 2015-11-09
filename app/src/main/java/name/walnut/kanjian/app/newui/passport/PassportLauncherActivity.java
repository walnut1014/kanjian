package name.walnut.kanjian.app.newui.passport;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import name.walnut.kanjian.app.R;

/**
 * Created by linxunjian on 15/10/27.
 */
public class PassportLauncherActivity extends Activity {

    public void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_passport_launcher);
    }

    public void onClickToRegister(View view) {

       startActivity(new Intent(this, RegisterPhoneActivity.class));
    }

    public void onClickToLogin(View view) {

        startActivity(new Intent());
    }
}

