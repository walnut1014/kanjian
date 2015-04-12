package name.walnut.kanjian.app.views;

import android.content.Context;
import android.content.res.TypedArray;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageButton;

import name.walnut.kanjian.app.R;

/**
 * 带有清除按钮的edittext
 * app:hint
 * android.R.attr.textSize,
 * android.R.attr.textColor,
 * android.R.attr.focusable,
 * android.R.attr.clickable,
 * android.R.attr.text,
 * android.R.attr.inputType,
 */
public class ClearEditText extends FrameLayout {

    private static final int[] ATTRS = new int[] {
            android.R.attr.textSize,
            android.R.attr.textColor,
            android.R.attr.focusable,
            android.R.attr.clickable,
            android.R.attr.text,
            android.R.attr.inputType,
    };

    private EditText mEditText;
    private ImageButton mClearButton;

    private int textSize = 18;
    private int textColor = 0xFF040404;
    private boolean focusable = true;
    private boolean clickable = true;
    private CharSequence text = "";
    private int inputType = EditorInfo.TYPE_CLASS_TEXT;
    private CharSequence hint = "";


    public ClearEditText(Context context) {
        super(context);
        init(context, null, 0);
    }

    public ClearEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs, 0);
    }

    public ClearEditText(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(context, attrs, defStyle);
    }

    private void init(Context context, AttributeSet attrs, int defStyle) {
        TypedArray a = context.obtainStyledAttributes(
                attrs, R.styleable.ClearEditText, defStyle, 0);

        final LayoutInflater inflater = (LayoutInflater) context.getSystemService(
                Context.LAYOUT_INFLATER_SERVICE);
        final int layoutResId = R.layout.view_clear_edit_text;
        inflater.inflate(layoutResId, this, true);

        mEditText = (EditText) this.findViewById(R.id.editText);
        mClearButton = (ImageButton) this.findViewById(R.id.clear_button);

        hint = a.getString(R.styleable.ClearEditText_hint);
        a.recycle();

        a = context.obtainStyledAttributes(attrs, ATTRS);

        DisplayMetrics dm = getResources().getDisplayMetrics();
        textSize = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, textSize, dm);

        textSize = a.getDimensionPixelSize(0, textSize);
        textColor = a.getColor(1, textColor);
        focusable = a.getBoolean(2, focusable);
        clickable = a.getBoolean(3, clickable);
        text = a.getText(4);
        inputType = a.getInt(5, inputType);

        a.recycle();


        mEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (TextUtils.isEmpty(s.toString())) {
                    mClearButton.setVisibility(INVISIBLE);
                } else {
                    mClearButton.setVisibility(VISIBLE);
                }
            }
        });
        mClearButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                mEditText.setText("");
                mEditText.requestFocus();
            }
        });

        mClearButton.setVisibility(INVISIBLE);

        mEditText.setTextSize(TypedValue.COMPLEX_UNIT_PX, textSize);
        mEditText.setTextColor(textColor);
        mEditText.setFocusable(focusable);
        mEditText.setClickable(clickable);
        mEditText.setText(text);
        mEditText.setInputType(inputType);

        mEditText.setHint(hint);

    }

    public EditText getEditText() {
        return mEditText;
    }

    public void setEditText(String text) {
        mEditText.setText(text);
    }

}
