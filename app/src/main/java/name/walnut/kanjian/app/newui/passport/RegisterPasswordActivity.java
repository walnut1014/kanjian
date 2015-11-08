package name.walnut.kanjian.app.newui.passport;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import name.walnut.kanjian.app.R;

/**
 * Created by linxunjian on 15/10/31.
 */
public class RegisterPasswordActivity extends Activity implements View.OnClickListener{

    private Button mBtnSubmit;
    private EditText mEtPassword;

    public void onCreate(final Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_password);

        mBtnSubmit = (Button)findViewById(R.id.btn_password_submit);
        mBtnSubmit.setOnClickListener(this);
        mEtPassword = (EditText)findViewById(R.id.et_password);

    }

    @Override
    public void onClick(View view) {
        switch(view.getId())
        {
            case R.id.btn_password_submit:
                if("".equals(mEtPassword.getText().toString()))
                {
                    Toast.makeText(RegisterPasswordActivity.this, "请输入密码", Toast.LENGTH_SHORT).show();
                }else
                {
                    Intent _intentRegisterNickName = new Intent(RegisterPasswordActivity.this,RegisterNicknameActivity.class);
                    startActivity(_intentRegisterNickName);
                }
                break;
            default:
                break;
        }

    }
}
