package name.walnut.kanjian.app.push.message;

import name.walnut.kanjian.app.push.BasePushMessage;

/**
 * 首页顶部小黑条提醒
 */
public class MainTopTipMessage extends BasePushMessage {

    private int count;

    public MainTopTipMessage(String message) {
        super(message);
    }

    @Override
    public void resolve(String message) {

    }

    public int getCount() {
        return count;
    }
}
