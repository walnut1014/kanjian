package name.walnut.kanjian.app.ui.login;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import name.walnut.kanjian.app.R;
import name.walnut.kanjian.app.resource.impl.Resource;
import name.walnut.kanjian.app.resource.impl.ResourceWeave;
import name.walnut.kanjian.app.support.ActionBarFragment;
import name.walnut.kanjian.app.ui.Constants;
import name.walnut.kanjian.app.ui.login.action.LoginAction;
import name.walnut.kanjian.app.ui.util.RegexUtils;
import name.walnut.kanjian.app.views.ClearEditText;

public class LoginFragment extends ActionBarFragment implements Constants.Action{

    @InjectView(R.id.login)
    Button fragBtn;
    @InjectView(R.id.login_mobilephone)
    ClearEditText mobilephoneTv;
    @InjectView(R.id.login_password)
    ClearEditText passwordTv;
    @InjectView(R.id.register_tv)
    TextView registerTv;
    @InjectView(R.id.login_forget_password)
    TextView forgetPasswordTv;

    @ResourceWeave(actionClass=LoginAction.class)
    public Resource loginResource;

    @Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		View view = inflater.inflate(R.layout.fragment_login, container, false);

        ButterKnife.inject(this, view);
		return view;
	}

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.reset(this);
    }

    @Override
	public String getTitle() {
		return getResources().getString(R.string.text_login);
	}

    @OnClick(R.id.login)
    void login() {
        String phone = mobilephoneTv.getEditText().getText().toString();
        String password = passwordTv.getEditText().getText().toString();
        if (TextUtils.isEmpty(phone) && TextUtils.isEmpty(password)) {
            Toast.makeText(getActivity(), R.string.toast_login_empty_phone_and_password, Toast.LENGTH_SHORT).show();

        } else if (TextUtils.isEmpty(phone)) {
            Toast.makeText(getActivity(), R.string.toast_empty_phone, Toast.LENGTH_SHORT).show();

        } else if (TextUtils.isEmpty(password)) {
            Toast.makeText(getActivity(), R.string.toast_empty_password, Toast.LENGTH_SHORT).show();

        } else if (!isPhoneAvailable(phone)) {
            Toast.makeText(getActivity(), R.string.toast_login_error_format_phone, Toast.LENGTH_SHORT).show();

        } else if (!isPasswordAvailable(password)) {
            Toast.makeText(getActivity(), R.string.toast_error_format_password, Toast.LENGTH_SHORT).show();

        } else {
            //发送登陆请求
            loginResource.addParam("mobilephone", phone)
                    .addParam("password", password)
                    .send();
        }

    }

    // 检查手机号格式
    private boolean isPhoneAvailable(String phone) {
        return RegexUtils.isMobilePhone(phone);
    }
    // 检查密码格式
    private boolean isPasswordAvailable(String password) {
        int length = password == null ? 0 : password.length();
        return RegexUtils.isNumAndLetter(password)
                && length >= Constants.Materials.PASSWORD_MIN_LENGTH
                && length <= Constants.Materials.PASSWORD_MAX_LENGTH;
    }

    @OnClick(R.id.register_tv)
    void startRegister() {
        getActivity().finish();
        Intent intent = new Intent(REGISTER_ACTION);
        startActivity(intent);
    }

    @OnClick(R.id.login_forget_password)
    void forgetPassword() {
        getActivity().finish();
        Intent intent = new Intent(FORGET_PASSWORD_ACTION);
        startActivity(intent);
    }

}
