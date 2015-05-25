package name.walnut.kanjian.app.views;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import name.walnut.kanjian.app.R;

/**
 * 自定义对话框
 */
public class KJAlertDialog extends Dialog implements View.OnClickListener{

    private View mDialogView;
    private TextView mContentTv;
    private Button mNegativeBtn;
    private Button mPositiveBtn;

    private String mContent;
    private String mNegativeStr;
    private String mPositiveStr;
    private boolean mShowNegative;

    private OnKJClickListener mPositiveClickListener;
    private OnKJClickListener mNegativeClickListener;

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.negative_button:
                if (mNegativeClickListener != null) {
                    mNegativeClickListener.onClick(this);
                }
                dismiss();
                break;
            case R.id.positive_button:
                if (mPositiveClickListener != null) {
                    mPositiveClickListener.onClick(this);
                }
                dismiss();
                break;
        }
    }

    public static interface OnKJClickListener {
        public void onClick (KJAlertDialog dialog);
    }

    public KJAlertDialog(Context context) {
        super(context, R.style.IAlertDialog);

        setCancelable(false);
        setCanceledOnTouchOutside(false);

        Window window = getWindow();
        window.requestFeature(Window.FEATURE_NO_TITLE);

        int padding = getContext().getResources().getDimensionPixelSize(R.dimen.dialog_login_alert_padding);

        window.getDecorView().setPadding(padding, 0, padding, 0);

        WindowManager.LayoutParams layoutParams = window.getAttributes();
        layoutParams.width = WindowManager.LayoutParams.MATCH_PARENT;
        layoutParams.height = WindowManager.LayoutParams.WRAP_CONTENT;
        window.setAttributes(layoutParams);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.alert_dialog);

        mDialogView = getWindow().getDecorView().findViewById(android.R.id.content);
        mContentTv = (TextView) findViewById(R.id.content);
        mNegativeBtn = (Button) findViewById(R.id.negative_button);
        mPositiveBtn = (Button) findViewById(R.id.positive_button);

        setContent(mContent);
        setNegativeText(mNegativeStr);
        setPositiveText(mPositiveStr);
        showNegativeButton(mShowNegative);

        mNegativeBtn.setOnClickListener(this);
        mPositiveBtn.setOnClickListener(this);
    }

    public KJAlertDialog setContent(String content) {
        mContent = content;
        if (mContentTv != null) {
            mContentTv.setText(content);
        }
        return this;
    }

    public KJAlertDialog showNegativeButton(boolean show) {
        mShowNegative = show;
        if (mNegativeBtn != null) {
            mNegativeBtn.setVisibility(show ? View.VISIBLE : View.GONE);
        }
        return this;
    }

    public KJAlertDialog setNegativeText(String text) {
        mNegativeStr = text;
        if (mNegativeBtn != null && text != null) {
            showNegativeButton(true);
            mNegativeBtn.setText(text);
        }
        return this;
    }

    public KJAlertDialog setPositiveText(String text) {
        mPositiveStr = text;
        if (mPositiveBtn != null && text != null) {
            mPositiveBtn.setText(text);
        }
        return this;
    }

    public KJAlertDialog setPositiveClickListener(OnKJClickListener listener) {
        mPositiveClickListener = listener;
        return this;
    }

    public KJAlertDialog setNegativeClickListener(OnKJClickListener listener) {
        mNegativeClickListener = listener;
        return this;
    }

}
