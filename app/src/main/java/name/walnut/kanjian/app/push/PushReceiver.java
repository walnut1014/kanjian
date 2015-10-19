package name.walnut.kanjian.app.push;

import android.content.Context;
import android.content.Intent;

import com.umeng.message.PushAgent;
import com.umeng.message.UmengMessageHandler;
import com.umeng.message.entity.UMessage;

import name.walnut.kanjian.app.support.AppContext;
import name.walnut.kanjian.app.ui.Constants;
import name.walnut.kanjian.app.utils.Logger;

/**
 * 消息推送
 */
public enum PushReceiver {
    INSTANCE
    ;

    PushReceiver() {}

    public void init(Context context) {
        this.mPushAgent = PushAgent.getInstance(context);

        UmengMessageHandler messageHandler = new UmengMessageHandler(){
            @Override
            public void dealWithCustomMessage(final Context context, final UMessage msg) {
                // 负责处理自定义消息，需由用户处理

                /*
                 * 直接在这里使用EventBus机制分发消息时activity中接受不到，
                 * 所以将消息传递到broadcast中再进行分发
                 */
                Intent intent = new Intent();
                intent.putExtra("message", msg.custom);
                intent.setAction(Constants.Action.PUAH_ACTION);
                AppContext.INSTANCE.sendBroadcast(intent);

                Logger.e(msg.custom);
                Logger.e(msg.extra.toString());
            }

            @Override
            public void dealWithNotificationMessage(Context context, UMessage uMessage) {
                super.dealWithNotificationMessage(context, uMessage);
                // 处理通知消息，该方法已经由消息推送SDK 完成
                Logger.e(uMessage.text);
                Logger.e(uMessage.custom);
                Logger.e(uMessage.extra.toString());
            }

        };
        mPushAgent.setMessageHandler(messageHandler);
    }


    public PushAgent getPushAgent() {
        return mPushAgent;
    }

    private PushAgent mPushAgent;
}
