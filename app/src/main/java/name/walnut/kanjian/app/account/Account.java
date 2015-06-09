package name.walnut.kanjian.app.account;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * 账号信息缓存类
 */
public enum Account {
    INSTANCE
    ;

    Account() {
        accountBean = null;
    }

    /**
     * 清楚账号缓存
     * @param context
     */
    public synchronized void clear(Context context) {
        this.accountBean = new AccountBean();
    }

    public synchronized void setAccount(AccountBean accountBean) {
        this.accountBean = accountBean;
    }

    public String getNickname() {
        return accountBean.getNickname();
    }

    public synchronized void setNickname(String nickname) {
        this.accountBean.setNickname(nickname);
    }

    public String getHeadPhotoPath() {
        return accountBean.getHeadPhotoPath();
    }

    public synchronized void setHeadPhotoPath(String headPhotoPath) {
        accountBean.setHeadPhotoPath(headPhotoPath);
    }

    public String getMobilePhone() {
        return this.accountBean.getMobilePhone();
    }

    public synchronized void setMobilePhone(String mobilePhone) {
        this.accountBean.setMobilePhone(mobilePhone);
    }

    private AccountBean accountBean;

}
