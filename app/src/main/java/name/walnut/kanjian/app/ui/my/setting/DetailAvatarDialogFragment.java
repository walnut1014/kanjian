package name.walnut.kanjian.app.ui.my.setting;

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
public class DetailAvatarDialogFragment extends DialogFragment {

    @InjectView(R.id.avatar_detail)
    SimpleDraweeView avatarView;

    public static DetailAvatarDialogFragment showDialog(FragmentManager fragmentManager) {
        DetailAvatarDialogFragment fragment = new DetailAvatarDialogFragment();
        fragment.show(fragmentManager, "dialog");
        return fragment;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        LayoutInflater inflater =
                (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final View view = inflater.inflate(R.layout.dialog_detail_avatar, null, false);

        ButterKnife.inject(this, view);

        Dialog dialog=new Dialog(getActivity());

        Window window = dialog.getWindow();
        window.requestFeature(Window.FEATURE_NO_TITLE);
        window.setGravity(Gravity.BOTTOM);
        window.setWindowAnimations(R.style.AvatarPopupAnimation);

        window.getDecorView().setPadding(0, 0, 0, 0);

        WindowManager.LayoutParams layoutParams = window.getAttributes();
        layoutParams.width = WindowManager.LayoutParams.MATCH_PARENT;
        layoutParams.height = WindowManager.LayoutParams.MATCH_PARENT;
        window.setAttributes(layoutParams);

        dialog.setContentView(view);

        avatarView.setAspectRatio(1.0f);
        avatarView.setImageURI(Constants.getFileUri(Account.INSTANCE.getHeadPhotoPath()));

        return dialog;
    }

    @OnClick(R.id.avatar_container)
    void dismissDialog() {
        dismiss();
    }

}
