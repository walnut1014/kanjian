package name.walnut.kanjian.app.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by baiya on 15/6/25.
 */
public class TimeUtils {

    public static String getTimeDiff(long time) {
        final long timeNow = System.currentTimeMillis();
        int timeDiff = (int) (Math.abs(timeNow - time) / 1000);
        int timeDiffInMinute = timeDiff / 60;   // 分钟差
        int timeDiffInHour = timeDiffInMinute / 60; // 小时差
        int timeDiffInDay = timeDiffInHour / 24; // 天差
        int timeDiffInMouth = timeDiffInDay / 30;   // 月差
        int timeDiffInYear = timeDiffInMouth / 12;  // 年差

        SimpleDateFormat dateFormat = new SimpleDateFormat("MM月dd日 HH:mm");
        Date date = new Date(time);
        String dateFormatStr = dateFormat.format(date);

        if (timeDiffInMouth > 0) {
            return dateFormatStr;
        } else if (timeDiffInDay != 0) {
            return timeDiffInDay + "天前";
        } else if (timeDiffInHour != 0) {
            return timeDiffInHour + "小时前";
        } else if (timeDiffInMinute != 0) {
            return timeDiffInMinute + "分钟前";
        } else {
            return "刚刚";
        }
    }

}
