package name.walnut.kanjian.app.ui.register;

import name.walnut.kanjian.app.R;
import name.walnut.kanjian.app.service.RegisterService;
import name.walnut.kanjian.app.service.ServiceException;
import name.walnut.kanjian.app.service.impl.RegisterServiceImpl;
import name.walnut.kanjian.app.support.ActionBarFragment;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

public class RegisterFragment extends ActionBarFragment implements OnClickListener {

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		
		View view = inflater.inflate(R.layout.fragment_register, container, false);
		registerButton = (Button)view.findViewById(R.id.btnFragRegister);
		mobilephoneText = (TextView) view.findViewById(R.id.register_txtMobilephone);
		
		registerButton.setOnClickListener(this);
		
		registerButton.setClickable(false);
		
		mobilephoneText.addTextChangedListener(new TextWatcher() {
			
			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
				if(count > 0)
					registerButton.setClickable(true);
				else
					registerButton.setClickable(false);
			}
			
			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void afterTextChanged(Editable s) {
				
			}
		});
		
		return view;
	}
	
	@Override
	public void onClick(View v) {
		switch(v.getId()){
		case R.id.btnFragRegister:
			
			String mobilephone = mobilephoneText.getText().toString();
			try {
				registerService.sendVeriCode(mobilephone);
			} catch (ServiceException e) {
				this.getActionBarActivity().showMessage(e.getMessage());
			}
			switchFragment(new VerifiCodeFragment(mobilephone));
		break;
		}
	}
	
	@Override
	public void onResume() {
		super.onResume();
		mobilephoneText.setText(null);
	}


	@Override
	public String getTitle() {
		return getResources().getString(R.string.text_register);
	}
	
	private Button registerButton;
	
	private TextView mobilephoneText;
	
	private RegisterService registerService = new RegisterServiceImpl();
	
}
