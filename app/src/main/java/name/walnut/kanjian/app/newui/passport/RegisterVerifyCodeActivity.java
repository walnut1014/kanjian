package name.walnut.kanjian.app.newui.passport;

import android.app.Activity;
import android.content.Intent;
import android.databinding.BaseObservable;
import android.databinding.Bindable;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import java.util.Timer;
import java.util.TimerTask;

import name.walnut.kanjian.app.BR;
import name.walnut.kanjian.app.R;
import name.walnut.kanjian.app.databinding.ActivityRegisterVerifyCodeBinding;
import name.walnut.kanjian.app.resource.ResourceFactory;
import name.walnut.kanjian.app.resource.VerificationResource;
import name.walnut.kanjian.app.resource.support.DefaultCallback;
import name.walnut.kanjian.app.resource.support.ResourceResult;
import name.walnut.kanjian.app.utils.ActivityUtils;

/**
 * Created by linxunjian on 15/10/29.
 */
public class RegisterVerifyCodeActivity extends Activity{

    public void onCreate(final Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        ActivityRegisterVerifyCodeBinding binding =
                DataBindingUtil.setContentView(this, R.layout.activity_register_verify_code);

        codeText = (EditText) this.findViewById(R.id.et_verifycode);

        vm = new VM(this);
        binding.setVm(vm);

        vm.startTimer(); //启动短信发送倒计时
    }


    @Override
    public void onBackPressed() {
        this.startActivity(new Intent(this, RegisterPhoneActivity.class));
    }

    /**
     * 单击回退按钮
     */
    public void btnBackClick(View view) {
        this.onBackPressed();
    }

    /**
     * 提交按钮点击
     */
    public void btnSubmitClick(View view) {

        ResourceFactory.createResource(VerificationResource.class)
                .smsCodeValidation(getIntent().getExtras().getString("phone"), "86",
                        codeText.getText().toString())
                .enqueue(new DefaultCallback<ResourceResult>() {
                    @Override
                    public void success(ResourceResult resourceResult) {
                        startActivity(new Intent(RegisterVerifyCodeActivity.this,
                                RegisterPasswordActivity.class));
                    }

                    @Override
                    public void failure(String message) {
                        ActivityUtils.showError(RegisterVerifyCodeActivity.this, message);
                    }
                });
    }

    /**
     * 重新发送按钮单击事件
     * @param view
     */
    public void btnReSendClick(View view) {

        if(!isAbleReSendCode())
            return;

        vm.startTimer();
    }

    @Override
    protected void onStop() {
        super.onStop();
        vm.stopTimer();
    }

    /**
     * 是否可以重新发送短信
     * @return
     */
    private boolean isAbleReSendCode() {
        return vm.getTime() == 0;
    }

    public static class VM extends BaseObservable {
        private int time = TIMER_TIME;
        private Activity activity;
        private Timer timer;

        @Bindable
        public int getTime() {
            return time;
        }

        private VM(Activity activity) {
            this.activity = activity;
            timer = new Timer();
        }

        void startTimer() {
            timer.schedule(new TimerTask() {
                @Override

                public void run() {
                    activity.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            if (time >= 1) {
                                time--;
                            }
                            if (time == 0) {
                                stopTimer();
                            }
                            notifyPropertyChanged(BR.time);
                        }
                    });
                }
            }, 0, 1000);
        }

        void stopTimer() {
            timer.cancel();
            timer.purge();
        }

    }


    private final static int TIMER_TIME = 60;
    private VM vm;
    private EditText codeText;
}
