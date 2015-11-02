//package name.walnut.kanjian.app.support;
//
//import android.os.Bundle;
//import android.view.Gravity;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup.LayoutParams;
//import android.view.Window;
//import android.widget.PopupWindow;
//import android.widget.TextView;
//
//import name.walnut.kanjian.app.R;
//
//
//public abstract class ActionBarActivity extends BaseActivity {
//
//    public ActionBarActivity(int containerViewId) {
//        this.containerViewId = containerViewId;
//    }
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        requestWindowFeature(Window.FEATURE_ACTION_BAR);
//    }
//
//    @Override
//    public void onBackPressed() {
//        if (getFragmentManager().getBackStackEntryCount() <= 1) {
//            currentFragment = null;
//            finish();
//        } else {
//            currentFragment.onBack();
//        }
//    }
//
//    /**
//     * 切换Fragment
//     *
//     * @param actionBarFragment
//     *            fragment的类型
//     */
//    public void switchFragment(ActionBarFragment actionBarFragment) {
//
//        getFragmentManager().beginTransaction()
//                .replace(containerViewId, actionBarFragment)
//                .addToBackStack(null).commit();
//    }
//
//    protected void setCurrentFragment(ActionBarFragment currentFragment) {
//        this.currentFragment = currentFragment;
//    }
//
//    public void showMessage(String message) {
//        LayoutInflater inflater = getLayoutInflater();
//        final View vPopupWindow = inflater.inflate(R.layout.popup_message, null, false);
//        pw = new PopupWindow(vPopupWindow, LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT,true);
//
//        ((TextView) vPopupWindow.findViewById(R.id.message)).setText(message);
//        findViewById(containerViewId).post(new Runnable() {
//            @Override
//            public void run() {
//                pw.showAtLocation(findViewById(containerViewId), Gravity.CENTER, 0, 0);
//            }
//        });
//
//    }
//
//    public void dismissMessage() {
//        if (pw != null && pw.isShowing()) {
//            pw.dismiss();
//        }
//        pw = null;
//    }
//
//    private int containerViewId;
//
//    /** 提示框*/
//    private PopupWindow pw;
//
//    private ActionBarFragment currentFragment;
//}
