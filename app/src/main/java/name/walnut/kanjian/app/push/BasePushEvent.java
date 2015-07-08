package name.walnut.kanjian.app.push;

/**
 * 推送消息
 */
public abstract class BasePushEvent {

    protected String message;

    public BasePushEvent(String message) {
        this.message = message;
        resolve(message);
    }

    /**
     * 解析消息
     * @param message
     */
    public abstract void resolve(String message);
}
