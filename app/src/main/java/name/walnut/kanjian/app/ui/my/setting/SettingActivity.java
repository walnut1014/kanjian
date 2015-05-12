package name.walnut.kanjian.app.ui.my.setting;

import android.os.Bundle;

import name.walnut.kanjian.app.R;
import name.walnut.kanjian.app.support.ActionBarActivity;

/**
 * 设置界面
 */
public class SettingActivity extends ActionBarActivity {

    public SettingActivity() {
        super(R.id.container);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base_fragment);

        switchFragment(new SettingFragment());
    }
}
