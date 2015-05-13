package name.walnut.kanjian.app.support;

import android.app.Activity;
import android.os.Bundle;

/**
 *
 */
public class BaseActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityManager.getScreenManager().pushActivity(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ActivityManager.getScreenManager().popActivity(this);
    }
}
