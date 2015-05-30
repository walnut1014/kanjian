package name.walnut.kanjian.app.ui.upload;

import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.Button;
import android.widget.ImageButton;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.InjectViews;
import butterknife.OnClick;
import name.walnut.kanjian.app.R;
import name.walnut.kanjian.app.resource.impl.Resource;
import name.walnut.kanjian.app.resource.impl.ResourceWeave;
import name.walnut.kanjian.app.support.ActionBarFragment;
import name.walnut.kanjian.app.support.KJAlertDialogFragment;
import name.walnut.kanjian.app.ui.register.action.VerifyCodeAction;
import name.walnut.kanjian.app.ui.upload.action.ResidueTimeAction;
import name.walnut.kanjian.app.views.KJAlertDialog;
import name.walnut.kanjian.app.views.UploadPreviewRadioManager;
import name.walnut.kanjian.app.views.UploadPreviewView;

/**
 * 上传图片 fragment
 */
public class UploadFragment extends ActionBarFragment{

    private static final float AspectRatio = 1.42f; //照片显示比例

    private UploadPreviewRadioManager radioManager;

    private long residueTime; //上传上传距离24小时剩余时间

    @InjectViews({R.id.upload_image_1, R.id.upload_image_2})
    UploadPreviewView[] previewViews;
    @InjectView(R.id.upload)
    Button uploadBtn;

    @ResourceWeave(actionClass = ResidueTimeAction.class)
    public Resource residueTimeResource;

    @Override
    protected String getTitle() {
        return getString(R.string.title_activity_upload_pic);
    }

    @Override
    protected View getActionBarMenuView() {
        final ImageButton button = (ImageButton) LayoutInflater.from(getActionBarActivity())
                .inflate(R.layout.action_bar_menu_button, null);
        button.setImageResource(R.drawable.icon_refresh);
        final RotateAnimation animation = new RotateAnimation(
                0, 360f,
                Animation.RELATIVE_TO_SELF,
                0.5f,
                Animation.RELATIVE_TO_SELF,
                0.5f);
        animation.setDuration(300);
        animation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                showReselectDialog();
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
            }
        });
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                button.startAnimation(animation);
            }
        });
        return button;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_upload, container, false);
        ButterKnife.inject(this, view);

        radioManager = new UploadPreviewRadioManager();

        for (UploadPreviewView previewView : previewViews) {
            previewView.setAspectRatio(AspectRatio);
            previewView.setImageURI(null);

            radioManager.register(previewView);
        }

        setImage(getImagePath());

        uploadBtn.setEnabled(true);
        //获得剩余时间
        residueTimeResource.send();

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        uploadBtn.requestFocus();
    }

    @OnClick(R.id.upload)
    void startUpload() {
        if(residueTime == 0)
            //TODO 上传照片
            ;
        else
            showWaitDialog();
    }

    /**
     * 设置图片
     * @param uris
     */
    public void setImage(Uri[] uris) {
        int min = Math.min(uris.length, previewViews.length);

        for (int i = 0; i < min; i++) {
            previewViews[i].setImageURI(uris[i]);
        }

    }

    // 时间未够，等待
    private void showWaitDialog() {
        new KJAlertDialogFragment()
                .setContent(getString(R.string.dialog_upload_title_wait))
                .setPositiveText(getString(R.string.dialog_upload_wait_positive))
                .show(getFragmentManager());
    }

    // 已到24小时，重新挑选图片
    private void showReselectDialog() {
        new KJAlertDialogFragment()
                .setContent(getString(R.string.dialog_upload_title_reselect))
                .setPositiveText(getString(R.string.dialog_upload_reselect_positive))
                .setNegativeText(getString(R.string.dialog_upload_button_negative))
                .setPositiveClickListener(new KJAlertDialog.OnKJClickListener() {
                    @Override
                    public void onClick(KJAlertDialog dialog) {
                        // TODO 马上挑选

                    }
                })
                .show(getFragmentManager());
    }

    // 获取本地图库图片
    private Uri[] getImagePath() {
        List<Uri> uriList = new ArrayList<>();
        Uri[] uris = {};

        Uri uri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
        Cursor cursor = getActionBarActivity().getContentResolver().query(uri, null, MediaStore.Images.Media.MIME_TYPE + "=? or "
                        + MediaStore.Images.Media.MIME_TYPE + "=?",
                new String[]{"image/jpeg", "image/png"}, MediaStore.Images.Media.DATE_MODIFIED);
        if(cursor == null){
            return uris;
        }
        while (cursor.moveToNext()) {
            //获取图片的路径
            String path = cursor.getString(cursor
                    .getColumnIndex(MediaStore.Images.Media.DATA));

            Uri imgUri = Uri.fromFile(new File(path));
            uriList.add(imgUri);
        }
        cursor.close();
        uris = uriList.toArray(uris);
        return uris;
    }

    public void setResidueTime(long residueTime) {
        this.residueTime = residueTime;
    }
}
