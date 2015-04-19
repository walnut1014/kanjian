package name.walnut.kanjian.app.ui.my.relation.request;

import android.os.Bundle;

import name.walnut.kanjian.app.R;
import name.walnut.kanjian.app.support.ActionBarActivity;

public class FriendRequestActivity extends ActionBarActivity {

    public FriendRequestActivity() {
        super(R.id.container);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base_fragment);

        switchFragment(new FriendRequestFragment());
    }

}
