package name.walnut.kanjian.app.push.message;

import name.walnut.kanjian.app.push.BasePushMessage;

/**
 * 小消息红点
 */
public class BadgeMessage extends BasePushMessage{

    private int count;
    private BadgeType badgeType;

    public BadgeMessage(String message) {
        super(message);
    }

    @Override
    public void resolve(String message) {

    }

    public BadgeType getBadgeType() {
        return badgeType;
    }

    public int getCount() {
        return count;
    }
}
