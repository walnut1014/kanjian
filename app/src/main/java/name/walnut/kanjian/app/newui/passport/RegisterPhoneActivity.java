package name.walnut.kanjian.app.newui.passport;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.ta.utdid2.android.utils.StringUtils;

import name.walnut.kanjian.app.R;
import name.walnut.kanjian.app.ui.util.RegexUtils;
import name.walnut.kanjian.app.utils.ActivityUtils;

/**
 * Created by linxunjian on 15/10/28.
 */
public class RegisterPhoneActivity extends Activity{


    public void onCreate(final Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_phone);
        phoneText = (EditText) findViewById(R.id.edt_register_phone);
    }

    /**
     * 单击返回按钮
     * @param view
     */
    public void onClickBack(View view) {
        onBackPressed();
    }

    /**
     * 单击下一步按钮
     * @param view
     */
    public void onClickNext(View view) {

        final String phone = phoneText.getText().toString();
        if(StringUtils.isEmpty(phone)) {
            ActivityUtils.showError(this, "请输入手机号码");
            return;
        }
        if(!RegexUtils.isMobilePhone(phone)) {
            ActivityUtils.showError(this, "请输入正确的手机号码");
            return;
        }

        //SMSController.getChinaVerificationCode(phone); //发送短信

        startActivity(new Intent(RegisterPhoneActivity.this, RegisterVerifyCodeActivity.class).putExtra("phone", phone));
    }

    private EditText phoneText;

}
