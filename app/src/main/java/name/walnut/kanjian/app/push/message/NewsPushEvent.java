package name.walnut.kanjian.app.push.message;

import org.json.JSONException;
import org.json.JSONObject;

import name.walnut.kanjian.app.push.BasePushEvent;

/**
 * 首页tab栏，小消息红点
 */
public class NewsPushEvent extends BasePushEvent {

    private int count;

    public NewsPushEvent(String message) {
        super(message);
    }

    @Override
    public void resolve(String message) {
        try {
            JSONObject jsonObject = new JSONObject(message);
            count = jsonObject.optInt("count", 0);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public int getCount() {
        return count;
    }
}
