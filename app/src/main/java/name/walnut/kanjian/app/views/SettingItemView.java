package name.walnut.kanjian.app.views;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import name.walnut.kanjian.app.R;

/**
 * 我的账户 item view
 */
public class SettingItemView extends FrameLayout {

    private TextView titleTv;
    private TextView extraTv;
    private ImageView iconImg;
    private ImageView dotImg;

    public SettingItemView(Context context) {
        super(context);
    }

    public SettingItemView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SettingItemView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs, defStyleAttr);
    }

    private void init(Context context, AttributeSet attrs, int defStyleAttr) {

        final LayoutInflater inflater = LayoutInflater.from(context);
        final int resLayoutId = R.layout.view_my_item;
        inflater.inflate(resLayoutId, this, true);

        TypedArray a =
                context.obtainStyledAttributes(attrs, R.styleable.SettingItemView, defStyleAttr, 0);

        titleTv = (TextView) this.findViewById(R.id.title);
        extraTv = (TextView) this.findViewById(R.id.extra_content);
        iconImg = (ImageView) this.findViewById(R.id.imageview);
        dotImg = (ImageView) findViewById(R.id.dot);

        titleTv.setText(a.getString(R.styleable.SettingItemView_title));
        extraTv.setText(a.getString(R.styleable.SettingItemView_extraContent));
        iconImg.setImageDrawable(a.getDrawable(R.styleable.SettingItemView_icon));

        boolean showDot = a.getBoolean(R.styleable.SettingItemView_showDot, false);
        showDot(showDot);

        a.recycle();
    }

    /**
     * 显示小圆点
     * @param showDot
     */
    public void showDot(boolean showDot) {
        if (showDot) {
            dotImg.setVisibility(VISIBLE);
        } else {
            dotImg.setVisibility(INVISIBLE);
        }
    }

    /**
     * 显示extra
     * @param extra
     */
    public void setExtra(String extra) {
        extraTv.setText(extra);
    }

}
