package name.walnut.kanjian.app.ui.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 验证码工具
 *
 * @author walnut
 */
public class RegexUtils {

    /**
     * 判断手机格式是否正确
     * @param phone
     * @return
     */
    public static boolean isMobilePhone(String phone) {

        return phone.matches("^((13[0-9])|(15[^4,\\D])|(18[0-9]))\\d{8}$");

    }

    public static boolean isNumAndLetter(String str) {

        Pattern pattern = Pattern.compile("([0-9a-zA-Z])+");
        Matcher isNum = pattern.matcher(str);

        return isNum.matches();
    }
}
