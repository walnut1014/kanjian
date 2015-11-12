package name.walnut.kanjian.app.views;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;

import name.walnut.kanjian.app.R;

/**
 * 照片流添加评论View
 */
public class CommentView extends FrameLayout {

    private Button mSendBtn;
    private EditText mCommentEdit;
    private OnSendClickListener mSendClickListener;

    public interface OnSendClickListener {
        public void onSend(String message, CommentView view);
    }

    public CommentView(Context context) {
        super(context);
    }

    public CommentView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CommentView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs, defStyleAttr);
    }

    private void init(Context context, AttributeSet attrs, int defStyleAttr) {
        final LayoutInflater inflater = LayoutInflater.from(context);
        inflater.inflate(R.layout.view_comment, this, true);

        mSendBtn = (Button) findViewById(R.id.comment_send);
        mCommentEdit = (EditText) findViewById(R.id.comment_edit);

        mSendBtn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                String commentStr = mCommentEdit.getText().toString();
                if (mSendClickListener != null) {
                    mSendClickListener.onSend(commentStr, CommentView.this);
                }
            }
        });

    }

    /**
     * 清空评论区内容
     */
    public void resetComment() {
        mCommentEdit.setText("");
        mCommentEdit.setHint("");
    }

    /**
     * Sets the text to be displayed when the text of the TextView is empty.
     * @param hint
     */
    public void setHint(String hint) {
        mCommentEdit.setHint(hint);
    }

    /**
     * 设置文字
     * @param text
     */
    public void setText(CharSequence text) {
        mCommentEdit.setText(text);
    }

    public OnSendClickListener getSendClickListener() {
        return mSendClickListener;
    }

    public void setSendClickListener(OnSendClickListener sendClickListener) {
        this.mSendClickListener = sendClickListener;
    }

    @Override
    protected void onVisibilityChanged(View changedView, int visibility) {
        super.onVisibilityChanged(changedView, visibility);
        InputMethodManager inputMethodManager = (InputMethodManager) changedView.getContext()
                .getSystemService(Context.INPUT_METHOD_SERVICE);
        if (visibility == VISIBLE) {
            mCommentEdit.requestFocus();
            mCommentEdit.requestFocusFromTouch();
            inputMethodManager.showSoftInput(mCommentEdit, InputMethodManager.SHOW_FORCED);
        } else {

            inputMethodManager.hideSoftInputFromWindow(getWindowToken(), 0);
        }
    }
}
