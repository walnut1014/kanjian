package name.walnut.kanjian.app.ui.my.setting;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import butterknife.ButterKnife;
import butterknife.InjectView;
import name.walnut.kanjian.app.R;
import name.walnut.kanjian.app.resource.impl.Resource;
import name.walnut.kanjian.app.resource.impl.ResourceWeave;
import name.walnut.kanjian.app.support.ActionBarBuilder;
import name.walnut.kanjian.app.support.ActionBarFragment;
import name.walnut.kanjian.app.ui.my.setting.action.UpdateNickNameAction;
import name.walnut.kanjian.app.ui.util.ToastUtils;
import name.walnut.kanjian.app.views.ClearEditText;

/**
 * 修改昵称
 */
public class UpdateNickNameFragment extends ActionBarFragment {

    private static final String ARGS_NICKNAME = "nickname";

    private String nickname;

    @InjectView(R.id.nickname_edittext)
    ClearEditText nicknameEdit;

    @ResourceWeave(actionClass = UpdateNickNameAction.class)
    public Resource modifyNickNameResource;

    public static UpdateNickNameFragment newInstance(String nickname) {
        Bundle bundle = new Bundle();
        bundle.putString(ARGS_NICKNAME, nickname);
        UpdateNickNameFragment fragment = new UpdateNickNameFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    protected String getTitle() {
        return getString(R.string.title_activity_update_nickname);
    }

    @Override
    protected ActionBarBuilder.BackStyle getActionBarBackStyle() {
        return ActionBarBuilder.BackStyle.TEXT;
    }

    @Override
    protected String getActionBarBackText() {
        return getString(R.string.action_cancel);
    }

    @Override
    protected View getActionBarMenuView() {
        TextView textView = (TextView) LayoutInflater.from(getActionBarActivity())
                .inflate(R.layout.action_bar_menu_text, null);
        textView.setText(getString(R.string.menu_submit));
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                submitNickName();
            }
        });
        return textView;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_update_nickname, container, false);
        ButterKnife.inject(this, view);

        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        String nickname = getArguments().getString(ARGS_NICKNAME);
        nicknameEdit.setEditText(nickname);
    }

    @Override
    public void onDestroyView() {
        ButterKnife.reset(this);
        super.onDestroyView();
    }

    private void submitNickName() {
        // 修改昵称
        String nickname = nicknameEdit.getEditText().getText().toString();

        if (TextUtils.isEmpty(nickname)) {
            ToastUtils.toast(R.string.toast_empty_nickname);

        } else {
            this.nickname = nickname;
            showMessage(R.string.dialog_message_update_nickname);

            modifyNickNameResource.addParam("nickName", nickname)
                    .send();
        }
    }

    public String getNickName() {
        return nickname;
    }
}
