package name.walnut.kanjian.app.support;

import android.app.Application;
import android.os.Environment;

import java.io.File;

import name.walnut.kanjian.app.entity.PhotoContext;
import name.walnut.kanjian.app.push.PushReceiver;
import name.walnut.kanjian.app.support.rest.RequestQueueContext;

public class KanJianApplication extends Application {

	@Override
	public void onCreate() {
		super.onCreate();
		RequestQueueContext.INSTANCE.initRequestQueue(this);

		INTANCE = this;
		
		File file = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM);
		
		PhotoContext.INSTANCE.init(file.getPath());

        // 短信验证 SDK
        SMSController.init(this);

        // 消息推送
        PushReceiver.INSTANCE.init(this);

    }
	
	public static Application INTANCE;
	
	public static boolean isLogin = false;
	
}
