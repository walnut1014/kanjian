package name.walnut.kanjian.app.ui.my.relation.newfriends;

import android.os.Bundle;

import name.walnut.kanjian.app.R;
import name.walnut.kanjian.app.support.ActionBarActivity;

/**
 * 添加好友activity
 */
public class NewFriendActivity extends ActionBarActivity {

    public NewFriendActivity() {
        super(R.id.container);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base_fragment);
        if (savedInstanceState == null) {
            switchFragment(new NewFriendFragment());
        }
    }

}
