package name.walnut.kanjian.app.ui.register;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import name.walnut.kanjian.app.R;
import name.walnut.kanjian.app.resource.impl.Resource;
import name.walnut.kanjian.app.resource.impl.ResourceWeave;
import name.walnut.kanjian.app.support.ActionBarFragment;
import name.walnut.kanjian.app.ui.Constants;
import name.walnut.kanjian.app.ui.register.action.RegisterAction;
import name.walnut.kanjian.app.views.ClearEditText;

import android.os.Bundle;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

/**
 * 填写昵称Fragment
 * @author walnut
 *
 */
public class FillNicknameFragment extends ActionBarFragment {

    @InjectView(R.id.fill_nickname_avatar)
    ImageView avatarImg;
    @InjectView(R.id.fill_nickname_edit)
    ClearEditText nicknameEdit;
    @InjectView(R.id.fill_nickname_submit)
    Button submitBtn;

    @ResourceWeave(actionClass = RegisterAction.class)
    public Resource registerResource;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_fill_nickname, container, false);
        ButterKnife.inject(this, view);

        nicknameEdit.getEditText().setFilters(new InputFilter[]{
                new InputFilter.LengthFilter(Constants.Materials.NICKNAME_MAX_LENGTH)
        });

        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.reset(this);
    }

    @Override
    public String getTitle() {
        return getString(R.string.title_fill_nickname);
    }

    @OnClick(R.id.fill_nickname_submit)
    void submit() {
        String nickname = nicknameEdit.getEditText().getText().toString();

        if (TextUtils.isEmpty(nickname)) {
            Toast.makeText(getActivity(), R.string.toast_empty_nickname, Toast.LENGTH_SHORT).show();

        } else {
            registerResource.send();

        }
    }

}
