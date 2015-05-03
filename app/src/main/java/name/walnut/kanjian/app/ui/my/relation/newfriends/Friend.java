package name.walnut.kanjian.app.ui.my.relation.newfriends;

/**
 * 好友 entity
 */
public class Friend {

    private long id;    // 服务器好友id

    private String mobilePhone; // 手机号

    private String nickName;    // 昵称

    private String avatar; // 头像路径，相对路径，需要使用Constants.getFrescoUrl()拼接完整路径

    private String contactsName = null; //  手机通讯录中名称

    private RelationShip relation = RelationShip.NO_RELATION;

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

    public RelationShip getRelation() {
        return relation;
    }

    public void setRelation(RelationShip relation) {
        this.relation = relation;
    }

    /**
     * 根据状态码设置好友关系
     * @param relationCode NO_RELATION(0), WAIT_VERIFY(1), ACCEPT(2), AGREE(3), 其他值按 NO_RELATION 处理
     */
    public void setRelation(int relationCode) {
        RelationShip[] relationShips = RelationShip.values();
        for (RelationShip relationShip : relationShips) {
            if (relationShip.status == relationCode) {
                setRelation(relationShip);
                return;
            }
        }
        setRelation(RelationShip.NO_RELATION);
    }

    /**
     * 好友关系
     */
    public static enum RelationShip {
        NO_RELATION(0),    // 没有关系
        WAIT_VERIFY(1),    // 等待验证
        ACCEPT(2),         // 接受
        AGREE(3),          // 已添加为好友
        ;

        RelationShip(int status) {
            this.status = status;
        }

        public int getStatus() {
            return status;
        }

        private int status; //服务器中状态码

    }
}
