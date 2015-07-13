package name.walnut.kanjian.app.ui.main;

import android.app.Dialog;
import android.app.DialogFragment;
import android.app.FragmentManager;
import android.content.Context;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.facebook.drawee.view.SimpleDraweeView;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import name.walnut.kanjian.app.R;
import name.walnut.kanjian.app.account.Account;
import name.walnut.kanjian.app.ui.Constants;

/**
 * 显示头像详情dialog fragment
 */
public class DetailPhotoDialogFragment extends DialogFragment {

    private static final String KEY_PHOTO = "photo";

    @InjectView(R.id.photo)
    SimpleDraweeView photoView;

    public static DetailPhotoDialogFragment showDialog(FragmentManager fragmentManager, String photoPath) {
        DetailPhotoDialogFragment fragment = new DetailPhotoDialogFragment();
        Bundle bundle = new Bundle();
        bundle.putString(KEY_PHOTO, photoPath);
        fragment.setArguments(bundle);
        fragment.show(fragmentManager, "dialog");
        return fragment;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        final String photoPath = getArguments().getString(KEY_PHOTO);

        LayoutInflater inflater =
                (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final View view = inflater.inflate(R.layout.dialog_detail_photo, null, false);

        ButterKnife.inject(this, view);

        Dialog dialog=new Dialog(getActivity());

        Window window = dialog.getWindow();
        window.requestFeature(Window.FEATURE_NO_TITLE);
        window.setGravity(Gravity.BOTTOM);
        window.setWindowAnimations(R.style.PhotoPopupAnimation);

        window.getDecorView().setPadding(0, 0, 0, 0);

        WindowManager.LayoutParams layoutParams = window.getAttributes();
        layoutParams.width = WindowManager.LayoutParams.MATCH_PARENT;
        layoutParams.height = WindowManager.LayoutParams.MATCH_PARENT;
        window.setAttributes(layoutParams);

        dialog.setContentView(view);

        photoView.setImageURI(Constants.getFileUri(photoPath));

        return dialog;
    }

    @OnClick(R.id.photo_container)
    void dismissDialog() {
        dismiss();
    }

}
