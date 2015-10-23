package name.walnut.kanjian.app.resource.dto;

import java.util.List;

/**
 * 照片相关消息
 *
 * 包括评论,发送者以及回复列表
 * @author walnut
 */
public class PhotoAccessories {
    /**  照片评论*/
    private String comment;
    /** 照片发送人*/
    private User sender;
    /** 照片拍摄时间*/
    private long dateTimeOriginal;

    /** 回复列表*/
    private List<ReplayMessage> replayMessages;

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public User getSender() {
        return sender;
    }

    public void setSender(User sender) {
        this.sender = sender;
    }

    public long getDateTimeOriginal() {
        return dateTimeOriginal;
    }

    public void setDateTimeOriginal(long dateTimeOriginal) {
        this.dateTimeOriginal = dateTimeOriginal;
    }

    public List<ReplayMessage> getReplayMessages() {
        return replayMessages;
    }

    public void setReplayMessages(List<ReplayMessage> replayMessages) {
        this.replayMessages = replayMessages;
    }


}
