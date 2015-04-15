package name.walnut.kanjian.app.push;

import name.walnut.kanjian.app.push.message.DefaultPushMessage;

/**
 * 解析推送的消息
 */
public class PushMessageResolve {

    /**
     * 消息解析
     * @param message 推送的消息
     * @return
     */
    public static BasePushMessage resolve(String message) {

        return new DefaultPushMessage(message);
    }

}
