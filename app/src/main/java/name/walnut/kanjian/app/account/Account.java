package name.walnut.kanjian.app.account;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * 账号信息缓存类
 */
public enum Account {
    INSTANCE
    ;

    public synchronized void init(Context context) {
        this.context = context.getApplicationContext();
        SharedPreferences preferences =
                this.context.getSharedPreferences(ACCOUNT_FILE, Context.MODE_PRIVATE);
        nickname = preferences.getString(KEY_NICKNAME, "");
        headPhotoPath = preferences.getString(KEY_AVATAR_PATH, "");
        mobilePhone = preferences.getString(KEY_MOBILE_PHONE, "");
    }

    public synchronized void setAccount(int friendCount, int unreadCount, String nickname, String headPhotoPath, String mobilePhone) {
        SharedPreferences.Editor editor = context.getSharedPreferences(ACCOUNT_FILE, Context.MODE_PRIVATE).edit();
        editor.putString(KEY_NICKNAME, nickname)
              .putString(KEY_AVATAR_PATH, headPhotoPath)
              .putString(KEY_MOBILE_PHONE, mobilePhone)
              .apply();
        this.nickname = nickname;
        this.headPhotoPath = headPhotoPath;
        this.mobilePhone = mobilePhone;
    }

    public String getNickname() {
        return nickname;
    }

    public synchronized void setNickname(String nickname) {
        SharedPreferences.Editor editor = context.getSharedPreferences(ACCOUNT_FILE, Context.MODE_PRIVATE).edit();
        editor.putString(KEY_NICKNAME, nickname).apply();
        this.nickname = nickname;
    }

    public String getHeadPhotoPath() {
        return headPhotoPath;
    }

    public synchronized void setHeadPhotoPath(String headPhotoPath) {
        SharedPreferences.Editor editor = context.getSharedPreferences(ACCOUNT_FILE, Context.MODE_PRIVATE).edit();
        editor.putString(KEY_AVATAR_PATH, headPhotoPath).apply();
        this.headPhotoPath = headPhotoPath;
    }

    public String getMobilePhone() {
        return mobilePhone;
    }

    public synchronized void setMobilePhone(String mobilePhone) {
        SharedPreferences.Editor editor = context.getSharedPreferences(ACCOUNT_FILE, Context.MODE_PRIVATE).edit();
        editor.putString(KEY_MOBILE_PHONE, mobilePhone).apply();
        this.mobilePhone = mobilePhone;
    }

    private Context context;

    private String nickname;
    private String headPhotoPath;
    private String mobilePhone;

    private static final String ACCOUNT_FILE = "name.walnut.kanjian.app.account";
    private static final String KEY_NICKNAME = "nickname";
    private static final String KEY_AVATAR_PATH = "avatar_path";
    private static final String KEY_MOBILE_PHONE = "mobilePhone";
}
