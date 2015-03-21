package name.walnut.kanjian.app.support;

import com.squareup.otto.Bus;
import com.squareup.otto.ThreadEnforcer;

public enum BusContext {
    INSTANCE;

    public void init() {
        bus = new Bus(ThreadEnforcer.MAIN);
    }

    public Bus getBus() {
        return bus;
    }

    private Bus bus;
}
