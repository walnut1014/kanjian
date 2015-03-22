package name.walnut.kanjian.app.ui;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.text.style.URLSpan;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import name.walnut.kanjian.app.R;

public class LaunchActivity extends Activity implements Constants.Action{

    @InjectView(R.id.btnRegister) Button registerButton;
    @InjectView(R.id.btnLogin) Button loginButton;
    @InjectView(R.id.launch_clause) TextView clauseTv;

    @Override
	protected void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
		this.setContentView(R.layout.activity_launch);

        ButterKnife.inject(this);

        clauseTv.setMovementMethod(LinkMovementMethod.getInstance());
        CharSequence clauseStr = clauseTv.getText();
//        clauseTv.setText(clauseStr);

        int end = clauseStr.length();
        Spannable spannable = (Spannable) clauseTv.getText();
        URLSpan[] urls = spannable.getSpans(0, end, URLSpan.class);
        SpannableStringBuilder builder = new SpannableStringBuilder(clauseStr);
        builder.clearSpans();
        for (URLSpan url : urls) {
            builder.setSpan(new ClickableSpan() {
                @Override
                public void onClick(View widget) {
                    Intent intent = new Intent(CLAUSE_ACTION);
                    startActivity(intent);
//                    Toast.makeText(LaunchActivity.this, "点击链接", Toast.LENGTH_SHORT).show();
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
