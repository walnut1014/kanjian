package name.walnut.kanjian.app.ui;

import android.content.Intent;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.text.style.URLSpan;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import name.walnut.kanjian.app.R;
import name.walnut.kanjian.app.account.Account;
import name.walnut.kanjian.app.push.PushReceiver;
import name.walnut.kanjian.app.support.BaseActivity;

public class LaunchActivity extends BaseActivity implements Constants.Action{

    @InjectView(R.id.btnRegister) Button registerButton;
    @InjectView(R.id.btnLogin) Button loginButton;
    @InjectView(R.id.launch_clause) TextView clauseTv;

    @Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

        if (Account.INSTANCE.isAvailable()) {
            finish();
            Intent intent = new Intent(Constants.Action.MAIN_ACTION);
            startActivity(intent);
            overridePendingTransition(0, 0);
            return;
        }

        /**
         * TODO
         * 开启客户端推送服务，不要在Application onCreate() 中调用 mPushAgent.enable();
         * 由于SDK 设计的逻辑， 这会造成循环。
         */
        PushReceiver.INSTANCE.getPushAgent().enable();

		this.setContentView(R.layout.activity_launch);

        ButterKnife.inject(this);

        clauseTv.setMovementMethod(LinkMovementMethod.getInstance());
        CharSequence clauseStr = clauseTv.getText();

        int end = clauseStr.length();
        Spannable spannable = (Spannable) clauseTv.getText();
        URLSpan[] urls = spannable.getSpans(0, end, URLSpan.class);
        SpannableStringBuilder builder = new SpannableStringBuilder(clauseStr);
        builder.clearSpans();
        for (final URLSpan url : urls) {
            builder.setSpan(new ClickableSpan() {
                @Override
                public void onClick(View widget) {
                    String urlStr = url.getURL();
                    if ("clause_use".equals(urlStr)) {
                        Intent intent = new Intent(CLAUSE_ACTION);
                        startActivity(intent);
                    } else if ("privacy".equals(urlStr)) {
                        Intent intent = new Intent(PRIVACY_ACTION);
                        startActivity(intent);
                    }
                }
            }, spannable.getSpanStart(url), spannable.getSpanEnd(url), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        }
        clauseTv.setText(builder);
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
