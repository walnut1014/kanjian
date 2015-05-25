package name.walnut.kanjian.app.ui.upload;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.InjectViews;
import name.walnut.kanjian.app.R;
import name.walnut.kanjian.app.support.ActionBarFragment;
import name.walnut.kanjian.app.support.KJAlertDialogFragment;
import name.walnut.kanjian.app.views.UploadPreviewView;

/**
 * 上传图片 fragment
 */
public class UploadFragment extends ActionBarFragment{

    private static final float AspectRatio = 1.42f;

    @InjectViews({R.id.upload_image_1, R.id.upload_image_2})
    UploadPreviewView[] previewViews;
    @InjectView(R.id.upload)
    Button uploadBtn;

    @Override
    protected String getTitle() {
        return getString(R.string.title_activity_upload_pic);
    }

    @Override
    protected View getActionBarMenuView() {
        ImageButton button = (ImageButton) LayoutInflater.from(getActionBarActivity())
                .inflate(R.layout.action_bar_menu_button, null);
        button.setImageResource(R.drawable.ic_tab_camera);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new KJAlertDialogFragment()
                        .setContent(getString(R.string.dialog_upload_title_reselect))
                        .setPositiveText(getString(R.string.dialog_upload_button_positive))
                        .setNegativeText(getString(R.string.dialog_upload_button_negative))
                        .show(getFragmentManager());
            }
        });
        return button;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_upload, container, false);
        ButterKnife.inject(this, view);

        for (UploadPreviewView previewView : previewViews) {
            previewView.setAspectRatio(AspectRatio);
            previewView.setImageURI(null);
        }

        return view;
    }
}
