package name.walnut.kanjian.app.account;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;

/**
 * 账号信息缓存类
 */
public enum Account {
    INSTANCE;

    Account() {
        accountBean = new AccountBean();
    }

    public synchronized void init(Context context) {
        this.context = context.getApplicationContext();
        SharedPreferences preferences =
                this.context.getSharedPreferences(ACCOUNT_FILE, Context.MODE_PRIVATE);
        long userId = preferences.getLong(KEY_USER_ID, 0);
        String nickname = preferences.getString(KEY_NICKNAME, "");
        String headPhotoPath = preferences.getString(KEY_AVATAR_PATH, "");
        String mobilePhone = preferences.getString(KEY_MOBILE_PHONE, "");
        int photoCount = preferences.getInt(KEY_PHOTO_COUNT, 0);

        accountBean.setId(userId);
        accountBean.setNickname(nickname);
        accountBean.setHeadPhotoPath(headPhotoPath);
        accountBean.setMobilePhone(mobilePhone);
        accountBean.setPhotoCount(photoCount);
    }

    public boolean isAvailable() {
        return accountBean.getId() != 0
                && !TextUtils.isEmpty(accountBean.getMobilePhone())
                && !TextUtils.isEmpty(accountBean.getNickname());
    }

    /**
     * 清楚账号缓存
     * @param context
     */
    public synchronized void clear(Context context) {
        this.accountBean = new AccountBean();
        clearAccount();
    }

    public synchronized void setAccount(AccountBean accountBean) {
        this.accountBean = accountBean;
        saveAccount();
    }

    public String getNickname() {
        return accountBean.getNickname();
    }

    public synchronized void setNickname(String nickname) {
        this.accountBean.setNickname(nickname);
        saveAccount();
    }

    public String getHeadPhotoPath() {
        return accountBean.getHeadPhotoPath();
    }

    public synchronized void setHeadPhotoPath(String headPhotoPath) {
        accountBean.setHeadPhotoPath(headPhotoPath);
        saveAccount();
    }

    public String getMobilePhone() {
        return this.accountBean.getMobilePhone();
    }

    public synchronized void setMobilePhone(String mobilePhone) {
        this.accountBean.setMobilePhone(mobilePhone);
        saveAccount();
    }

    public int getPhotoCount() {
        return accountBean.getPhotoCount();
    }

    public long getId() {
        return accountBean.getId();
    }

    private void saveAccount() {
        SharedPreferences.Editor editor = context.getSharedPreferences(ACCOUNT_FILE, Context.MODE_PRIVATE).edit();
        editor.putLong(KEY_USER_ID, accountBean.getId())
                .putString(KEY_NICKNAME, accountBean.getNickname())
                .putString(KEY_AVATAR_PATH, accountBean.getHeadPhotoPath())
                .putString(KEY_MOBILE_PHONE, accountBean.getMobilePhone())
                .putInt(KEY_PHOTO_COUNT, accountBean.getPhotoCount())
                .apply();
    }

    private void clearAccount() {
        SharedPreferences.Editor editor =
                context.getSharedPreferences(ACCOUNT_FILE, Context.MODE_PRIVATE).edit();
        editor.clear().apply();
    }

    private AccountBean accountBean;
    private Context context;

    private static final String ACCOUNT_FILE = "name.walnut.kanjian.app.account";
    private static final String KEY_USER_ID = "id";
    private static final String KEY_NICKNAME = "nickname";
    private static final String KEY_AVATAR_PATH = "avatar_path";
    private static final String KEY_MOBILE_PHONE = "mobilePhone";
    private static final String KEY_PHOTO_COUNT = "photocount";
}
