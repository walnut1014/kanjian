package name.walnut.kanjian.app.ui.my.relation.request;

/**
 * 好友请求 entity
 */
public class FriendRequest {


    private long id;    // 服务器好友id

    private String mobilePhone; // 手机号

    private String nickName;    // 昵称

    private String avatar; // 头像路径，相对路径，需要使用Constants.getFrescoUrl()拼接完整路径

    private String contactsName = null; //  手机通讯录中名称

    /**
     * 是否是被邀请，true表示为邀请者，false表示为被邀请人
     */
    private boolean isInvited;

    /**
     * 是否接受邀请
     */
    private boolean isAgree;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public void setMobilePhone(String mobilePhone) {
        this.mobilePhone = mobilePhone;
    }

    public String getMobilePhone() {
        return mobilePhone;
    }

    public String getContactsName() {
        return contactsName;
    }

    public void setContactsName(String name) {
        this.contactsName = name;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public boolean isAgree() {
        return isAgree;
    }

    public void setAgree(boolean isAgree) {
        this.isAgree = isAgree;
    }

    public boolean isInvited() {
        return isInvited;
    }

    public void setInvited(boolean isInvited) {
        this.isInvited = isInvited;
    }
}
