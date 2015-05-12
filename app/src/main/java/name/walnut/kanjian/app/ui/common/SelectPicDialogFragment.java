package name.walnut.kanjian.app.ui.common;

import android.app.Activity;
import android.app.FragmentManager;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.widget.Button;

import butterknife.InjectView;
import butterknife.OnClick;
import name.walnut.kanjian.app.R;

public class SelectPicDialogFragment extends BaseBottomMenuDialogFragment {

    private static final int SELECT_PIC_KITKAT = 1; // Android4.4 从图库选择图片
    private static final int SELECT_PIC = 2;    // Android 从图库选择图片
    private static final int TAKE_PHOTO = 3;    // 拍照
    private static final int CROP_PHOTO = 4;    // 截图

    private static final String IMAGE_FILE_LOCATION = "file:///sdcard/temp.jpg";    // 临时图片路径
    private static final int IMAGE_SIDE = 276;  // 头像边长

    private Uri imageUri;   //选中的图片地址

    private SelectPicListener listener;

    @InjectView(R.id.popup_cancel)
    Button cancleBtn;
    @InjectView(R.id.popup_local_photo)
    Button localBtn;
    @InjectView(R.id.popup_take_photo)
    Button takePhotoBtn;

    public static SelectPicDialogFragment showDialog(FragmentManager fragmentManager, SelectPicListener listener) {
        // 显示选择框
        SelectPicDialogFragment dialog = new SelectPicDialogFragment();
        dialog.show(fragmentManager, "dialog");
        dialog.listener = listener;
        return dialog;
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.popup_select_pic;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        imageUri = Uri.parse(IMAGE_FILE_LOCATION);
    }

    @OnClick(R.id.popup_cancel)
    void dismissDialog() {
        dismiss();
    }

    @OnClick(R.id.popup_local_photo)
    void localPhoto() {
        Intent intent=new Intent(Intent.ACTION_GET_CONTENT);//ACTION_OPEN_DOCUMENT
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        intent.setType("image/*");
        if(android.os.Build.VERSION.SDK_INT>=android.os.Build.VERSION_CODES.KITKAT){
            startActivityForResult(intent, SELECT_PIC_KITKAT);
        }else{
            startActivityForResult(intent, SELECT_PIC);
        }
    }

    @OnClick(R.id.popup_take_photo)
    void takePhoto() {
        Intent intent = new Intent();
        // 指定开启系统相机的Action
        intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
        intent.setAction(MediaStore.ACTION_IMAGE_CAPTURE);
        intent.addCategory(Intent.CATEGORY_DEFAULT);

        startActivityForResult(intent, TAKE_PHOTO);
    }

    // 调用系统裁减图片
    private void cropImageUri(Uri data, Uri uri, int outputX, int outputY, int requestCode){
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(data, "image/*");
        intent.putExtra("crop", "true");
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);
        intent.putExtra("outputX", outputX);
        intent.putExtra("outputY", outputY);
        intent.putExtra("scale", true);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
        intent.putExtra("return-data", false);
        intent.putExtra("outputFormat", Bitmap.CompressFormat.JPEG.toString());
        intent.putExtra("noFaceDetection", true); // no face detection
        startActivityForResult(intent, requestCode);
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == Activity.RESULT_OK) {
            switch (requestCode) {
                case TAKE_PHOTO:
                    cropImageUri(imageUri, imageUri, IMAGE_SIDE, IMAGE_SIDE, CROP_PHOTO);
                    break;
                case SELECT_PIC:
                case SELECT_PIC_KITKAT:
                    cropImageUri(data.getData(),imageUri, IMAGE_SIDE, IMAGE_SIDE, CROP_PHOTO);
                    break;
                case CROP_PHOTO:
                    selectPhoto(imageUri);
                    dismissDialog();
                    break;
            }
        }
    }

    public void selectPhoto(Uri uri) {
        if (listener != null) {
            listener.onSelect(uri);
        }
    }

    public interface SelectPicListener {
        public void onSelect(Uri uri) ;
    }
}
