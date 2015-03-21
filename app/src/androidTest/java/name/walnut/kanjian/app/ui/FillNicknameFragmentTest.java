package name.walnut.kanjian.app.ui;

import android.test.ActivityInstrumentationTestCase2;

import name.walnut.kanjian.app.ui.register.FillNicknameFragment;
import name.walnut.kanjian.app.ui.register.RegisterActivity;

/**
 * Created by baiya on 15/3/21.
 */
public class FillNicknameFragmentTest extends ActivityInstrumentationTestCase2<RegisterActivity>{
    public FillNicknameFragmentTest() {
        super(RegisterActivity.class);
    }

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        getActivity().switchFragment(new FillNicknameFragment());
    }

    public void testUI() {
        while (true) {

        }
    }
}
