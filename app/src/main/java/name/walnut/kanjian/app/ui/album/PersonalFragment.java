package name.walnut.kanjian.app.ui.album;

import android.content.Intent;
import android.os.Bundle;

import name.walnut.kanjian.app.account.Account;
import name.walnut.kanjian.app.resource.impl.Resource;
import name.walnut.kanjian.app.resource.impl.ResourceWeave;
import name.walnut.kanjian.app.support.ActionBarFragment;
import name.walnut.kanjian.app.ui.album.action.SelfPhotoAction;
import name.walnut.kanjian.app.ui.album.action.UserPhotoAction;

/**
 * 个人主页
 */
public class PersonalFragment extends ActionBarFragment {


    @ResourceWeave(actionClass = SelfPhotoAction.class)
    public Resource selfPhotoResource;

    @ResourceWeave(actionClass = UserPhotoAction.class)
    public Resource userPhotoResource;

    private String userName = "";
    private long userId;

    public static PersonalFragment newInstance() {
        PersonalFragment fragment = new PersonalFragment();
        return fragment;
    }

    @Override
    protected String getTitle() {
        return userName;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent intent = getActivity().getIntent();
        userId = intent.getLongExtra("userId", 0);
        userName = intent.getStringExtra("userName");

        if (isSelfAlbum()) {
            userName = Account.INSTANCE.getNickname();
        }

        fetchPhotoAlbum();
    }

    /**
     * 是否自己的相簿
     * @return
     */
    private boolean isSelfAlbum() {
        return userId == 0;
    }

    private void fetchPhotoAlbum() {
        if (isSelfAlbum()) {
            selfPhotoResource.send();
        } else {
            userPhotoResource.addParam("userId", userId).send();
        }
    }
}
