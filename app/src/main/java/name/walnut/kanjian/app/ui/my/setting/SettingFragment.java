package name.walnut.kanjian.app.ui.my.setting;

import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;

import java.io.File;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import name.walnut.kanjian.app.R;
import name.walnut.kanjian.app.account.Account;
import name.walnut.kanjian.app.resource.impl.Resource;
import name.walnut.kanjian.app.resource.impl.ResourceWeave;
import name.walnut.kanjian.app.support.ActionBarFragment;
import name.walnut.kanjian.app.ui.Constants;
import name.walnut.kanjian.app.ui.common.SelectPicDialogFragment;
import name.walnut.kanjian.app.ui.my.setting.action.LogoutAction;
import name.walnut.kanjian.app.ui.my.setting.action.UpdateAvatarAction;
import name.walnut.kanjian.app.utils.UriUtils;

/**
 * 设置fragment
 */
public class SettingFragment extends ActionBarFragment {

    @InjectView(R.id.setting_avatar)
    SimpleDraweeView avatarView;
    @InjectView(R.id.setting_nickname)
    TextView nickNameTv;

    @ResourceWeave(actionClass = UpdateAvatarAction.class)
    public Resource modifyHeedPohotoResource;

    @ResourceWeave(actionClass = LogoutAction.class)
    public Resource exitResource;

    @Override
    protected String getTitle() {
        return getString(R.string.title_activity_setting);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_setting, container, false);
        ButterKnife.inject(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        // 显示个人资料
        nickNameTv.setText(Account.INSTANCE.getNickname());
        avatarView.setImageURI(Constants.getFrescoUrl(Account.INSTANCE.getHeadPhotoPath()));
    }

    @Override
    public void onDestroyView() {
        ButterKnife.reset(this);
        super.onDestroyView();
    }

    @OnClick(R.id.setting_avatar_container)
    void changeAvatar() {
        // 更换头像
        SelectPicDialogFragment.showDialog(getFragmentManager(), new SelectPicDialogFragment.SelectPicListener() {
            @Override
            public void onSelect(Uri uri) {
                showMessage(R.string.dialog_message_update_avatar);
                String imgPath = UriUtils.getPath(getActivity(), uri);
                File photo = new File(imgPath);
                modifyHeedPohotoResource.addParam("headPhoto", photo)
                        .send();
//                Bitmap bitmap = BitmapFactory.decodeFile(imgPath);
            }
        });
    }

    @OnClick(R.id.setting_nickname_container)
    void changeNickName() {
        // 修改昵称
        switchFragment(UpdateNickNameFragment.newInstance(Account.INSTANCE.getNickname()));
    }

    @OnClick(R.id.setting_password)
    void changePassword() {
        // 修改密码
    }

    @OnClick(R.id.setting_logout)
    void logout() {
        // 退出账号
        LogoutDialogFragment.showDialog(getFragmentManager(), new LogoutDialogFragment.LogoutClickListener() {
            @Override
            public void onLogout() {
                exitResource.send();
            }
        });
    }


}
