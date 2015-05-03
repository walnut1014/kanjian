package name.walnut.kanjian.app.ui.my.relation.friends;

/**
 * 好友信息 entity
 */
public class FriendInfo {

    private String mobilePhone; // 注册手机号
    private long userId;    // 用户id
    private int photoCount; // 图片数
    private String nickName;    // 昵称
    private String avatar;  // 头像路径，相对路径，需要使用Constants.getFrescoUrl()拼接完整路径

    public FriendInfo() {}

    public String getMobilePhone() {
        return mobilePhone;
    }

    public void setMobilePhone(String mobilePhone) {
        this.mobilePhone = mobilePhone;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public int getPhotoCount() {
        return photoCount;
    }

    public void setPhotoCount(int photoCount) {
        this.photoCount = photoCount;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }
}
