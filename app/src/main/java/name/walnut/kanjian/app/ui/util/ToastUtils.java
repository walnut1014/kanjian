package name.walnut.kanjian.app.ui.util;

import android.widget.Toast;

import name.walnut.kanjian.app.support.AppContext;

public class ToastUtils {

    public static void toast(String msg) {
        toast(msg, Toast.LENGTH_LONG);
    }

    public static void toast(int msg) {
        toast(msg, Toast.LENGTH_LONG);
    }

    public static void toast(String msg, int duration) {
        Toast.makeText(AppContext.INSTANCE, msg, duration).show();
    }

    public static void toast(int msg, int duration) {
        Toast.makeText(AppContext.INSTANCE, msg, duration).show();
    }

}
