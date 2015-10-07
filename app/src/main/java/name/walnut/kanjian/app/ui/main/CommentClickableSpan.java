package name.walnut.kanjian.app.ui.main;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.text.Selection;
import android.text.Spannable;
import android.text.TextPaint;
import android.text.style.ClickableSpan;
import android.view.View;
import android.widget.TextView;

import name.walnut.kanjian.app.R;
import name.walnut.kanjian.app.ui.Constants;
import name.walnut.kanjian.app.utils.Logger;

/**
 * 评论昵称点击事件
 */
public class CommentClickableSpan extends ClickableSpan {

    private Context context;
    private long userId;    // 被点中的用户id
    private String userName;    //被点中用户昵称

    public CommentClickableSpan(Context context, long userId, String username) {
        this.context = context;
        this.userId = userId;
        this.userName = username;
    }

    @Override
    public void onClick(View widget) {
        Logger.e(userId + "onClick");
        CharSequence text = ((TextView) widget).getText();
        Spannable spannable = (Spannable) text;
        Selection.removeSelection(spannable);
        startPersonalPageActivity(context, userId, userName);
    }

    @Override
    public void updateDrawState(@NonNull TextPaint ds) {
        super.updateDrawState(ds);
        ds.setColor(context.getResources().getColor(R.color.white));
        ds.setUnderlineText(false);
    }

    /**
     * 跳转到个人主页
     * @param context
     * @param userId
     * @param userName
     */
    private void startPersonalPageActivity(Context context, long userId, String userName) {
        Intent intent = new Intent();
        intent.putExtra("userId", userId);
        intent.putExtra("userName", userName);
        intent.setAction(Constants.Action.PERSONAL_PAGE_ACTION);
        context.startActivity(intent);
    }
}
