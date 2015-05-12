package name.walnut.kanjian.app.support;

import android.app.ActionBar;
import android.view.Gravity;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.TextView;

import name.walnut.kanjian.app.R;

/**
 *
 */
public class ActionBarBuilder {

    public ActionBarBuilder(ActionBarActivity activity) {
        this.activity = activity;
        this.actionBar = activity.getActionBar();
    }

    /**
     * 返回按钮样式：文字和箭头
     */
    public static enum BackStyle {
        TEXT, ARROW,
    }

    public ActionBarBuilder setTitle(String title) {
        this.title = title;
        return this;
    }

    public ActionBarBuilder showBack(boolean show) {
        this.showBack = show;
        return this;
    }

    public ActionBarBuilder setBackStyle(BackStyle style) {
        this.backStyle = style;
        return this;
    }

    public ActionBarBuilder setBackText(String backText) {
        this.backText = backText;
        return this;
    }

    public ActionBarBuilder setBackClickListener(View.OnClickListener listener) {
        this.backListener = listener;
        return this;
    }

    public ActionBarBuilder setMenuView(View menuView) {
        this.menuView = menuView;
        return this;
    }

    public ActionBarBuilder build() {
        ActionBar.LayoutParams lp = new ActionBar.LayoutParams(
                ActionBar.LayoutParams.MATCH_PARENT,
                ActionBar.LayoutParams.MATCH_PARENT, Gravity.CENTER);

        View viewTitleBar = activity.getLayoutInflater().inflate(
                R.layout.action_bar_title, null);
        actionBar.setCustomView(viewTitleBar, lp);
        actionBar.setDisplayShowHomeEnabled(false);
        actionBar.setDisplayShowTitleEnabled(false);
        actionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        actionBar.setDisplayShowCustomEnabled(true);

        View customView = actionBar.getCustomView();

        // 居中标题
        titleTv = (TextView) customView.findViewById(android.R.id.title);
        titleTv.setText(title);

        // 左上返回按钮
        backButton = (ImageButton) customView.findViewById(R.id.btnActionBack);
        backTextButton = (TextView) customView.findViewById(R.id.action_back_text);
        backTextButton.setText(backText);
        backButton.setOnClickListener(backListener);
        backTextButton.setOnClickListener(backListener);
        if (!showBack) {
            backButton.setVisibility(View.GONE);
            backTextButton.setVisibility(View.GONE);
        } else {
            switch (backStyle) {
                case TEXT:
                    backButton.setVisibility(View.GONE);
                    backTextButton.setVisibility(View.VISIBLE);
                    break;
                case ARROW:
                    backButton.setVisibility(View.VISIBLE);
                    backTextButton.setVisibility(View.GONE);
                    break;
            }
        }

        // 右上自定义布局
        menuLayout = (FrameLayout) customView.findViewById(R.id.menu);
        menuLayout.removeAllViews();
        if (menuView != null) {
            menuLayout.addView(menuView);
        }

        return this;
    }

    private ActionBarActivity activity;
    private ActionBar actionBar;
    private String title = "";
    private boolean showBack = false;
    private BackStyle backStyle = BackStyle.ARROW;
    private String backText = "";
    private View.OnClickListener backListener;
    private View menuView;

    private ImageButton backButton;
    private TextView backTextButton;
    private TextView titleTv;
    private FrameLayout menuLayout;
}