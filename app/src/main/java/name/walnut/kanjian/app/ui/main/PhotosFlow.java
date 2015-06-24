package name.walnut.kanjian.app.ui.main;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * 看照片 bean
 */
public class PhotosFlow {

    public long id;         // 消息id
    public String content;  // 内容
    public String photoPath;    // 图片路径
    public boolean root;    // 主消息
    public long sendTime;   // 发送时间
    public String sender;   // 发送人昵称
    public long senderId;   // 发送人id

    public List<Comment> comments;


    public PhotosFlow() {
        comments = new ArrayList<>();
        int commentsCount = createRange(-1, 10);
        for (int i = 0; i < commentsCount; i++) {
            Comment comment = new Comment();
            comments.add(comment);
        }
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
