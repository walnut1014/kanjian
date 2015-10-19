package name.walnut.kanjian.app.resource.dto;

/**
 * 回复列表DTO
 *
 * @author walnut
 */
public class ReplayMessage {
    /** 回复发送人*/
    private User sender;
    /** 被回复人,如果为null则意味回复的是照片本身*/
    private User target;
    /** 回复时间*/
    private long replayTime;

    public User getSender() {
        return sender;
    }

    public void setSender(User sender) {
        this.sender = sender;
    }

    public User getTarget() {
        return target;
    }

    public void setTarget(User target) {
        this.target = target;
    }

    public long getReplayTime() {
        return replayTime;
    }

    public void setReplayTime(long replayTime) {
        this.replayTime = replayTime;
    }
}
