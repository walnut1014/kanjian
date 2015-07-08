package name.walnut.kanjian.app.push;

import org.json.JSONException;
import org.json.JSONObject;

import name.walnut.kanjian.app.push.message.DefaultPushEvent;
import name.walnut.kanjian.app.push.message.MessagePushEvent;
import name.walnut.kanjian.app.push.message.NewFriendPushEvent;
import name.walnut.kanjian.app.push.message.NewsPushEvent;

/**
 * 解析推送的消息
 */
public class PushMessageResolve {

    /**
     * 消息解析
     * @param message 推送的消息
     * @return
     */
    public static BasePushEvent resolve(String message) {
        try {
            JSONObject object = new JSONObject(message);
            String type = object.getString("type");
            return resolveByType(type, message);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return new DefaultPushEvent(message);
    }

    /**
     * 根据消息类型获取相应{@link BasePushEvent}对象
     * @param type 消息类型
     * @param message 消息内容
     * @return
     */
    private static BasePushEvent resolveByType(final String type, String message) {
        BasePushEvent pushMessage;
        switch (type) {
            case "newsCount":   // 首页tab栏新消息
                pushMessage = new NewsPushEvent(message);
                break;

            case "newFriend":   // 个人tab栏
                pushMessage = new NewFriendPushEvent(message);
                break;

            case "message":     // 首页顶部小黑条
                pushMessage = new MessagePushEvent(message);
                break;
            default:
                pushMessage = new DefaultPushEvent(message);
        }
        return pushMessage;
    }

}
