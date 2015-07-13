package name.walnut.kanjian.app.support;

import android.app.Application;
import android.content.Intent;
import android.os.Environment;

import java.io.File;

import name.walnut.kanjian.app.account.Account;
import name.walnut.kanjian.app.entity.PhotoContext;
import name.walnut.kanjian.app.push.PushReceiver;
import name.walnut.kanjian.app.support.rest.RequestQueueContext;
import name.walnut.kanjian.app.ui.Constants;

public class KanJianApplication extends Application {

	@Override
	public void onCreate() {
		super.onCreate();

		INSTANCE = this;


		File file = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM);

		PhotoContext.INSTANCE.init(file.getPath());

        // 账号信息
        Account.INSTANCE.init(this);

        // 网络请求
		RequestQueueContext.INSTANCE.initRequestQueue(this);

        // 短信验证 SDK
        SMSController.init(this);

        // 消息推送
        PushReceiver.INSTANCE.init(this);

        // 图片加载
        FrescoContext.INSTANCE.init(this);

    }

	public static Application INSTANCE;
	
	public static boolean isLogin = false;


    public static void restart() {
        // 清楚本地账号，退出所有activity，重新启动main
        Account.INSTANCE.clear(INSTANCE);
        ActivityManager.getScreenManager().popAllActivityExceptOne(null);
        Intent intent = new Intent(Constants.Action.LAUNCH_ACTION);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        INSTANCE.startActivity(intent);
    }
}
