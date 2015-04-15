package name.walnut.kanjian.app.push;

import de.greenrobot.event.EventBus;

/**
 * Push EventBus
 */
public class PushBusProvider {
    private static EventBus BUS = new EventBus();

    private PushBusProvider() {}

    public static EventBus getInstance() {
        return BUS;
    }
}
