package name.walnut.kanjian.app.ui.register;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.text.InputFilter;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import com.umeng.message.UmengRegistrar;

import java.io.File;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import name.walnut.kanjian.app.R;
import name.walnut.kanjian.app.resource.impl.Resource;
import name.walnut.kanjian.app.resource.impl.ResourceWeave;
import name.walnut.kanjian.app.support.ActionBarFragment;
import name.walnut.kanjian.app.ui.Constants;
import name.walnut.kanjian.app.ui.common.SelectPicDialogFragment;
import name.walnut.kanjian.app.ui.register.action.RegisterAction;
import name.walnut.kanjian.app.ui.util.ToastUtils;
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
    private String nickname;

    public FillNicknameFragment() {}

    public static FillNicknameFragment newInstance(String token, String password) {
        Bundle bundle = new Bundle();
        bundle.putString("token", token);
        bundle.putString("password", password);
        FillNicknameFragment fragment = new FillNicknameFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public boolean isVerifyAuth() {
        return false;
    }

    @Override
    protected void onBack() {
//        super.onBack();
        // 直接返回输入手机号界面
        RegisterActivity activity = (RegisterActivity) getActionBarActivity();
        String mobilePhone = activity.getMobilephone();

        Intent intent = new Intent(Constants.Action.REGISTER_ACTION);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.putExtra("mobilephone", mobilePhone);
        startActivity(intent);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        token = getArguments().getString("token");
        password = getArguments().getString("password");
    }

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
            ToastUtils.toast(R.string.toast_empty_nickname);

        } else {
            this.nickname = nickname;

            showMessage(R.string.dialog_message_fill_nickname);

            // 获取设备Device Token
            String deviceToken = UmengRegistrar.getRegistrationId(getActionBarActivity());
            if (TextUtils.isEmpty(deviceToken)) {
                Logger.e("设备还未注册");
            }

            // TODO 上传图片
            registerResource.addParam("token", token)
                    .addParam("nickName", nickname)
                    .addParam("password", password)
                    .addParam("deviceToken", deviceToken);
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

    public String getNickname() {
        return nickname;
    }
}
