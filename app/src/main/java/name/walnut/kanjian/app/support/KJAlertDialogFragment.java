package name.walnut.kanjian.app.support;

import android.app.DialogFragment;
import android.app.FragmentManager;
import android.os.Bundle;

import name.walnut.kanjian.app.views.KJAlertDialog;

/**
 * 简单的提示对话框，可以设置内容和两个按钮文字
 * {@link name.walnut.kanjian.app.support.KJAlertDialogFragment#setContent(String)} 设置提示内容
 * {@link name.walnut.kanjian.app.support.KJAlertDialogFragment#setNegativeText(String)}设置右边按钮文字
 * {@link name.walnut.kanjian.app.support.KJAlertDialogFragment#setPositiveText(String)}设置左边按钮文字
 * {@link name.walnut.kanjian.app.support.KJAlertDialogFragment#showNegativeButton(boolean)}是否显示右边的按钮
 */
public class KJAlertDialogFragment extends DialogFragment {

    private String mContent;
    private String mNegativeStr;
    private String mPositiveStr;
    private boolean mShowNegative;

    private KJAlertDialog.OnKJClickListener mPositiveClickListener;
    private KJAlertDialog.OnKJClickListener mNegativeClickListener;

    @Override
    public KJAlertDialog onCreateDialog(Bundle savedInstanceState) {
        return new KJAlertDialog(getActivity())
                .setContent(mContent)
                .showNegativeButton(mShowNegative)
                .setNegativeText(mNegativeStr)
                .setPositiveText(mPositiveStr)
                .setNegativeClickListener(mNegativeClickListener)
                .setPositiveClickListener(mPositiveClickListener);
    }

    @Override
    public KJAlertDialog getDialog() {
        return (KJAlertDialog) super.getDialog();
    }

    public void show(FragmentManager manager) {
        super.show(manager, "dialog");
    }

    public KJAlertDialogFragment setContent(String content) {
        mContent = content;
        return this;
    }

    public KJAlertDialogFragment showNegativeButton(boolean show) {
        mShowNegative = show;
        return this;
    }

    public KJAlertDialogFragment setNegativeText(String text) {
        mNegativeStr = text;
        return this;
    }

    public KJAlertDialogFragment setPositiveText(String text) {
        mPositiveStr = text;
        return this;
    }

    public KJAlertDialogFragment setPositiveClickListener(KJAlertDialog.OnKJClickListener listener) {
        mPositiveClickListener = listener;
        return this;
    }

    public KJAlertDialogFragment setNegativeClickListener(KJAlertDialog.OnKJClickListener listener) {
        mNegativeClickListener = listener;
        return this;
    }
}
