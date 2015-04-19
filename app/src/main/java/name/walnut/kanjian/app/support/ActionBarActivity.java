package name.walnut.kanjian.app.support;

import android.app.ActionBar;
import android.app.Activity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.view.Window;
import android.widget.ImageButton;
import android.widget.PopupWindow;
import android.widget.TextView;

import name.walnut.kanjian.app.R;


public abstract class ActionBarActivity extends Activity {

    public ActionBarActivity(int containerViewId) {
        this.containerViewId = containerViewId;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_ACTION_BAR);
        actionBar = getActionBar();

        ActionBar.LayoutParams lp = new ActionBar.LayoutParams(
                ActionBar.LayoutParams.MATCH_PARENT,
                ActionBar.LayoutParams.MATCH_PARENT, Gravity.CENTER);

        View viewTitleBar = getLayoutInflater().inflate(
                R.layout.action_bar_title, null);
        actionBar.setCustomView(viewTitleBar, lp);
        actionBar.setDisplayShowHomeEnabled(false);
        actionBar.setDisplayShowTitleEnabled(false);
        actionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        actionBar.setDisplayShowCustomEnabled(true);

        backButton = (ImageButton) actionBar.getCustomView().findViewById(
                R.id.btnActionBack);
        menuTextButton = (TextView) actionBar.getCustomView().findViewById(
                R.id.action_back_text);


        backButton.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        menuTextButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    @Override
    public void onBackPressed() {
        if (getFragmentManager().getBackStackEntryCount() <= 1) {
            currentFragment = null;
            finish();
        } else {
            currentFragment.onBack();
        }
    }

    /**
     * 切换Fragment
     *
     * @param actionBarFragment
     *            fragment的类型
     */
    public void switchFragment(ActionBarFragment actionBarFragment) {

        getFragmentManager().beginTransaction()
                .replace(containerViewId, actionBarFragment)
                .addToBackStack(null).commit();
    }

    protected void setCurrentFragment(ActionBarFragment currentFragment) {
        this.currentFragment = currentFragment;
    }

    public void showMessage(String message) {
        LayoutInflater inflater = getLayoutInflater();
        final View vPopupWindow=inflater.inflate(R.layout.popup_message, null, false);
        pw= new PopupWindow(vPopupWindow, LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT,true);

        ((TextView) vPopupWindow.findViewById(R.id.message)).setText(message);
        pw.showAtLocation(findViewById(containerViewId), Gravity.CENTER, 0, 0);

    }

    public void dismissMessage() {
        if (pw != null && pw.isShowing()) {
            pw.dismiss();
        }
        pw = null;
    }

    private int containerViewId;
    protected ActionBar actionBar;
    protected ImageButton backButton;
    protected TextView menuTextButton;  // actionbar右上文字按钮

    /** 提示框*/
    private PopupWindow pw;

    private ActionBarFragment currentFragment;
}
