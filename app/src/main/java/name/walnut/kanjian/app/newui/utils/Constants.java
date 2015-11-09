package name.walnut.kanjian.app.newui.utils;

import android.net.Uri;


public class Constants {
    public interface Action {

         String PUAH_ACTION = "kanjian.intent.action.PUSH"; // 推送

         String LAUNCH_ACTION = "kanjian.intent.action.LAUNCH"; // 启动加载

         String REGISTER_ACTION = "kanjian.intent.action.REGISTER";

         String LOGIN_ACTION = "kanjian.intent.action.LOGIN";

         String MAIN_ACTION = "kanjian.intent.action.MAIN";

         String FORGET_PASSWORD_ACTION = "kanjian.intent.action.FORGET_PASSWORD";

         String CLAUSE_ACTION = "kanjian.intent.action.CLAUSE"; //服务条款

         String PRIVACY_ACTION = "kanjian.intent.action.PRIVACY"; //隐私策略

         String MESSAGE_ACTION = "kanjian.intent.action.MESSAGE";    //新消息界面

         String PERSONAL_PAGE_ACTION = "kanjian.intent.action.PERSONAL_PAGE"; // 个人主页

        /* 我的账号相关界面 action */
         String FRIEND_REQUEST_ACTION = "kanjian.intent.action.FRIEND_REQUEST"; // 好友请求

         String FRIENDS_ACTION = "kanjian.intent.action.FRIENDS"; // 我的好友

         String ADD_FRIENDS_ACTION = "kanjian.intent.action.ADD_FRIENDS"; // 添加好友

         String SETTING_ACTION = "kanjian.intent.action.SETTING";    // 设置

         String RESET_PASSWORD_ACTION = "kanjian.intent.action.RESET_PASSWORD"; //修改密码

        /* 上传图片相关 action */
         String UPLOAD_PIC_ACTION = "kanjian.intent.action.UPLOAD_PIC"; // 上传图片

    }

    public static interface Materials {
         int PASSWORD_MIN_LENGTH = 6;
         int PASSWORD_MAX_LENGTH = 20;

         int NICKNAME_MAX_LENGTH = 32;
    }
    }
