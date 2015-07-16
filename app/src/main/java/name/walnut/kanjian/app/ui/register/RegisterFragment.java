package name.walnut.kanjian.app.ui.register;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import name.walnut.kanjian.app.R;
import name.walnut.kanjian.app.resource.impl.Resource;
import name.walnut.kanjian.app.resource.impl.ResourceWeave;
import name.walnut.kanjian.app.support.ActionBarFragment;
import name.walnut.kanjian.app.ui.Constants;
import name.walnut.kanjian.app.ui.register.action.RegisterSendAction;
import name.walnut.kanjian.app.ui.util.RegexUtils;
import name.walnut.kanjian.app.ui.util.ToastUtils;
import name.walnut.kanjian.app.views.ClearEditText;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

/**
 * 注册输入手机号码fragment
 * Intent extra:
 * mobilephone: 注册手机号
 */
public class RegisterFragment extends ActionBarFragment implements Constants.Action{

    @InjectView(R.id.register_mobilephone)
    ClearEditText mobilephoneTv;
    @InjectView(R.id.register)
    Button registerBtn;
    @InjectView(R.id.login_tv)
    TextView loginTv;

    @ResourceWeave(actionClass = RegisterSendAction.class)
    public Resource registerSendResource;  //注册发送手机验证码

    private String mobilephone;

    public String getMobilephone() {
        return mobilephone;
    }

    @Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		
		View view = inflater.inflate(R.layout.fragment_register, container, false);
        ButterKnife.inject(this, view);
		return view;
	}

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // 从注册最后一步回退时带有“mobilephone”信息
        Intent intent = getActionBarActivity().getIntent();
        String mobilephone = intent.getStringExtra("mobilephone");
        if (!TextUtils.isEmpty(mobilephone)) {
            mobilephoneTv.getEditText().setText(mobilephone);
            mobilephoneTv.getEditText().setSelection(mobilephone.length());
        }
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
		return getResources().getString(R.string.text_register);
	}

    @OnClick(R.id.register)
    void register() {
        String mobilephone = mobilephoneTv.getEditText().getText().toString();

        if (TextUtils.isEmpty(mobilephone)) {
            ToastUtils.toast(R.string.toast_register_empty_phone, Toast.LENGTH_SHORT);

        } else if (!isMobilePhoneAvailable(mobilephone)){
            ToastUtils.toast(R.string.toast_register_error_format_phone, Toast.LENGTH_SHORT);

        } else {
            this.mobilephone = mobilephone;

            showMessage(R.string.dialog_message_register);

            registerSendResource.addParam("mobilephone", mobilephone)
                    .send();

        }
    }

    private boolean isMobilePhoneAvailable(String mobilephone) {
        return RegexUtils.isMobilePhone(mobilephone);
    }

    @OnClick(R.id.login_tv)
    void startLogin() {
        getActivity().finish();
        Intent intent = new Intent(LOGIN_ACTION);
        startActivity(intent);
    }

}
