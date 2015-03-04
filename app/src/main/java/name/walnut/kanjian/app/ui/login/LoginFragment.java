package name.walnut.kanjian.app.ui.login;

import name.walnut.kanjian.app.R;
import name.walnut.kanjian.app.service.LoginService;
import name.walnut.kanjian.app.service.impl.LoginServiceImpl;
import name.walnut.kanjian.app.support.ActionBarFragment;
import name.walnut.kanjian.app.support.KanJianApplication;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

public class LoginFragment extends ActionBarFragment implements OnClickListener {

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		
		View view = inflater.inflate(R.layout.fragment_login, container, false);
		
		Button button = (Button)view.findViewById(R.id.btnFragLogin);
		txtMobilephone = (TextView) view.findViewById(R.id.login_txtMobilephone);
		txtPassword = (TextView) view.findViewById(R.id.login_txtPassword);
		
		button.setOnClickListener(this);
		
		loginService = new LoginServiceImpl(this.getActionBarActivity());
		
		return view;
	}

	@Override
	public String getTitle() {
		return getResources().getString(R.string.text_login);
	}

	@Override
	public void onClick(View v) {
		
		switch(v.getId()){
		case R.id.btnFragLogin:
			loginService.login(txtMobilephone.getText().toString(), 
								txtPassword.getText().toString());
		}
	}
	
	private LoginService loginService;
	
	private TextView txtMobilephone;
	private TextView txtPassword;
	
	
}
