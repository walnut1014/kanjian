package name.walnut.kanjian.app.ui.my.relation.newfriends;

import android.app.Dialog;
import android.app.DialogFragment;
import android.app.FragmentManager;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import butterknife.OnEditorAction;
import butterknife.OnTextChanged;
import name.walnut.kanjian.app.R;
import name.walnut.kanjian.app.support.ActionBarActivity;

/**
 * 搜索fragment
 */
public class SearchDialogFragment extends DialogFragment {

    @InjectView(R.id.search_back)
    ImageButton backBtn;
    @InjectView(R.id.search_edit)
    EditText searchEdit;
    @InjectView(R.id.search_cancel)
    Button cancelBtn;
    @InjectView(R.id.search_text)
    TextView searchContentTv;

    public static SearchDialogFragment show(FragmentManager manager) {
        SearchDialogFragment fragment = new SearchDialogFragment();
        fragment.show(manager, "dialog");
        return fragment;
    }


    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        LayoutInflater inflater =
                (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final View view = inflater.inflate(R.layout.fragment_friend_search, null, false);

        ButterKnife.inject(this, view);
        searchEdit.setText("13000000003");

        Dialog dialog=new Dialog(getActivity(), R.style.SearchDialog);

        Window window = dialog.getWindow();
        window.requestFeature(Window.FEATURE_NO_TITLE);
        window.setGravity(Gravity.TOP);
//        window.setWindowAnimations(R.style.PopupAnimation);

        window.getDecorView().setPadding(0, 0, 0, 0);

        WindowManager.LayoutParams layoutParams = window.getAttributes();
        layoutParams.width = WindowManager.LayoutParams.MATCH_PARENT;
        layoutParams.height = WindowManager.LayoutParams.MATCH_PARENT;
        window.setAttributes(layoutParams);

        dialog.setContentView(view);

        return dialog;
    }

    @OnClick(R.id.search_back)
    void back() {
        dismiss();
    }

    @OnClick(R.id.search_cancel)
    void cancel() {
        dismiss();
    }

    @OnTextChanged(R.id.search_edit)
    void onEdit(CharSequence text) {
        if (TextUtils.isEmpty(text)) {
            searchContentTv.setVisibility(View.GONE);
        } else {
            searchContentTv.setVisibility(View.VISIBLE);
            searchContentTv.setText("搜索："+text);
        }
    }

    @OnClick(R.id.search_text)
    void search() {
        dismiss();
        String queryStr = searchEdit.getText().toString();
        ActionBarActivity activity = (ActionBarActivity) getActivity();
        activity.switchFragment(SearchResultFragment.newInstance(queryStr));
    }
}
