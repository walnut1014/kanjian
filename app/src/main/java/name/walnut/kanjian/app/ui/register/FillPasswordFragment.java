package name.walnut.kanjian.app.ui.register;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import name.walnut.kanjian.app.R;
import name.walnut.kanjian.app.support.ActionBarFragment;
import name.walnut.kanjian.app.ui.Constants;
import name.walnut.kanjian.app.ui.util.RegexUtils;
import name.walnut.kanjian.app.views.ClearEditText;

/**
 * 设置密码
 */
public class FillPasswordFragment extends ActionBarFragment{

    @InjectView(R.id.fill_password_edit)
    ClearEditText passwordEdit;
    @InjectView(R.id.fill_password_submit)
    Button submitBtn;

    private String token;

    public FillPasswordFragment(String token) {
        this.token = token;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_fill_password, container, false);
        ButterKnife.inject(this, view);
        passwordEdit.requestFocus();
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.reset(this);
    }

    @Override
    public String getTitle() {
        return getString(R.string.title_fill_password);
    }

    @OnClick(R.id.fill_password_submit)
    void submit() {
        String password = passwordEdit.getEditText().getText().toString();
        if (TextUtils.isEmpty(password)) {
            Toast.makeText(getActivity(), R.string.toast_empty_password, Toast.LENGTH_SHORT).show();

        } else if (!isPasswordAvailable(password)) {
            Toast.makeText(getActivity(), R.string.toast_error_format_password, Toast.LENGTH_SHORT).show();

        } else {
            // 跳转到完善资料界面
            switchFragment(new FillNicknameFragment(token, password));

        }
    }

    // 检查密码格式
    private boolean isPasswordAvailable(String password) {
        int length = password == null ? 0 : password.length();
        return RegexUtils.isNumAndLetter(password)
                && length >= Constants.Materials.PASSWORD_MIN_LENGTH
                && length <= Constants.Materials.PASSWORD_MAX_LENGTH;
    }
}