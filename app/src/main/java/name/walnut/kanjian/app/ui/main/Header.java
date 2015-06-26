package name.walnut.kanjian.app.ui.main;

/**
 * 首页照片流header
 */
class Header {
    private int newsCount;
    private boolean showRemindTip = true;
    private boolean showNewsTip = true;

    public int getNewsCount() {
        return newsCount;
    }

    public void setNewsCount(int newsCount) {
        this.newsCount = newsCount;
    }

    public boolean isShowRemindTip() {
        return showRemindTip;
    }

    public void setShowRemindTip(boolean showRemindTip) {
        this.showRemindTip = showRemindTip;
    }

    public boolean isShowNewsTip() {
        return showNewsTip;
    }

    public void showNewsTip(boolean showNewsTip) {
        this.showNewsTip = showNewsTip;
    }
}
