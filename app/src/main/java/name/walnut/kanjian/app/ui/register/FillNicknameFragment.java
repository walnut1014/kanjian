package name.walnut.kanjian.app.ui.register;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.text.InputFilter;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.squareup.otto.Subscribe;

import java.io.File;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import name.walnut.kanjian.app.R;
import name.walnut.kanjian.app.resource.impl.Resource;
import name.walnut.kanjian.app.resource.impl.ResourceWeave;
import name.walnut.kanjian.app.support.ActionBarFragment;
import name.walnut.kanjian.app.support.BusContext;
import name.walnut.kanjian.app.ui.Constants;
import name.walnut.kanjian.app.ui.register.action.RegisterAction;
import name.walnut.kanjian.app.utils.Logger;
import name.walnut.kanjian.app.utils.UriUtils;
import name.walnut.kanjian.app.views.ClearEditText;

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

    private Uri avatarUri; //选中图片路径

    private String token;
    private String password;

    public FillNicknameFragment(String token, String password) {
        this.token = token;
        this.password = password;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_fill_nickname, container, false);
        ButterKnife.inject(this, view);

        nicknameEdit.getEditText().setFilters(new InputFilter[]{
                new InputFilter.LengthFilter(Constants.Materials.NICKNAME_MAX_LENGTH)
        });

        BusContext.INSTANCE.getBus().register(this);

        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.reset(this);
        BusContext.INSTANCE.getBus().unregister(this);
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
            // TODO 上传图片
            registerResource.addParam("token", token)
                    .addParam("nickName", nickname)
                    .addParam("password", password);
            if (avatarUri != null) {
                String photoPath = UriUtils.getPath(getActivity(), avatarUri);
                File photo = new File(photoPath);
                registerResource.addParam("photo", photo);
            }
            registerResource.send();

        }
    }


    @OnClick(R.id.fill_nickname_avatar)
    void showSelectPopup() {
        // 显示选择框
        SelectPicDialogFragment.showDialog(getFragmentManager(), new SelectPicDialogFragment.SelectPicListener() {
            @Override
            public void onSelect(Uri uri) {
                avatarUri = uri;
                String imgPath = UriUtils.getPath(getActivity(), uri);
                Bitmap bitmap = BitmapFactory.decodeFile(imgPath);
                avatarImg.setImageBitmap(bitmap);
            }
        });
    }

}
