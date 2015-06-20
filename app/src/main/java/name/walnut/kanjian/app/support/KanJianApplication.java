package name.walnut.kanjian.app.support;

import android.app.Application;
import android.os.Environment;

import java.io.File;

import name.walnut.kanjian.app.account.Account;
import name.walnut.kanjian.app.entity.PhotoContext;
import name.walnut.kanjian.app.push.PushReceiver;
import name.walnut.kanjian.app.support.rest.RequestQueueContext;
import name.walnut.kanjian.app.ui.upload.UploadImageCache;

public class KanJianApplication extends Application {

	@Override
	public void onCreate() {
		super.onCreate();

		INSTANCE = this;


		File file = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM);

		PhotoContext.INSTANCE.init(file.getPath());


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
	
}
