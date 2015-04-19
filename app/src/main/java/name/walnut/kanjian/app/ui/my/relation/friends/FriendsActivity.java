package name.walnut.kanjian.app.ui.my.relation.friends;

import android.os.Bundle;

import name.walnut.kanjian.app.R;
import name.walnut.kanjian.app.support.ActionBarActivity;

public class FriendsActivity extends ActionBarActivity {

    public FriendsActivity() {
        super(R.id.container);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base_fragment);
        if (savedInstanceState == null) {
            switchFragment(new FriendsFragment());
        }
    }

}
