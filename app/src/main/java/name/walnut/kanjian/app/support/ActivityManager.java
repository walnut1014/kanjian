package name.walnut.kanjian.app.support;

import android.app.Activity;

import java.util.Stack;

/**
 * activity 栈
 */
public class ActivityManager {
    private static Stack<Activity> activityStack;
    private static ActivityManager instance;
    private ActivityManager() {
    }
    public static ActivityManager getScreenManager() {
        if (instance == null) {
            instance = new ActivityManager();
        }
        return instance;
    }

    //退出栈顶Activity
    public void popActivity(Activity activity) {
        popActivityWithTransition(true, activity);
    }

    /**
     * 退出activity
     * @param transition 是否显示跳转动画
     * @param activity
     */
    private void popActivityWithTransition(boolean transition, Activity activity) {
        if (activity != null) {
            //在从自定义集合中取出当前Activity时，也进行了Activity的关闭操作
            if (!transition) {
                activity.overridePendingTransition(0, 0);
            }
            activity.finish();
            activityStack.remove(activity);
            activity = null;
        }
    }

    //获得当前栈顶Activity
    public Activity currentActivity() {
        Activity activity = null;
        if(!activityStack.empty())
            activity= activityStack.lastElement();
        return activity;
    }

    //将当前Activity推入栈中
    public void pushActivity(Activity activity) {
        if (activityStack == null) {
            activityStack = new Stack<>();
        }
        activityStack.add(activity);
    }

    //退出栈中所有Activity
    public void popAllActivityExceptOne(Class cls) {
        while (true) {
            Activity activity = currentActivity();
            if (activity == null) {
                break;
            }
            if (activity.getClass().equals(cls)) {
                break;
            }
            popActivityWithTransition(false, activity);
        }
    }
}


