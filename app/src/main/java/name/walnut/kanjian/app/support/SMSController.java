package name.walnut.kanjian.app.support;

import android.content.Context;

import cn.smssdk.EventHandler;
import cn.smssdk.SMSSDK;

/**
 * 短信验证
 */
public class SMSController {

    public static abstract class SmsEvenHandler {
        public abstract void onSmsResultComplete(Object data); // 短信注册成功
        public abstract void onVerificationCodeSend(Object data);  // 验证码已发送
        public abstract void onFault(int event, int result, Object data);

        private EventHandler eventHandler;
    }

    public static void init(Context context) {
        // appkey, appsecret
        SMSSDK.initSDK(context, "673e4d512b90", "2537ee14a6dd90601c29fb7e3c7ce832");
    }

    public static void registerEventHandler(final SmsEvenHandler smsEvenHandler) {
        EventHandler eventHandler = new EventHandler(){
            @Override
            public void afterEvent(int event, int result, Object data) {
                if (result == SMSSDK.RESULT_COMPLETE) {
                    // 短信注册成功后，返回MainActivity,然后提示
                    if (event == SMSSDK.EVENT_SUBMIT_VERIFICATION_CODE) {// 提交验证码成功
                        smsEvenHandler.onSmsResultComplete(data);
                    } else if (event == SMSSDK.EVENT_GET_VERIFICATION_CODE) {
                        // 验证码已经发送
                        smsEvenHandler.onVerificationCodeSend(data);
                    } else {
                        ((Throwable) data).printStackTrace();
                        smsEvenHandler.onFault(event, result, data);
                    }
                } else {
                    smsEvenHandler.onFault(event, result, data);
                }
            }
        };
        smsEvenHandler.eventHandler = eventHandler;
        SMSSDK.registerEventHandler(eventHandler);
    }

    public static void unregisterEventHandler(SmsEvenHandler smsEvenHandler) {
        EventHandler eventHandler = smsEvenHandler.eventHandler;
        if (eventHandler != null) {
            SMSSDK.unregisterEventHandler(eventHandler);
        }
    }

    public static void unregisterAllEventHandler() {
        SMSSDK.unregisterAllEventHandler();
    }

    /**
     * 获取中国区号码的短信验证码
     * @param phoneNums
     */
    public static void getChinaVerificationCode(String phoneNums) {
        SMSSDK.getVerificationCode("86", phoneNums);
    }

    public static void getVerificationCode(String country, String phoneNums) {
        SMSSDK.getVerificationCode(country, phoneNums);
    }

}
