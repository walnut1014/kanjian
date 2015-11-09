package name.walnut.kanjian.app.ui.my.relation.newfriends.pojo;

/**
 * 好友关系
 */
public enum RelationStatus {
    SELF(-1),          // 自己
    NO_RELATION(0),    // 没有关系
    WAIT_VERIFY(1),    // 等待验证
    ACCEPT(2),         // 接受
    AGREE(3),          // 已添加为好友
    ;

    private RelationStatus(int status) {
        this.status = status;
    }

    public int getStatus() {
        return status;
    }

    private int status; //服务器中状态码

}
