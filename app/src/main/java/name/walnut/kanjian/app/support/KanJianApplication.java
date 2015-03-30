package name.walnut.kanjian.app.support;

import java.io.File;

import cn.smssdk.SMSSDK;
import name.walnut.kanjian.app.entity.PhotoContext;
import name.walnut.kanjian.app.support.rest.RequestQueueContext;

import android.app.Application;
import android.os.Environment;

public class KanJianApplication extends Application {

	@Override
	public void onCreate() {
		super.onCreate();
		RequestQueueContext.INSTANCE.initRequestQueue(this);

		INTANCE = this;
		
		File file = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM);
		
		PhotoContext.INSTANCE.init(file.getPath());

        BusContext.INSTANCE.init();

        // 短信验证 SDK
        SMSController.init(this);

    }
	
	public static Application INTANCE;
	
	public static boolean isLogin = false;
	
}
