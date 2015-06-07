package name.walnut.kanjian.app.views;

import android.content.Context;
import android.content.res.TypedArray;
import android.net.Uri;
import android.text.InputFilter;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.CheckBox;
import android.widget.Checkable;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.FrameLayout;

import com.facebook.drawee.view.SimpleDraweeView;

import java.net.URI;

import m.framework.ui.widget.asyncview.OnImageGotListener;
import name.walnut.kanjian.app.R;

/**
 * 上传照片界面，单个照片预览
 */
public class UploadPreviewView extends FrameLayout implements Checkable{

    private static final InputFilter[] NO_FILTERS = new InputFilter[0];

    private boolean mChecked;
    private boolean mShowDescription;
    private boolean mShowChecked;
    private Uri mImgUri;

    private EditText mTextView;
    private CheckBox mCheckBox;
    private SimpleDraweeView mImageView;
    private OnCheckedChangeListener mOnCheckedChangeListener;
    private OnImageChangeListener mOnImageChangeListener;

    public interface OnCheckedChangeListener {
        void onCheckedChanged(UploadPreviewView buttonView, boolean isChecked);
    }

    /**
     * 图片改变监听器
     */
    public interface OnImageChangeListener {
        void onImageChanged(UploadPreviewView view, Uri oldUri, Uri newUri);
    }

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
        mCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                setChecked(isChecked);
            }
        });

        final TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.UploadPreviewView, defStyleAttr, 0);

        final boolean checked = a.getBoolean(R.styleable.UploadPreviewView_checked, false);
        final int maxLength = a.getInteger(R.styleable.UploadPreviewView_maxLength, -1);

        a.recycle();

        setChecked(checked);

        if (maxLength >= 0) {
            mTextView.setFilters(new InputFilter[]{new InputFilter.LengthFilter(maxLength)});
        } else {
            mTextView.setFilters(NO_FILTERS);
        }
    }

    @Override
    public void setChecked(boolean checked) {
        if (mChecked != checked && mOnCheckedChangeListener != null) {
            mOnCheckedChangeListener.onCheckedChanged(this, checked);
        }
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

        // 判断uri是否为有效图片路径，
        if (!isAvailableImage(uri)) {
            uri = null;
        }

        Uri oldUri = mImgUri;
        Uri newUri = uri;

        mImgUri = newUri;
        mImageView.setImageURI(newUri);
        boolean show = newUri != null;
        showChecked(show);
        showDescription(show);

        if (mOnImageChangeListener != null && newUri != oldUri) {
            mOnImageChangeListener.onImageChanged(this, oldUri, newUri);
        }
    }

    private boolean isAvailableImage(Uri uri) {
        // TODO 判断uri是否为有效图片路径
        return uri != null;
    }

    public Uri getImgUri() {
        return mImgUri;
    }

    /**
     * 判断图片是否存在
     * @return
     */
    public boolean isAvailableImage() {
        return isAvailableImage(mImgUri);
    }

    /**
     * 获取图片描述
     * @return
     */
    public String getDescription() {
        return mTextView.getText().toString();
    }

    /**
     * 设置宽高比
     * @param aspectRatio
     */
    public void setAspectRatio(float aspectRatio) {
        mImageView.setAspectRatio(aspectRatio);
    }

    public void setOnCheckedChangeListener(OnCheckedChangeListener listener) {
        this.mOnCheckedChangeListener = listener;
    }

    public void setOnImageChangeListener(OnImageChangeListener onImageChangeListener) {
        this.mOnImageChangeListener = onImageChangeListener;
    }
}
