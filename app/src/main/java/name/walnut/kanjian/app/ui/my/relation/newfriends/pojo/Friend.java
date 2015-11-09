package name.walnut.kanjian.app.ui.my.relation.newfriends.pojo;

/**
 * 好友 entity
 */
public class Friend {

    private long id;    // 服务器好友id

    private String mobilePhone; // 手机号

    private String nickName;    // 昵称

    private String avatar; // 头像路径，相对路径，需要使用Constants.getFrescoUrl()拼接完整路径

    private String contactsName = null; //  手机通讯录中名称

    private RelationStatus relation = RelationStatus.NO_RELATION;

    public Friend() {}

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

    public RelationStatus getRelation() {
        return relation;
    }

    public void setRelation(RelationStatus relation) {
        this.relation = relation;
    }

    /**
     * 根据状态码设置好友关系
     * @param relationCode SELF(-1), NO_RELATION(0), WAIT_VERIFY(1), ACCEPT(2), AGREE(3)
     */
    public void setRelation(int relationCode) {
        RelationStatus[] relationShips = RelationStatus.values();
        for (RelationStatus relationStatus : relationShips) {
            if (relationStatus.getStatus() == relationCode) {
                setRelation(relationStatus);
                return;
            }
        }
        setRelation(RelationStatus.NO_RELATION);
    }

}
