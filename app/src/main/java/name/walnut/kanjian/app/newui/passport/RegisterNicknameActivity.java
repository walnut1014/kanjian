package name.walnut.kanjian.app.newui.passport;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import name.walnut.kanjian.app.R;

/**
 * Created by linxunjian on 15/10/31.
 */
public class RegisterNicknameActivity extends Activity{

    private Button mBtnSelPortrait, mBtnSubmit, mBtnBack;
    private EditText mEtNickname;

    public void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_nickname);

        mBtnSelPortrait = (Button) findViewById(R.id.btn_sel_portrait);
        mBtnSubmit = (Button) findViewById(R.id.btn_submit);
        mEtNickname = (EditText) findViewById(R.id.et_nickname);
        mBtnBack = (Button) findViewById(R.id.btn_back);
    }

    /**
     * 单击返回按钮
     *
     * @param view
     */
    public void onClickSelectPortrait(View view) {
        startActivity(new Intent(RegisterNicknameActivity.this, SelectPortraitActivity.class));

    }


    /**
     * 单击返回按钮
     *
     * @param view
     */
    public void onClickBack(View view) {
        onBackPressed();
    }

    /**
     * 单击提交按钮
     *
     * @param view
     */
    public void onClickSubmit(View view) {


    }


}