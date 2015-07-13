package name.walnut.kanjian.app.account;

/**
 */
public enum AuthManager {
    INSTANCE
    ;

    private long lastActiveTime;

    /**
     * 本地是否超时
     * @return 如果超时返回true
     */
    public boolean isLocalTimeout() {
        long currentTime = System.currentTimeMillis();

        final long timeDiff = 2 * 60 * 60 * 1000;   // 允许时间间隔为2小时
        return (currentTime - lastActiveTime) > timeDiff;
    }

    /**
     *
     */
    public void setLastAppActiveTime() {
        lastActiveTime = System.currentTimeMillis();
    }
}
