package name.walnut.kanjian.app.resource.dto;

public class MobileRelation {

    /** 用户昵称*/
    private String nickName;
    /** 头像路径*/
    private String headPhotoPath;
    /** 用户ID*/
    private long id;
    /** 用户手机号码*/
    private String phone;
    /** 关系状态*/
    private int relationStatus;

    public static final int SELF = -1; //自己
    public static final int NO_RELATION =0; //没有关系
    public static final int WAIT_VERIFY = 1; //待验证
    public static final int ACCEPT =2;	//接受按钮
    public static final int AGREE = 3; //已同意加为好友

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getHeadPhotoPath() {
        return headPhotoPath;
    }

    public void setHeadPhotoPath(String headPhotoPath) {
        this.headPhotoPath = headPhotoPath;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public int getRelationStatus() {
        return relationStatus;
    }

    public void setRelationStatus(int relationStatus) {
        this.relationStatus = relationStatus;
    }
}
