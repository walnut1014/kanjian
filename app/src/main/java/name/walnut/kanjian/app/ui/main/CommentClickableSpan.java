package name.walnut.kanjian.app.ui.main;

import android.content.Context;
import android.support.annotation.NonNull;
import android.text.Selection;
import android.text.Spannable;
import android.text.TextPaint;
import android.text.style.ClickableSpan;
import android.view.View;
import android.widget.TextView;

import name.walnut.kanjian.app.R;
import name.walnut.kanjian.app.utils.Logger;

/**
 * 评论昵称点击事件
 */
public class CommentClickableSpan extends ClickableSpan {

    private Context context;
    private long userId;    // 被点中的用户id

    public CommentClickableSpan(Context context, long userId) {
        this.context = context;
        this.userId = userId;
    }

    @Override
    public void onClick(View widget) {
        Logger.e(userId + "onClick");
        Spannable spannable = (Spannable) ((TextView) widget).getText();
        Selection.removeSelection(spannable);
    }

    @Override
    public void updateDrawState(@NonNull TextPaint ds) {
        super.updateDrawState(ds);
        ds.setColor(context.getResources().getColor(R.color.text_purple_dark));
        ds.setUnderlineText(false);
    }
}
