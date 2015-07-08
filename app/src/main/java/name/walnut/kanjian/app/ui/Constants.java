package name.walnut.kanjian.app.ui;

import android.net.Uri;

import name.walnut.kanjian.app.resource.impl.Resource;

public class Constants {
    public static interface Action {

        final static String PUAH_ACTION = "kanjian.intent.action.PUSH"; // 推送

        final static String LAUNCH_ACTION = "kanjian.intent.action.LAUNCH"; // 启动加载

        final static String REGISTER_ACTION = "kanjian.intent.action.REGISTER";

        final static String LOGIN_ACTION = "kanjian.intent.action.LOGIN";

        final static String MAIN_ACTION = "kanjian.intent.action.MAIN";

        final static String FORGET_PASSWORD_ACTION = "kanjian.intent.action.FORGET_PASSWORD";

        final static String CLAUSE_ACTION = "kanjian.intent.action.CLAUSE"; //服务条款

        final static String PRIVACY_ACTION = "kanjian.intent.action.PRIVACY"; //隐私策略

        final static String MESSAGE_ACTION = "kanjian.intent.action.MESSAGE";    //新消息界面

        final static String PERSONAL_PAGE_ACTION = "kanjian.intent.action.PERSONAL_PAGE"; // 个人主页

        /* 我的账号相关界面 action */
        final static String FRIEND_REQUEST_ACTION = "kanjian.intent.action.FRIEND_REQUEST"; // 好友请求

        final static String FRIENDS_ACTION = "kanjian.intent.action.FRIENDS"; // 我的好友

        final static String ADD_FRIENDS_ACTION = "kanjian.intent.action.ADD_FRIENDS"; // 添加好友

        final static String SETTING_ACTION = "kanjian.intent.action.SETTING";    // 设置

        final static String RESET_PASSWORD_ACTION = "kanjian.intent.action.RESET_PASSWORD"; //修改密码

        /* 上传图片相关 action */
        final static String UPLOAD_PIC_ACTION = "kanjian.intent.action.UPLOAD_PIC"; // 上传图片

    }

    public static interface Materials {
        final static int PASSWORD_MIN_LENGTH = 6;
        final static int PASSWORD_MAX_LENGTH = 20;

        final static int NICKNAME_MAX_LENGTH = 32;
    }

   //TODO 获得头像
    public static Uri getFileUri(String path) {
        return Uri.parse(Resource.getUrl("file/"+path));
    }
}
