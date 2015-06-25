package name.walnut.kanjian.app.ui.main;

/**
 * 照片流评论
 */
public class Comment {

    private long id; // 评论id
    private String content;      // 评论内容
    private long parentId;    // 父消息ID
    private String receiver; // 被回复人的昵称，如果没有就意味着这条回复是针对主消息的
    private boolean root;    // 回复消息
    private long senderTime;         // 评论时间
    private String sender;    // 评论作者
    private long senderId;               // 评论作者id

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public long getParentId() {
        return parentId;
    }

    public void setParentId(long parentId) {
        this.parentId = parentId;
    }

    public String getReceiver() {
        return receiver;
    }

    public void setReceiver(String receiver) {
        this.receiver = receiver;
    }

    public boolean isRoot() {
        return root;
    }

    public void setRoot(boolean root) {
        this.root = root;
    }

    public long getSenderTime() {
        return senderTime;
    }

    public void setSenderTime(long senderTime) {
        this.senderTime = senderTime;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public long getSenderId() {
        return senderId;
    }

    public void setSenderId(long senderId) {
        this.senderId = senderId;
    }
}
