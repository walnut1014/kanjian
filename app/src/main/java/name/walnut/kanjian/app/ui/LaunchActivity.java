package name.walnut.kanjian.app.ui;

import name.walnut.kanjian.app.R;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.GestureDetector;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class LaunchActivity extends Activity implements OnClickListener {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
		this.setContentView(R.layout.activity_launch);
		
		registerButton = (Button) findViewById(R.id.btnRegister);
		loginButton = (Button) findViewById(R.id.btnLogin);
		
		registerButton.setOnClickListener(this);
		loginButton.setOnClickListener(this);

        findViewById(R.id.textView1).setLongClickable(true);
        findViewById(R.id.textView1).setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                Intent intent = new Intent("test_activity");
                LaunchActivity.this.startActivity(intent);
                return true;
            }
        });

	}
	

	@Override
	public void onClick(View v) {
		
		Intent intent = null;
		switch (v.getId()) {
		case R.id.btnRegister:
			intent = new Intent(REGISTER_ACTION);
			startActivity(intent);
			break;		
		case R.id.btnLogin:
			intent = new Intent(LOGIN_ACTION);
			startActivity(intent);
			break;
		default:
			break;
		}
	}
	
	private Button registerButton, loginButton;
	
	private final static String REGISTER_ACTION = "kanjian.intent.action.REGISTER";
	
	private final static String LOGIN_ACTION = "kanjian.intent.action.LOGIN";
	
}
