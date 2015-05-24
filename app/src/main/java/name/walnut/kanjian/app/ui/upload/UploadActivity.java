package name.walnut.kanjian.app.ui.upload;

import android.os.Bundle;

import name.walnut.kanjian.app.R;
import name.walnut.kanjian.app.support.ActionBarActivity;

/**
 * 上传图片activity
 */
public class UploadActivity extends ActionBarActivity {

    public UploadActivity() {
        super(R.id.container);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_base_fragment);

        switchFragment(new UploadFragment());
    }
}
