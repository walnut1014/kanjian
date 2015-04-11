package name.walnut.kanjian.app.ui.password;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.Html;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import name.walnut.kanjian.app.R;
import name.walnut.kanjian.app.resource.impl.Resource;
import name.walnut.kanjian.app.resource.impl.ResourceWeave;
import name.walnut.kanjian.app.support.ActionBarFragment;
import name.walnut.kanjian.app.support.SMSController;
import name.walnut.kanjian.app.ui.password.action.ForgetVerifyCodeAction;
import name.walnut.kanjian.app.ui.password.action.ResendVerifyCodeAction;
import name.walnut.kanjian.app.utils.Logger;
import name.walnut.kanjian.app.views.ClearEditText;

/**
 * 手机验证码Fragment
 * @author walnut
 *
 */
@SuppressLint("ValidFragment")
public class VerifyCodeFragment extends ActionBarFragment {

    static final int COUNTDOWN_MILL = 60 * 1000; // 60秒倒计时
    static final int COUNTDOWN_INTERVAL = 1 * 1000;  // 1秒时间间隔

    private CharSequence mobilephone;
    private String verifyCode;

    @InjectView(R.id.verifycode_countdown)
    TextView countdownTv;
    @InjectView(R.id.verifycode_edit)
    ClearEditText verifycodeEdit;
    @InjectView(R.id.verifycode_submit)
    Button submitBtn;
    @InjectView(R.id.verifycode_tip)
    TextView verifycodeTipTv;
    @InjectView(R.id.verifycode_mobilephone)
    TextView mobilephoneTv;

    private CountDownTimer countDownTimer;  // 倒计时

    @ResourceWeave(actionClass = ForgetVerifyCodeAction.class)
    public Resource smsValidateResource;

    public VerifyCodeFragment(CharSequence mobilephone) {
        this.mobilephone = mobilephone;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_verifycode, container, false);
        ButterKnife.inject(this, view);

        verifycodeTipTv.setText(Html.fromHtml(getString(R.string.verifycode_tip)));
        countdownTv.setClickable(false);
        mobilephoneTv.setText("+86  " + mobilephone);
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        countDownTimer = new CountDownTimer(COUNTDOWN_MILL, COUNTDOWN_INTERVAL) {
            @Override
            public void onTick(long millisUntilFinished) {
                if (countdownTv != null && !VerifyCodeFragment.this.isDetached()) {
                    countdownTv.setText(
                            Html.fromHtml(
                                    getString(R.string.verifycode_countdown, millisUntilFinished / 1000)));
                }
            }

            @Override
            public void onFinish() {
                stopCountdown();
            }
        };
        startCountdown();
    }

    // 开始计时
    public void startCountdown() {
        countdownTv.setClickable(false);
        countdownTv.setTextColor(getResources().getColor(R.color.text_gray));
        countdownTv.setText(Html.fromHtml(getString(R.string.verifycode_countdown, COUNTDOWN_MILL)));
        countDownTimer.start();
    }

    // 停止计时
    public void stopCountdown() {
        countdownTv.setText(getString(R.string.verifycode_resend));
        countdownTv.setTextColor(getResources().getColor(R.color.text_purple));
        countdownTv.setClickable(true);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.reset(this);
        if (countDownTimer != null) {
            countDownTimer.cancel();
        }
    }

    @Override
    public String getTitle() {
        return getString(R.string.title_verifycode);
    }

    @OnClick(R.id.verifycode_submit)
    void verifyCode() {
        String verifyCode = verifycodeEdit.getEditText().getText().toString();
        if (TextUtils.isEmpty(verifyCode)) {
            Toast.makeText(getActivity(), R.string.toast_verify_empty, Toast.LENGTH_SHORT).show();

        } else {
            this.verifyCode = verifyCode;
            // 提交验证码验证
            // 提交验证码验证
            smsValidateResource.addParam("code", verifyCode)
                    .addParam("phone", mobilephone)
                    .addParam("zone", "86")
                    .send();
        }
    }

    @OnClick(R.id.verifycode_countdown)
    void resendVerifyCode() {
        // 重新获取验证码
        SMSController.getChinaVerificationCode(mobilephone.toString());
        startCountdown();
    }


    public CharSequence getMobilephone() {
        return mobilephone;
    }

    public String getVerifyCode() {
        return verifyCode;
    }
}
