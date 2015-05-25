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

    public UploadPreviewRadioManager() {
        viewList = new ArrayList<>();
    }

    public UploadPreviewRadioManager register(UploadPreviewView view) {
        boolean contain = viewList.contains(view);
        if (!contain) {
            viewList.add(view);
            view.setOnCheckedChangeListener(this);
        }
        return this;
    }

    @Override
    public void onCheckedChanged(UploadPreviewView buttonView, boolean isChecked) {
        if (isChecked) {
            for (UploadPreviewView previewView : viewList) {
                if (previewView != buttonView) {
                    previewView.setChecked(false);
                }
            }
        }
    }
}
