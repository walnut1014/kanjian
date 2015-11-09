package name.walnut.kanjian.app.newui.login;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import name.walnut.kanjian.app.R;
import name.walnut.kanjian.app.newui.utils.Constants;

/**
 * Created by linxunjian on 15/10/27.
 */
public class LoginOrRegisterActivity extends Activity implements View.OnClickListener {

    private Button mBtnRegister,mBtnLogin;

    public void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_or_register);

        mBtnRegister = (Button)findViewById(R.id.btn_register);
        mBtnLogin = (Button)findViewById(R.id.btn_login);
        mBtnRegister.setOnClickListener(this);
        mBtnLogin.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId())
        {
            case R.id.btn_register:
                Intent _intent_register = new Intent();
                _intent_register.setAction(Constants.Action.REGISTER_ACTION);
                startActivity(_intent_register);
                break;
            case R.id.btn_login:
                Intent _intent_login = new Intent(LoginOrRegisterActivity.this,LoginActivity.class);
                startActivity(_intent_login);
                break;
            default:
                break;
        }
    }
}

