package name.walnut.kanjian.app.ui;

import android.os.Bundle;
import android.view.View;

import name.walnut.kanjian.app.R;
import name.walnut.kanjian.app.support.ActionBarActivity;
import name.walnut.kanjian.app.support.ActionBarFragment;

public class ClauseActivity extends ActionBarActivity implements Constants.Action{

    public ClauseActivity() {
        super(R.id.container);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_clause);

        String action = getIntent().getAction();
        if (PRIVACY_ACTION.equals(action)) {
            switchFragment(new PrivacyFragment());
        } else if (CLAUSE_ACTION.equals(action)){
            switchFragment(new ClauseFragment());
        }

        backTextBtn.setVisibility(View.VISIBLE);
        backButton.setVisibility(View.GONE);
    }

    public static class ClauseFragment extends ActionBarFragment {

        @Override
        public String getTitle() {
            return getString(R.string.title_activity_clause);
        }
    }

    public static class PrivacyFragment extends ActionBarFragment {

        @Override
        public String getTitle() {
            return getString(R.string.title_activity_privacy);
        }
    }
}
