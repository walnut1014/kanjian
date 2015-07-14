package name.walnut.kanjian.app.ui.album;

import android.os.Bundle;

import name.walnut.kanjian.app.R;
import name.walnut.kanjian.app.support.ActionBarActivity;

/**
 * 个人主页
 * intent extra内容：
 * userId :  用户id
 * userName : 用户昵称
 */
public class PersonalActivity extends ActionBarActivity{

    public PersonalActivity() {
        super(R.id.container);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base_fragment);
        switchFragment(PersonalFragment.newInstance());
    }
}
