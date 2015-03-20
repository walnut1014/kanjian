package name.walnut.kanjian.app.util;

import android.test.AndroidTestCase;

import junit.framework.TestCase;

import name.walnut.kanjian.app.ui.util.RegexUtils;

public class RegexUtilsTest extends AndroidTestCase{

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        getContext();
    }

    public void testPhoneRegex() {
        String phone = "13006320412";
        boolean result = RegexUtils.isMobilePhone(phone);
        assertEquals(result, true);

        phone = "1300632412";
        result = RegexUtils.isMobilePhone(phone);
        assertEquals(result, false);

        phone = "1300";
        result = RegexUtils.isMobilePhone(phone);
        assertEquals(result, false);

        phone = "23006320412";
        result = RegexUtils.isMobilePhone(phone);
        assertEquals(result, false);
    }

    public void testNumAndLetterRegex() {
        String phone = "13006320412";
        boolean result = RegexUtils.isNumAndLetter(phone);
        assertEquals(result, true);

        phone = "1300632412-";
        result = RegexUtils.isNumAndLetter(phone);
        assertEquals(result, false);

        phone = "1300asssSSZ";
        result = RegexUtils.isNumAndLetter(phone);
        assertEquals(result, true);

    }
}
