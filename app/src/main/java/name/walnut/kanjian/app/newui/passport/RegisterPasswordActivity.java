package name.walnut.kanjian.app.newui.passport;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import name.walnut.kanjian.app.R;
import name.walnut.kanjian.app.utils.ActivityUtils;

/**
 * Created by linxunjian on 15/10/31.
 */
public class RegisterPasswordActivity extends Activity {

    private EditText mEtPassword;

    public void onCreate(final Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_password);

        mEtPassword = (EditText)findViewById(R.id.et_password);
    }

    /**
     * 单击提交按钮方法
     * @param view
     */
    public void btnSubmitClick(View view) {

        final String password = mEtPassword.getText().toString();
        if("".equals(password)) {
            ActivityUtils.showError(this, "请输入密码");
        }else {
            startActivity(new Intent(this, RegisterNicknameActivity.class)
                            .putExtra("password", password));
        }
    }
}
