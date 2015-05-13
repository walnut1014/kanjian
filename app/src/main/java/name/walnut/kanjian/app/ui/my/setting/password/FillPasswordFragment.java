package name.walnut.kanjian.app.ui.my.setting.password;

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
import name.walnut.kanjian.app.resource.impl.Resource;
import name.walnut.kanjian.app.resource.impl.ResourceWeave;
import name.walnut.kanjian.app.support.ActionBarFragment;
import name.walnut.kanjian.app.ui.Constants;
import name.walnut.kanjian.app.ui.my.setting.password.action.ResetPasswordAction;
import name.walnut.kanjian.app.ui.util.RegexUtils;
import name.walnut.kanjian.app.ui.util.ToastUtils;
import name.walnut.kanjian.app.views.ClearEditText;

/**
 * 设置密码
 */
public class FillPasswordFragment extends ActionBarFragment{

    private String token;

    @InjectView(R.id.fill_password_edit)
    ClearEditText passwordEdit;
    @InjectView(R.id.fill_password_submit)
    Button submitBtn;

    @ResourceWeave(actionClass = ResetPasswordAction.class)
    public Resource forgetPasswordResetResource;


    public FillPasswordFragment() {}

    public static FillPasswordFragment newInstance(String token) {
        Bundle bundle = new Bundle();
        bundle.putString("token", token);
        FillPasswordFragment fragment = new FillPasswordFragment();
        fragment.setArguments(bundle);
        return fragment;
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
    public String getTitle() {
        return getString(R.string.title_fill_password);
    }

    @OnClick(R.id.fill_password_submit)
    void submit() {
        String password = passwordEdit.getEditText().getText().toString();
        if (TextUtils.isEmpty(password)) {
            ToastUtils.toast(R.string.toast_empty_password, Toast.LENGTH_SHORT);

        } else if (!isPasswordAvailable(password)) {
            ToastUtils.toast(R.string.toast_error_format_password, Toast.LENGTH_SHORT);

        } else {
            showMessage(R.string.dialog_message_reset_password);

            forgetPasswordResetResource.addParam("password", password)
                    .addParam("token", token)
                    .send();
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