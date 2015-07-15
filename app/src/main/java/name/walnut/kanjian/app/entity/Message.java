package name.walnut.kanjian.app.entity;

/**
 * 新消息
 */
public class Message {

    private long senderId;  // 照片发送者id
    private long id;        // 消息id
    private long repayerId; // 回复人id
    private String repayerAvatar;   // 回复人头像
    private String repayerName; // 回复人昵称
    private String remark;  //
    private String photo;   // 照片路径

    public long getSenderId() {
        return senderId;
    }

    public void setSenderId(long senderId) {
        this.senderId = senderId;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getRepayerId() {
        return repayerId;
    }

    public void setRepayerId(long repayerId) {
        this.repayerId = repayerId;
    }

    public String getRepayerAvatar() {
        return repayerAvatar;
    }

    public void setRepayerAvatar(String repayerAvatar) {
        this.repayerAvatar = repayerAvatar;
    }

    public String getRepayerName() {
        return repayerName;
    }

    public void setRepayerName(String repayerName) {
        this.repayerName = repayerName;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }
}
