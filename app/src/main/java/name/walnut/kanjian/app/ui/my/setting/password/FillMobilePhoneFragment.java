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
import name.walnut.kanjian.app.ui.my.setting.password.action.CurrMobilePhoneAction;
import name.walnut.kanjian.app.ui.util.RegexUtils;
import name.walnut.kanjian.app.ui.util.ToastUtils;
import name.walnut.kanjian.app.views.ClearEditText;

/**
 * 填写手机号fragment
 */
public class FillMobilePhoneFragment extends ActionBarFragment{

    @InjectView(R.id.forget_password_mobilephone)
    ClearEditText mobilephoneTv;
    @InjectView(R.id.forget_password_submit)
    Button submitBtn;

    @ResourceWeave(actionClass=CurrMobilePhoneAction.class)
    public Resource isCurrMobilephoneResource;  //注册发送手机验证码

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
        return getString(R.string.title_activity_fill_phone);
    }

    @OnClick(R.id.forget_password_submit)
    void forgetPasswordSubmit() {
        // 提交手机号
        mobilephone = mobilephoneTv.getEditText().getText().toString();

        if (TextUtils.isEmpty(mobilephone)) {
            ToastUtils.toast(R.string.toast_register_empty_phone, Toast.LENGTH_SHORT);

        } else if (!isMobilePhoneAvailable(mobilephone)){
            ToastUtils.toast(R.string.toast_register_error_format_phone, Toast.LENGTH_SHORT);

        } else {
            showMessage(R.string.dialog_message_forgot_password);
            // TODO 验证手机号码是否注册

            isCurrMobilephoneResource.addParam("mobilephone", mobilephone)
                    .send();

        }
    }

    private boolean isMobilePhoneAvailable(String mobilephone) {
        return RegexUtils.isMobilePhone(mobilephone);
    }

    public String getMobilephone() {
        return mobilephone;
    }
}