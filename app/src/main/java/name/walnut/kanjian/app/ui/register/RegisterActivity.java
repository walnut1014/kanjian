package name.walnut.kanjian.app.ui.register;

import name.walnut.kanjian.app.R;
import name.walnut.kanjian.app.account.Account;
import name.walnut.kanjian.app.support.ActionBarActivity;
import android.os.Bundle;

public class RegisterActivity extends ActionBarActivity {

	public RegisterActivity() {
		super(R.id.register_container);
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.setContentView(R.layout.activity_register);

        switchFragment(new RegisterFragment());
	}

    public void onRegisterSuccess() {
        // 缓存用户信息到本地
        Account.INSTANCE.setAccount(nickName, avatar, mobilephone);
    }


    private String mobilephone; // 注册手机号
    private String nickName;    // 昵称
    private String avatar;      // 头像

    public String getMobilephone() {
        return mobilephone;
    }

    public void setMobilephone(String mobilephone) {
        this.mobilephone = mobilephone;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }
}
