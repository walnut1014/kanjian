package name.walnut.kanjian.app.ui.register;

import android.app.FragmentManager;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import name.walnut.kanjian.app.R;
import name.walnut.kanjian.app.support.ActionBarFragment;
import name.walnut.kanjian.app.ui.Constants;
import name.walnut.kanjian.app.ui.util.RegexUtils;
import name.walnut.kanjian.app.ui.util.ToastUtils;
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

    public FillPasswordFragment() {}

    public static FillPasswordFragment newInstance(String token) {
        Bundle bundle = new Bundle();
        bundle.putString("token", token);
        FillPasswordFragment fragment = new FillPasswordFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    protected void onBack() {
//        super.onBack();
        // 直接返回输入手机号界面
        FragmentManager manager = getFragmentManager();
        while (manager.getBackStackEntryCount() > 1) {
            manager.popBackStackImmediate();
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        token = getArguments().getString("token");
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
    public boolean isVerifyAuth() {
        return false;
    }

    @Override
    public String getTitle() {
        return getString(R.string.title_fill_password);
    }

    @OnClick(R.id.fill_password_submit)
    void submit() {
        String password = passwordEdit.getEditText().getText().toString();
        if (TextUtils.isEmpty(password)) {
            ToastUtils.toast(R.string.toast_empty_password);

        } else if (!isPasswordAvailable(password)) {
            ToastUtils.toast(R.string.toast_error_format_password);

        } else {
            // 跳转到完善资料界面
            switchFragment(FillNicknameFragment.newInstance(token, password));

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
