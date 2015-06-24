package name.walnut.kanjian.app.ui.upload;

import android.database.Cursor;
import android.graphics.Bitmap;
import android.media.ExifInterface;
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
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.InjectViews;
import butterknife.OnClick;
import name.walnut.kanjian.app.R;
import name.walnut.kanjian.app.resource.impl.Resource;
import name.walnut.kanjian.app.resource.impl.ResourceWeave;
import name.walnut.kanjian.app.support.ActionBarFragment;
import name.walnut.kanjian.app.support.KanJianApplication;
import name.walnut.kanjian.app.ui.upload.action.ResidueTimeAction;
import name.walnut.kanjian.app.ui.upload.action.SetSelectTimeAction;
import name.walnut.kanjian.app.ui.upload.action.UploadPhotoAction;
import name.walnut.kanjian.app.ui.util.ToastUtils;
import name.walnut.kanjian.app.utils.Logger;
import name.walnut.kanjian.app.utils.UriUtils;
import name.walnut.kanjian.app.utils.image.BitmapUtils;
import name.walnut.kanjian.app.utils.image.CompressOptions;
import name.walnut.kanjian.app.views.UploadPreviewRadioManager;
import name.walnut.kanjian.app.views.UploadPreviewView;

/**
 * 上传图片 fragment
 */
public class UploadFragment extends ActionBarFragment
        implements UploadPreviewRadioManager.OnCheckedChangeListener, UploadPreviewView.OnImageChangeListener{

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


    /**
     * 停止右上角刷新动画
     */
    public void stopAnimation() {
        refreshing = false;
        refreshBtn.setEnabled(true);
        Animation animation = refreshBtn.getAnimation();
        if (animation != null) {
            animation.cancel();
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // 上传图片路径
        UploadImageCache.INSTANCE.init(KanJianApplication.INSTANCE);

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
            previewView.setOnImageChangeListener(this);

            radioManager.register(previewView);
        }

        List<Uri> selected = UploadImageCache.INSTANCE.getImageUris();
        for (Uri uri : selected) {
            Logger.e("选中的图片路径：" + uri);
        }
        setImage(selected);   // 设置图片路径

        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (savedInstanceState == null) {
            // 获取剩余时间
            residueTimeResource.send();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        uploadBtn.requestFocus();
    }

    @OnClick(R.id.upload)
    void startUpload() {
        // 上传照片
        final UploadPreviewView checkedView = radioManager.getCheckedView();
        if (checkedView == null) {
            ToastUtils.toast(R.string.toast_error_unselected_image);
            return;
        }
        Uri uri = checkedView.getImgUri();
        String imagePath = UriUtils.getPath(getActionBarActivity(), uri);

        showMessage(R.string.dialog_message_upload_photo);

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


    // 重新选择图片后，缓存图片路径
    public void onImageReselect(List<Uri> imageUris) {
        if (imageUris != null) {

            // 在设置时间结果出来后，缓存
            showMessage(getString(R.string.dialog_message_upload_update_photo));

            UploadImageCache.INSTANCE.addToTmpCache(imageUris);
            updateRemoteSelectTime();
        }
    }

    /**
     * 更新服务器选择照片时间
     */
    public void updateRemoteSelectTime() {
        setSelectTimeResource.send();
    }

    /**
     * 设置图片
     * @param uris
     */
    public void setImage(List<Uri> uris) {
        int min = Math.min(uris.size(), previewViews.length);

        for (int i = 0; i < min; i++) {
            previewViews[i].setImageURI(uris.get(i));
        }
    }

    // 随机获取本地图库两张图片
    public List<Uri> getImagePath() {
        List<Uri> uriList = new ArrayList<>();

        Uri uri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
        Cursor cursor = getActionBarActivity().getContentResolver().query(uri, null,
                MediaStore.Images.Media.MIME_TYPE + "=? or "
                        + MediaStore.Images.Media.MIME_TYPE + "=?",
                new String[]{"image/jpeg", "image/png"}, MediaStore.Images.Media.DATE_MODIFIED);
        if(cursor == null){
            return uriList;
        }
        while (cursor.moveToNext()) {
            //获取图片的路径
            String path = cursor.getString(cursor
                    .getColumnIndex(MediaStore.Images.Media.DATA));

            if (filterPhoto(path)) {
                Uri imgUri = Uri.fromFile(new File(path));
                uriList.add(imgUri);
            }

        }
        cursor.close();

        Random random = new Random();

        int target1 = random.nextInt(uriList.size());
        int target2 = random.nextInt(uriList.size());

        List<Uri> result = new ArrayList<>();
        result.add(uriList.get(target1));
        result.add(uriList.get(target2));

        return result;
    }

    /**
     * 是否为手机照片
     * @param path
     * @return 如果符合要求，返回true
     */
    private boolean filterPhoto(String path) {
        try {
            ExifInterface exif = new ExifInterface(path);
            // 判断设备型号, 如果不为空，则认为是手机拍照照片
            String tagMake = exif.getAttribute(ExifInterface.TAG_MODEL);
            if (!TextUtils.isEmpty(tagMake)) {
                return true;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return false;
    }


    public void setResidueTime(long residueTime) {
        this.residueTime = residueTime;
    }

    @Override
    public void onCheckedChanged(UploadPreviewRadioManager manager, UploadPreviewView checkedView) {
    }

    @Override
    public void onImageChanged(UploadPreviewView view, Uri oldUri, Uri newUri) {
        boolean enable = true;
        for (UploadPreviewView previewView : previewViews) {
            enable = enable && previewView.isAvailableImage();
        }
        uploadBtn.setEnabled(enable);
    }
}
