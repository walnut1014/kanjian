package name.walnut.kanjian.app.ui.common;

import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import butterknife.ButterKnife;
import name.walnut.kanjian.app.R;

/**
 * 从底部弹出的menu
 */
public abstract class BaseBottomMenuDialogFragment extends DialogFragment{

    /**
     * dialog 布局
     * @return
     */
    protected abstract int getLayoutResId();

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        LayoutInflater inflater =
                (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final View view = inflater.inflate(getLayoutResId(), null, false);

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
        return dialog;
    }

}
