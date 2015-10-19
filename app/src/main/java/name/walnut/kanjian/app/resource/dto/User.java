package name.walnut.kanjian.app.resource.dto;

/**
 * @auth walnut
 */
public class User {
    /** 用户ID*/
    private long id;
    /** 用户昵称*/
    private String nickName;

    public String getHeadPhotoPath() {
        return headPhotoPath;
    }

    public void setHeadPhotoPath(String headPhotoPath) {
        this.headPhotoPath = headPhotoPath;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    /** 用户头像路径*/
    private String headPhotoPath;
}
