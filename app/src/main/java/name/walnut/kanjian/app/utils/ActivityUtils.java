package name.walnut.kanjian.app.utils;

import android.app.Activity;
import android.widget.Toast;

/**
 * Created by walnut on 15/11/8.
 */
public final class ActivityUtils {

    public static void showError(Activity activity, String msg) {
        Toast.makeText(activity, msg, Toast.LENGTH_SHORT).show();
    }

}
