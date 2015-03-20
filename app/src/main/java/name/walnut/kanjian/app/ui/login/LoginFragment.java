package name.walnut.kanjian.app.ui.login;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import name.walnut.kanjian.app.R;
import name.walnut.kanjian.app.resource.impl.Resource;
import name.walnut.kanjian.app.resource.impl.ResourceWeave;
import name.walnut.kanjian.app.service.LoginService;
import name.walnut.kanjian.app.support.ActionBarFragment;
import name.walnut.kanjian.app.ui.login.action.LoginAction;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.nio.Buffer;

public class LoginFragment extends ActionBarFragment {

    @InjectView(R.id.btnFragLogin) Button fragBtn;
    @InjectView(R.id.login_txtMobilephone) TextView txtMobilephone;
    @InjectView(R.id.login_txtPassword) TextView txtPassword;

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

    @OnClick(R.id.btnFragLogin)
    void login() {
        //发送登陆请求
        loginResource.addParam("mobilephone","13000000001")
                .addParam("password", "sdfsdf").send();
    }

}
