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
        friendCount = preferences.getInt(KEY_FRIEND_COUNT, 0);
        unreadCount = preferences.getInt(KEY_UNREAD_COUNT, 0);
        nickname = preferences.getString(KEY_NICKNAME, "");
        headPhotoPath = preferences.getString(KEY_AVATAR_PATH, "");
        mobilePhone = preferences.getString(KEY_MOBILE_PHONE, "");
    }

    public synchronized void setAccount(int friendCount, int unreadCount, String nickname, String headPhotoPath, String mobilePhone) {
        SharedPreferences.Editor editor = context.getSharedPreferences(ACCOUNT_FILE, Context.MODE_PRIVATE).edit();
        editor.putInt(KEY_FRIEND_COUNT, friendCount)
                .putInt(KEY_UNREAD_COUNT, unreadCount)
                .putString(KEY_NICKNAME, nickname)
                .putString(KEY_AVATAR_PATH, headPhotoPath)
                .putString(KEY_MOBILE_PHONE, mobilePhone)
                .apply();
        this.friendCount = friendCount;
        this.unreadCount = unreadCount;
        this.nickname = nickname;
        this.headPhotoPath = headPhotoPath;
        this.mobilePhone = mobilePhone;
    }

    public int getFriendCount() {
        return friendCount;
    }

    public synchronized void setFriendCount(int friendCount) {
        SharedPreferences.Editor editor = context.getSharedPreferences(ACCOUNT_FILE, Context.MODE_PRIVATE).edit();
        editor.putInt(KEY_FRIEND_COUNT, friendCount).apply();
        this.friendCount = friendCount;
    }

    public int getUnreadCount() {
        return unreadCount;
    }

    public synchronized void setUnreadCount(int unreadCount) {
        SharedPreferences.Editor editor = context.getSharedPreferences(ACCOUNT_FILE, Context.MODE_PRIVATE).edit();
        editor.putInt(KEY_UNREAD_COUNT, unreadCount).apply();
        this.unreadCount = unreadCount;
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

    private int friendCount;
    private int unreadCount;
    private String nickname;
    private String headPhotoPath;
    private String mobilePhone;

    private static final String ACCOUNT_FILE = "name.walnut.kanjian.app.account";
    private static final String KEY_FRIEND_COUNT = "friend_count";
    private static final String KEY_UNREAD_COUNT = "unread_count";
    private static final String KEY_NICKNAME = "nickname";
    private static final String KEY_AVATAR_PATH = "avatar_path";
    private static final String KEY_MOBILE_PHONE = "mobilePhone";
}
