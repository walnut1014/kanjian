package name.walnut.kanjian.app.newui.login;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import name.walnut.kanjian.app.R;

/**
 * Created by linxunjian on 15/11/4.
 */
public class LoginActivity extends Activity implements View.OnClickListener {

    private Button mBtnSelPortrait, mBtnLogin, mBtnBack;
    private EditText mEtNickname;

    public void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mBtnLogin = (Button) findViewById(R.id.btn_login);
        mBtnBack = (Button) findViewById(R.id.btn_back);

        mBtnLogin.setOnClickListener(this);
        mBtnBack.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {

    }
}
