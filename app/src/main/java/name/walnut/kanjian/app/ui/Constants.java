package name.walnut.kanjian.app.ui;

public class Constants {
    public static interface Action {

        final static String REGISTER_ACTION = "kanjian.intent.action.REGISTER";

        final static String LOGIN_ACTION = "kanjian.intent.action.LOGIN";

        final static String MAIN_ACTION = "kanjian.intent.action.MAIN";

        final static String FORGET_PASSWORD_ACTION = "kanjian.intent.action.FORGET_PASSWORD";

        final static String CLAUSE_ACTION = "kanjian.intent.action.CLAUSE"; //服务条款

        final static String PRIVACY_ACTION = "kanjian.intent.action.PRIVACY"; //隐私策略
    }

    public static interface Materials {
        final static int PASSWORD_MIN_LENGTH = 6;
        final static int PASSWORD_MAX_LENGTH = 20;

        final static int NICKNAME_MAX_LENGTH = 32;
    }
}