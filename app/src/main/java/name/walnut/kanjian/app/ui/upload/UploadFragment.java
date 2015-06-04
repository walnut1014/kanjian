package name.walnut.kanjian.app.ui.upload;

import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
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
import name.walnut.kanjian.app.ui.upload.action.ResidueTimeAction;
import name.walnut.kanjian.app.ui.upload.action.SetSelectTimeAction;
import name.walnut.kanjian.app.ui.upload.action.UploadPhotoAction;
import name.walnut.kanjian.app.ui.util.ToastUtils;
import name.walnut.kanjian.app.utils.Logger;
import name.walnut.kanjian.app.utils.UriUtils;
import name.walnut.kanjian.app.utils.image.BitmapUtils;
import name.walnut.kanjian.app.utils.image.CompressOptions;
import name.walnut.kanjian.app.views.KJAlertDialog;
import name.walnut.kanjian.app.views.UploadPreviewRadioManager;
import name.walnut.kanjian.app.views.UploadPreviewView;

/**
 * 上传图片 fragment
 */
public class UploadFragment extends ActionBarFragment implements UploadPreviewRadioManager.OnCheckedChangeListener{

    private static final float AspectRatio = 1.42f; //照片显示比例

    private UploadPreviewRadioManager radioManager;

    private long residueTime; //上传上传距离24小时剩余时间

    @InjectViews({R.id.upload_image_1, R.id.upload_image_2})
    UploadPreviewView[] previewViews;
    @InjectView(R.id.upload)
    Button uploadBtn;

    private ImageButton refreshBtn;
    private boolean refreshing = false;

    @ResourceWeave(actionClass = ResidueTimeAction.class)
    public Resource residueTimeResource;

    @ResourceWeave(actionClass = SetSelectTimeAction.class)
    public Resource setSelectTimeResource;

    @ResourceWeave(actionClass = UploadPhotoAction.class)
    public Resource uploadPhotoResource;

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
        animation.setRepeatMode(Animation.RESTART);
        animation.setRepeatCount(Animation.INFINITE);
        animation.setInterpolator(new LinearInterpolator());
        animation.setDuration(300);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 获得用户还剩多长时间可以上传照片
                refreshing = true;
                button.setEnabled(false);
                button.startAnimation(animation);
                residueTimeResource.send();
            }
        });
        if (refreshing) {
            button.setEnabled(false);
            button.startAnimation(animation);
        }
        refreshBtn = button;
        return button;
    }

    // 停止刷新动画
    public void stopAnimation() {
        refreshing = false;
        refreshBtn.setEnabled(true);
        Animation animation = refreshBtn.getAnimation();
        if (animation != null) {
            animation.cancel();
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_upload, container, false);
        ButterKnife.inject(this, view);
        uploadBtn.setEnabled(false);

        radioManager = new UploadPreviewRadioManager();
        radioManager.setOnCheckedChangeListener(this);

        for (UploadPreviewView previewView : previewViews) {
            previewView.setAspectRatio(AspectRatio);
            previewView.setImageURI(null);

            radioManager.register(previewView);
        }

        setImage(getImagePath());   // todo 设置图片路径
        setSelectTimeResource.send();
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        uploadBtn.requestFocus();
    }

    @OnClick(R.id.upload)
    void startUpload() {
        showMessage(R.string.dialog_message_upload_photo);
        // 上传照片
        final UploadPreviewView checkedView = radioManager.getCheckedView();
        Uri uri = checkedView.getImgUri();
        String imagePath = UriUtils.getPath(getActionBarActivity(), uri);


        new AsyncTask<String, Void, String>() {

            @Override
            protected String doInBackground(String... params) {

                CompressOptions options = new CompressOptions(1600, 1600, 80, Bitmap.CompressFormat.JPEG);

                String scaleImgPath =  BitmapUtils.decodeFile(getActionBarActivity(), params[0], "tmp.tmp", options);

                Logger.i("压缩前："+BitmapUtils.getBitmapInfo(params[0]));
                Logger.i("压缩后："+BitmapUtils.getBitmapInfo(scaleImgPath));

                return scaleImgPath;
            }

            @Override
            protected void onPostExecute(String str) {
                super.onPostExecute(str);

                if (TextUtils.isEmpty(str)) {
                    ToastUtils.toast("加载图片出错！");
                    return;
                }

                File image = new File(str);
                String content = checkedView.getDescription();

                uploadPhotoResource.addParam("photo", image)
                        .addParam("content", content)
                        .send();
            }

        }.execute(imagePath);
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
    public void showWaitDialog(int hour, int minute) {
        new KJAlertDialogFragment()
                .setContent(getString(R.string.dialog_upload_title_wait, hour, minute))
                .setPositiveText(getString(R.string.dialog_upload_wait_positive))
                .show(getFragmentManager());
    }

    // 已到24小时，重新挑选图片
    public void showReselectDialog() {
        new KJAlertDialogFragment()
                .setContent(getString(R.string.dialog_upload_title_reselect))
                .setPositiveText(getString(R.string.dialog_upload_reselect_positive))
                .setNegativeText(getString(R.string.dialog_upload_button_negative))
                .setPositiveClickListener(new KJAlertDialog.OnKJClickListener() {
                    @Override
                    public void onClick(KJAlertDialog dialog) {
                        // TODO 马上挑选
                        startReselectPhoto();
                    }
                })
                .show(getFragmentManager());
    }

    // 重新挑选图片
    private void startReselectPhoto() {

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

    @Override
    public void onCheckedChanged(UploadPreviewRadioManager manager, UploadPreviewView checkedView) {
        uploadBtn.setEnabled(checkedView != null);
    }
}
