package name.walnut.kanjian.app.ui.my.setting;

import android.app.FragmentManager;
import android.widget.Button;

import butterknife.InjectView;
import butterknife.OnClick;
import name.walnut.kanjian.app.R;
import name.walnut.kanjian.app.ui.common.BaseBottomMenuDialogFragment;

/**
 * 退出登录dialog
 */
public class LogoutDialogFragment extends BaseBottomMenuDialogFragment{

    @InjectView(R.id.dialog_cancel)
    Button cancelBtn;
    @InjectView(R.id.dialog_logout)
    Button logoutBtn;

    private LogoutClickListener listener;

    @Override
    protected int getLayoutResId() {
        return R.layout.dialog_logout;
    }

    public static LogoutDialogFragment showDialog(FragmentManager fragmentManager, LogoutClickListener listener) {
        // 显示选择框
        LogoutDialogFragment dialog = new LogoutDialogFragment();
        dialog.show(fragmentManager, "dialog");
        dialog.listener = listener;
        return dialog;
    }

    @OnClick(R.id.dialog_logout)
    void logout() {
        // 退出登录
        if (listener != null) {
            listener.onLogout();
        }
        dismissDialog();
    }

    @OnClick(R.id.dialog_cancel)
    public void dismissDialog() {
        dismiss();
    }

    /**
     * 退出按钮选择listener
     */
    public interface LogoutClickListener {
        public void onLogout();
    }
}
