package name.walnut.kanjian.app.ui;

import android.test.ActivityInstrumentationTestCase2;

public class LaunchActivityTest extends ActivityInstrumentationTestCase2<LaunchActivity>{

    public LaunchActivityTest() {
        super(LaunchActivity.class);
    }

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        getActivity();
    }
}
