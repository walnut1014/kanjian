package name.walnut.kanjian.app.ui.register;

import android.app.Activity;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import name.walnut.kanjian.app.R;
import name.walnut.kanjian.app.support.BusContext;
import name.walnut.kanjian.app.utils.Logger;

public class SelectPicDialog extends DialogFragment {

    private static final int SELECT_PIC_KITKAT = 1;
    private static final int SELECT_PIC = 2;

    @InjectView(R.id.popup_cancel)
    Button cancleBtn;
    @InjectView(R.id.popup_local_photo)
    Button localBtn;
    @InjectView(R.id.popup_take_photo)
    Button takePhotoBtn;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        LayoutInflater inflater =
                (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final View view = inflater.inflate(R.layout.popup_select_pic, null, false);

        ButterKnife.inject(this, view);

        Dialog dialog=new Dialog(getActivity());

        Window window = dialog.getWindow();
        window.requestFeature(Window.FEATURE_NO_TITLE);
        window.setGravity(Gravity.BOTTOM);
        window.setWindowAnimations(R.style.PopupAnimation);

        window.getDecorView().setPadding(0, 0, 0, 0);

        WindowManager.LayoutParams layoutParams = window.getAttributes();
        layoutParams.width = WindowManager.LayoutParams.MATCH_PARENT;
        layoutParams.height = WindowManager.LayoutParams.WRAP_CONTENT;
        window.setAttributes(layoutParams);

        dialog.setContentView(view);
        dialog.show();
        return dialog;
    }

    @OnClick(R.id.popup_cancel)
    void dismissDialog() {
        dismiss();
    }

    @OnClick(R.id.popup_local_photo)
    void localPhoto() {
        Intent intent=new Intent(Intent.ACTION_GET_CONTENT);//ACTION_OPEN_DOCUMENT
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        intent.setType("image/jpeg");
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
        intent.setAction(MediaStore.ACTION_IMAGE_CAPTURE);
        intent.addCategory(Intent.CATEGORY_DEFAULT);
//        // 根据文件地址创建文件
//        File file = new File(FILE_PATH);
//        if (file.exists()) {
//            file.delete();
//        }
//        // 把文件地址转换成Uri格式
//        Uri uri = Uri.fromFile(file);
//        // 设置系统相机拍摄照片完成后图片文件的存放地址
//        intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
        startActivityForResult(intent, 3);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Logger.d(requestCode+"");
        Logger.d(data+"");

        if (resultCode == Activity.RESULT_OK && data != null) {
            if (requestCode == 3) {
                BusContext.INSTANCE.getBus().post(data.getExtras());
            } else {
                BusContext.INSTANCE.getBus().post(data.getData());
            }
//            String imgPath = UriUtils.getPath(getActivity(), data.getData());
//            Logger.d(imgPath);
        }

        if (resultCode == Activity.RESULT_OK) {
            dismissDialog();

        }
    }
}
