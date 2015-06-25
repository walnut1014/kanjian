package name.walnut.kanjian.app.ui.main;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Random;

/**
 * 看照片 bean
 */
public class PhotosFlow {

    private long id;         // 消息id
    private String content;  // 内容
    private String photoPath;    // 图片路径
    private boolean root;    // 主消息
    private long sendTime;   // 发送时间
    private String sender;   // 发送人昵称
    private long senderId;   // 发送人id

    private List<Comment> comments;


    public PhotosFlow() {
        comments = new ArrayList<>();
    }

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

    public String getPhotoPath() {
        return photoPath;
    }

    public void setPhotoPath(String photoPath) {
        this.photoPath = photoPath;
    }

    public boolean isRoot() {
        return root;
    }

    public void setRoot(boolean root) {
        this.root = root;
    }

    public long getSendTime() {
        return sendTime;
    }

    public void setSendTime(long sendTime) {
        this.sendTime = sendTime;
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

    public List<Comment> getComments() {
        return comments;
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }

    /**
     * 添加评论
     * @param comment
     */
    public void addComment(Comment comment) {
        comments.add(comment);
        Collections.sort(comments, new Comparator<Comment>() {
            @Override
            public int compare(Comment lhs, Comment rhs) {
                return (int) (lhs.getSenderTime() - rhs.getSenderTime());
            }
        });
    }

    private int createRange(int min, int max) {
        if (min >= max) {
            return -1;
        }
        Random random = new Random();
        int result = random.nextInt();
        if (result <= 0) {
            result = result + Integer.MAX_VALUE;
        }
        result = (result % (max - min)) + min;
        return result;
    }

}
