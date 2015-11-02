package name.walnut.kanjian.app.newui.login;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.Timer;
import java.util.TimerTask;

import name.walnut.kanjian.app.R;

/**
 * Created by linxunjian on 15/10/29.
 */
public class RegisterVerifyCodeActivity extends Activity implements View.OnClickListener{

    private Button mBtnSubmit,mBtnBack,mBtnCountDown;
    private EditText mEtVerifyCode;
    private Timer mTimer;
    private final int TIMER_TIME = 60;
    private int time =TIMER_TIME;

    public void onCreate(final Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_verifycode);

        mBtnBack = (Button)findViewById(R.id.btn_back);
        mBtnSubmit = (Button)findViewById(R.id.btn_verifycode_submit);
        mBtnCountDown = (Button)findViewById(R.id.btn_countdown);
        mEtVerifyCode = (EditText)findViewById(R.id.et_verifycode);
        mBtnCountDown.setOnClickListener(this);
        mBtnBack.setOnClickListener(this);
        mBtnSubmit.setOnClickListener(this);

        mBtnCountDown.setClickable(false);
        resetTimer();
    }

    private void resetTimer() {
        mTimer = new Timer();
        mTimer.schedule(new TimerTask() {
            @Override

            public void run() {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Log.i("RegisterVerifyCodeActivity",String.valueOf(time));
                        if (time >= 1) {
                            time--;
                            mBtnCountDown.setText(time + "S");
                            if (time ==0) {
                                mBtnCountDown.setText(getResources().getString(R.string.verifycode_resend));
                                mBtnCountDown.setClickable(true);
                                stopTimer();
                            }
                        }
                    }
                });
            }
        }, 0, 1000);
    }
    private void stopTimer()
    {
        if (mTimer!=null){
        mTimer.purge();
        mTimer.cancel();
        mTimer= null;}
    }

    @Override
    public void onClick(View view) {
        switch (view.getId())
        {
            case R.id.btn_back:
                stopTimer();
                onBackPressed();
                break;
            case R.id.btn_verifycode_submit:
                stopTimer();
                if("".equals(mEtVerifyCode.getText().toString()))
                {
                    Toast.makeText(RegisterVerifyCodeActivity.this, "请输验证码", Toast.LENGTH_SHORT).show();
                }else{
                    Intent _intentRegisterPassword = new Intent(RegisterVerifyCodeActivity.this,RegisterPasswordActivity.class);
                    startActivity(_intentRegisterPassword);
                    finish();
                }
                break;
            case R.id.btn_countdown:
                //向服务器发送重新获取验证码请求
                time=TIMER_TIME;
                mBtnCountDown.setClickable(false);
                break;
            default:
                break;
        }

    }
}
