package name.walnut.kanjian.app.ui.util;

import android.text.TextUtils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegexUtils {

    /**
     * 判断手机格式是否正确
     * @param mobiles
     * @return
     */
    public static boolean isMobilePhone(String mobiles) {

        Pattern p = Pattern
                .compile("^((13[0-9])|(15[^4,\\D])|(18[0-9]))\\d{8}$");
        Matcher m = p.matcher(mobiles);

        return m.matches();

    }

    public static boolean isNumAndLetter(String str) {

        Pattern pattern = Pattern.compile("([0-9a-zA-Z])+");
        Matcher isNum = pattern.matcher(str);

        return isNum.matches();
    }
}
