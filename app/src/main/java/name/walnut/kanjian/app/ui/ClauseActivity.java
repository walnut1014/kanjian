//package name.walnut.kanjian.app.ui;
//
//import android.os.Bundle;
//
//import name.walnut.kanjian.app.R;
//import name.walnut.kanjian.app.support.ActionBarActivity;
//import name.walnut.kanjian.app.support.ActionBarBuilder;
//import name.walnut.kanjian.app.support.ActionBarFragment;
//
//public class ClauseActivity extends ActionBarActivity implements Constants.Action{
//
//    public ClauseActivity() {
//        super(R.id.container);
//    }
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_clause);
//
//        String action = getIntent().getAction();
//        if (PRIVACY_ACTION.equals(action)) {
//            switchFragment(new PrivacyFragment());
//        } else if (CLAUSE_ACTION.equals(action)){
//            switchFragment(new ClauseFragment());
//        }
//
//    }
//
//    public static class ClauseFragment extends ActionBarFragment {
//
//        @Override
//        public String getTitle() {
//            return getString(R.string.title_activity_clause);
//        }
//
//        @Override
//        protected ActionBarBuilder.BackStyle getActionBarBackStyle() {
//            return ActionBarBuilder.BackStyle.TEXT;
//        }
//
//        @Override
//        public boolean isVerifyAuth() {
//            return false;
//        }
//    }
//
//    public static class PrivacyFragment extends ActionBarFragment {
//
//        @Override
//        public String getTitle() {
//            return getString(R.string.title_activity_privacy);
//        }
//
//        @Override
//        protected ActionBarBuilder.BackStyle getActionBarBackStyle() {
//            return ActionBarBuilder.BackStyle.TEXT;
//        }
//
//        @Override
//        public boolean isVerifyAuth() {
//            return false;
//        }
//    }
//}
