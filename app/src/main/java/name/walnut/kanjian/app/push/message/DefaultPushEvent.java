package name.walnut.kanjian.app.push.message;

import name.walnut.kanjian.app.push.BasePushEvent;

/**
 *
 */
public class DefaultPushEvent extends BasePushEvent {

    public DefaultPushEvent(String message) {
        super(message);
    }

    @Override
    public void resolve(String message) {
    }
}
