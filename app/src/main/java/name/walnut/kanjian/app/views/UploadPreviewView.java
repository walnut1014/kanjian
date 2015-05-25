package name.walnut.kanjian.app.views;

import android.content.Context;
import android.content.res.TypedArray;
import android.net.Uri;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.CheckBox;
import android.widget.Checkable;
import android.widget.EditText;
import android.widget.FrameLayout;

import com.facebook.drawee.view.SimpleDraweeView;

import name.walnut.kanjian.app.R;

/**
 * 上传照片界面，单个照片预览
 */
public class UploadPreviewView extends FrameLayout implements Checkable{

    private boolean mChecked;
    private boolean mShowDescription;
    private boolean mShowChecked;

    private EditText mTextView;
    private CheckBox mCheckBox;
    private SimpleDraweeView mImageView;

    public UploadPreviewView(Context context) {
        super(context);
    }

    public UploadPreviewView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public UploadPreviewView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs, defStyleAttr);
    }

    private void init(Context context, AttributeSet attrs, int defStyleAttr) {
        final LayoutInflater inflater = LayoutInflater.from(context);
        final int layoutId = R.layout.layout_upload_preview;

        inflater.inflate(layoutId, this, true);

        mTextView = (EditText) findViewById(R.id.upload_text);
        mCheckBox = (CheckBox) findViewById(R.id.upload_check);
        mImageView = (SimpleDraweeView) findViewById(R.id.upload_image);
        setImageURI(null);

        mTextView.setOnTouchListener(new OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent event) {
                view.getParent().requestDisallowInterceptTouchEvent(true);
                switch (event.getAction() & MotionEvent.ACTION_MASK){
                    case MotionEvent.ACTION_UP:
                        view.getParent().requestDisallowInterceptTouchEvent(false);
                        break;

                }
                return false;
            }
        });

        final TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.UploadPreviewView, defStyleAttr, 0);

        final boolean checked = a.getBoolean(R.styleable.UploadPreviewView_checked, false);
        setChecked(checked);

        a.recycle();

    }

    @Override
    public void setChecked(boolean checked) {
        mChecked = checked;
        mCheckBox.setChecked(checked);
    }

    @Override
    public boolean isChecked() {
        return mChecked;
    }

    @Override
    public void toggle() {
        setChecked(!mChecked);
    }

    /**
     * 是否显示文字编辑框
     * @param show
     */
    public void showDescription(boolean show) {
        mShowDescription = show;
        int visibility = show ? VISIBLE : GONE;
        mTextView.setVisibility(visibility);
    }

    /**
     * 显示选择框
     * @param show
     */
    public void showChecked(boolean show) {
        mShowChecked = show;
        int visibility = show ? VISIBLE : GONE;
        mCheckBox.setVisibility(visibility);
    }

    /**
     * 设置图片
     * @param uri
     */
    public void setImageURI(Uri uri) {
        mImageView.setImageURI(uri);
        boolean show = uri != null;
        showChecked(show);
        showDescription(show);
    }

    /**
     * 设置宽高比
     * @param aspectRatio
     */
    public void setAspectRatio(float aspectRatio) {
        mImageView.setAspectRatio(aspectRatio);
    }
}
