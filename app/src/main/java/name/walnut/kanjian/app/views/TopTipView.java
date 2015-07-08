package name.walnut.kanjian.app.views;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import butterknife.ButterKnife;
import butterknife.InjectView;
import name.walnut.kanjian.app.R;

/**
 * 看照片顶部提示
 */
public class TopTipView extends FrameLayout {

    @InjectView(R.id.tip_news)
    TextView newsTipTv;
    @InjectView(R.id.tip_remind)
    TextView remindTipTv;
    @InjectView(R.id.divide)
    View divideView;
    @InjectView(R.id.tip_container)
    LinearLayout tipContainer;

    private static final int SHOW_NEWS_TIP = 0x1;
    private static final int SHOW_REMIND_TIP = 0x2;
    private static final int SHOW_NEWS_AND_REMIND_TIP = 0x3;
    private static final int HIDE_ALL = 0x0;

    private int flag = HIDE_ALL;


    public TopTipView(Context context) {
        this(context, null);
    }

    public TopTipView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public TopTipView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs, defStyleAttr);
    }

    private void init(Context context, AttributeSet attrs, int defStyleAttr) {
        LayoutInflater inflater = LayoutInflater.from(context);
        inflater.inflate(R.layout.view_top_tips, this, true);

        ButterKnife.inject(this, this);

        flag = HIDE_ALL;
        refreshView();
    }

    /**
     * 设置小黑条点击事件
     * @param onClickListener
     */
    public void setNewsTipClickListener(OnClickListener onClickListener) {
        newsTipTv.setOnClickListener(onClickListener);
    }

    /**
     * 设置新消息提醒文字
     * @param text
     */
    public void setNewsTip(CharSequence text) {
        newsTipTv.setText(text);
    }

    /**
     * 设置消息提醒文字
     * @param text
     */
    public void setRemindTip(CharSequence text) {
        remindTipTv.setText(text);
    }

    /**
     * 显示未读提醒
     * @param show
     */
    public void showNewsTip(boolean show) {
        flag = show ? (flag | SHOW_NEWS_TIP) : (flag & (SHOW_NEWS_AND_REMIND_TIP^SHOW_NEWS_TIP));
        refreshView();
    }

    /**
     * @param show
     */
    public void showRemindTip(boolean show) {
        flag = show ? (flag | SHOW_REMIND_TIP) : (flag & (SHOW_NEWS_AND_REMIND_TIP^SHOW_REMIND_TIP));
        refreshView();
    }

    private void refreshView() {
        switch (flag) {
            case SHOW_NEWS_AND_REMIND_TIP:
                tipContainer.setVisibility(VISIBLE);
                newsTipTv.setVisibility(VISIBLE);
                remindTipTv.setVisibility(VISIBLE);
                divideView.setVisibility(VISIBLE);
                break;
            case HIDE_ALL:
                tipContainer.setVisibility(GONE);
                newsTipTv.setVisibility(GONE);
                remindTipTv.setVisibility(GONE);
                divideView.setVisibility(GONE);
                break;
            case SHOW_NEWS_TIP:
                tipContainer.setVisibility(VISIBLE);
                newsTipTv.setVisibility(VISIBLE);
                remindTipTv.setVisibility(GONE);
                divideView.setVisibility(GONE);
                break;
            case SHOW_REMIND_TIP:
                tipContainer.setVisibility(VISIBLE);
                newsTipTv.setVisibility(GONE);
                remindTipTv.setVisibility(VISIBLE);
                divideView.setVisibility(GONE);
                break;
        }
    }

}
