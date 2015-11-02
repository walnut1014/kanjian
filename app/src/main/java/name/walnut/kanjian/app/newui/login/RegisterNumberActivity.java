package name.walnut.kanjian.app.newui.login;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import name.walnut.kanjian.app.R;

/**
 * Created by linxunjian on 15/10/28.
 */
public class RegisterNumberActivity extends Activity implements View.OnClickListener{

    private EditText mEtPhoneNumber;
    private Button mBtnBack,mBtnNextStep;

    public void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_number);

        mEtPhoneNumber = (EditText)findViewById(R.id.et_phonenumber);
        mBtnBack = (Button)findViewById(R.id.btn_back);
        mBtnNextStep = (Button)findViewById(R.id.btn_next_step);

        mBtnBack.setOnClickListener(this);
        mBtnNextStep.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId())
        {
            case R.id.btn_next_step:
                if("".equals(mEtPhoneNumber.getText().toString()))
                {
                    Toast.makeText(RegisterNumberActivity.this,"请输入手机号码",Toast.LENGTH_SHORT).show();
                }else if(!isMobileNumber(mEtPhoneNumber.getText().toString())){
                    Toast.makeText(RegisterNumberActivity.this,"请输入正确的手机号码",Toast.LENGTH_SHORT).show();
                }else if(false)
                {
                    //和服务器通讯,如果号码已经注册,则跳到登录页面.
                }
                else{
                    Intent _intentRegisterPassword = new  Intent(RegisterNumberActivity.this,RegisterVerifyCodeActivity.class);
                    startActivity(_intentRegisterPassword);
                }
                break;
            case R.id.btn_back:
                onBackPressed();
                break;
            default:
                break;
        }
    }

    public boolean isMobileNumber(String mobiles){
        Pattern p = Pattern.compile("^((13[0-9])|(15[^4,\\D])|(18[0,5-9]))\\d{8}$");
        Matcher m = p.matcher(mobiles);
        return m.matches();
    }

}
