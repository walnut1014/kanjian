package name.walnut.kanjian.app.ui.login;

import android.app.Dialog;
import android.app.DialogFragment;
import android.app.FragmentManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import name.walnut.kanjian.app.R;
import name.walnut.kanjian.app.ui.Constants;

/**
 * 登录失败弹出框
 */
public class LoginAlertDialogFragment extends DialogFragment {

    private String content;

    @InjectView(R.id.positive_button)
    Button positiveBtn;
    @InjectView(R.id.negative_button)
    Button negativeBtn;
    @InjectView(R.id.content)
    TextView contentTv;

    public static LoginAlertDialogFragment showDialog(FragmentManager fragmentManager, String content) {
        LoginAlertDialogFragment fragment = new LoginAlertDialogFragment();
        fragment.content = content;
        fragment.show(fragmentManager, "dialog");
        return fragment;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        LayoutInflater inflater = (LayoutInflater)
                getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.dialog_login, null);

        ButterKnife.inject(this, view);

        contentTv.setText(content);

        Dialog dialog = new Dialog(getActivity(), R.style.LoginDialog);

        Window window = dialog.getWindow();
        window.requestFeature(Window.FEATURE_NO_TITLE);

        int padding = getResources().getDimensionPixelSize(R.dimen.dialog_login_alert_padding);

        window.getDecorView().setPadding(padding, 0, padding, 0);


        WindowManager.LayoutParams layoutParams = window.getAttributes();
        layoutParams.width = WindowManager.LayoutParams.MATCH_PARENT;
        layoutParams.height = WindowManager.LayoutParams.WRAP_CONTENT;
        window.setAttributes(layoutParams);

        dialog.setContentView(view);

        return dialog;
    }

    @OnClick(R.id.positive_button)
    void startRegister() {
        getActivity().finish();
        Intent intent = new Intent(Constants.Action.REGISTER_ACTION);
        startActivity(intent);
    }

    @OnClick(R.id.negative_button)
    void cancel() {
        dismiss();
    }
}
