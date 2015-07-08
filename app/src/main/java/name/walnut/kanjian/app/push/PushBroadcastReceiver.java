package name.walnut.kanjian.app.push;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import name.walnut.kanjian.app.utils.Logger;

/**
 * 消息推送事件
 */
public class PushBroadcastReceiver extends BroadcastReceiver{

    @Override
    public void onReceive(Context context, Intent intent) {
        String messageStr = intent.getStringExtra("message");
        BasePushEvent message = PushMessageResolve.resolve(messageStr);
        Logger.e(message.getClass().getName());

        PushBusProvider.getInstance().post(message);
    }
}
