package name.walnut.kanjian.app.ui.password;

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
import name.walnut.kanjian.app.support.SMSController;
import name.walnut.kanjian.app.ui.password.action.ForgetPasswordSendAction;
import name.walnut.kanjian.app.ui.util.RegexUtils;
import name.walnut.kanjian.app.views.ClearEditText;

/**
 * 忘记密码输入手机号
 */
public class ForgotPasswordFragment extends ActionBarFragment{

    @InjectView(R.id.forget_password_mobilephone)
    ClearEditText mobilephoneTv;
    @InjectView(R.id.forget_password_submit)
    Button submitBtn;

    @ResourceWeave(actionClass=ForgetPasswordSendAction.class)
    public Resource forgetPasswordSendResource;  //注册发送手机验证码

    private String mobilephone;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_forget_password, container, false);
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
        return getResources().getString(R.string.title_forget_password);
    }

    @OnClick(R.id.forget_password_submit)
    void forgetPasswordSubmit() {
        // 提交手机号
        mobilephone = mobilephoneTv.getEditText().getText().toString();

        if (TextUtils.isEmpty(mobilephone)) {
            Toast.makeText(getActivity(), R.string.toast_register_empty_phone, Toast.LENGTH_SHORT).show();

        } else if (!isMobilePhoneAvailable(mobilephone)){
            Toast.makeText(getActivity(), R.string.toast_register_error_format_phone, Toast.LENGTH_SHORT).show();

        } else {
            // 发送验证码，跳转页面
            switchFragment(new VerifyCodeFragment(mobilephone));
            SMSController.getChinaVerificationCode(mobilephone);

//            forgetPasswordSendResource.addParam("mobilephone", mobilephone)
//                    .send();

        }
    }

    private boolean isMobilePhoneAvailable(String mobilephone) {
        return RegexUtils.isMobilePhone(mobilephone);
    }

    public String getMobilephone() {
        return mobilephone;
    }
}
