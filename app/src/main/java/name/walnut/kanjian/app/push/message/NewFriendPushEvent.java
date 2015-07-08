package name.walnut.kanjian.app.push.message;

import org.json.JSONException;
import org.json.JSONObject;

import name.walnut.kanjian.app.push.BasePushEvent;
import name.walnut.kanjian.app.utils.Logger;

/**
 * 个人页tab栏，小消息红点
 */
public class NewFriendPushEvent extends BasePushEvent {

    private int count;

    public NewFriendPushEvent(String message) {
        super(message);
    }

    @Override
    public void resolve(String message) {
        try {
            JSONObject jsonObject = new JSONObject(message);
            count = jsonObject.optInt("count", 0);
            Logger.e(count+"");
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public int getCount() {
        return count;
    }
}
