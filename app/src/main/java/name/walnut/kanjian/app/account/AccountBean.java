package name.walnut.kanjian.app.account;

/**
 * 账号信息
 */
public class AccountBean {

    private String nickname;
    private String headPhotoPath;
    private String mobilePhone;
    private int photoCount;

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getHeadPhotoPath() {
        return headPhotoPath;
    }

    public void setHeadPhotoPath(String headPhotoPath) {
        this.headPhotoPath = headPhotoPath;
    }

    public String getMobilePhone() {
        return mobilePhone;
    }

    public void setMobilePhone(String mobilePhone) {
        this.mobilePhone = mobilePhone;
    }

    public int getPhotoCount() {
        return photoCount;
    }

    public void setPhotoCount(int photoCount) {
        this.photoCount = photoCount;
    }
}
