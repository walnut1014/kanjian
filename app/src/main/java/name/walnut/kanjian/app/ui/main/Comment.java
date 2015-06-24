package name.walnut.kanjian.app.ui.main;

/**
 * 照片流评论
 */
public class Comment {

    public long id; // 评论id
    public String content = "评论内容，随即测试";      // 评论内容
    public long prentId;    // 父消息ID
    public String receiver = "lala"; // 被回复人的昵称，如果没有就意味着这条回复是针对主消息的
    public boolean root;    // 回复消息
    public long senderTime;         // 评论时间
    public String sender = "哈哈哈哈";    // 评论作者
    public long senderId;               // 评论作者id

}
