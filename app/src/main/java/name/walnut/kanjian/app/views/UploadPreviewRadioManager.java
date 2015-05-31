package name.walnut.kanjian.app.views;

import java.util.ArrayList;
import java.util.List;

/**
 * 管理{@link name.walnut.kanjian.app.views.UploadPreviewView}按钮组单选状态
 * todo 因为使用{@link name.walnut.kanjian.app.views.UploadPreviewView.OnCheckedChangeListener},
 *      如果该监听器被覆盖，将无法正常监听
 */
public class UploadPreviewRadioManager implements UploadPreviewView.OnCheckedChangeListener{
    private List<UploadPreviewView> viewList;
    private UploadPreviewView checkedView;
    private OnCheckedChangeListener onCheckedChangeListener;

    public interface OnCheckedChangeListener {
        /**
         * 选中的按钮改变时回调
         * @param manager
         * @param checkedView 被选中的按钮，或者null
         */
        public void onCheckedChanged(UploadPreviewRadioManager manager, UploadPreviewView checkedView);
    }

    public UploadPreviewRadioManager() {
        viewList = new ArrayList<>();
        checkedView = null;
    }

    /**
     * 注册view到{@link name.walnut.kanjian.app.views.UploadPreviewRadioManager}中，会将选中状态置为未选中
     * @param view
     * @return
     */
    public UploadPreviewRadioManager register(UploadPreviewView view) {
        boolean contain = viewList.contains(view);
        if (!contain) {
            view.setChecked(false);
            viewList.add(view);
            view.setOnCheckedChangeListener(this);
        }
        return this;
    }

    public UploadPreviewView getCheckedView() {
        return checkedView;
    }

    @Override
    public void onCheckedChanged(UploadPreviewView buttonView, boolean isChecked) {
        boolean changed = false;
        if (isChecked) {
            if (checkedView != buttonView) {
                checkedView = buttonView;
                changed = true;
            }
            // 将其他按钮置为未选中
            for (UploadPreviewView previewView : viewList) {
                if (previewView != buttonView) {
                    previewView.setChecked(false);
                }
            }

        } else {
            if (checkedView == buttonView) {
                changed = true;
                checkedView = null;
            }
        }
        if (changed && onCheckedChangeListener != null) {
            onCheckedChangeListener.onCheckedChanged(this, checkedView);
        }
    }

    public void setOnCheckedChangeListener(OnCheckedChangeListener onCheckedChangeListener) {
        this.onCheckedChangeListener = onCheckedChangeListener;
    }
}
