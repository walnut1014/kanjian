package name.walnut.kanjian.app.ui;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.widget.Button;
import android.widget.TextView;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import name.walnut.kanjian.app.R;

public class LaunchActivity extends Activity implements Constants.Action{

    @InjectView(R.id.btnRegister) Button registerButton;
    @InjectView(R.id.btnLogin) Button loginButton;
    @InjectView(R.id.launch_clause) TextView clauseTv;

    @Override
	protected void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
		this.setContentView(R.layout.activity_launch);

        ButterKnife.inject(this);

        clauseTv.setText(Html.fromHtml(getString(R.string.text_launch_clause)));
	}

    @OnClick(R.id.btnLogin)
    void login() {
        Intent intent = new Intent(LOGIN_ACTION);
        startActivity(intent);
    }

    @OnClick(R.id.btnRegister)
    void startRegister() {
        Intent intent = new Intent(REGISTER_ACTION);
        startActivity(intent);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ButterKnife.reset(this);
    }

}
