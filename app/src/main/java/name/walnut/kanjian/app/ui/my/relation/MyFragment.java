package name.walnut.kanjian.app.ui.my.relation;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import name.walnut.kanjian.app.R;
import name.walnut.kanjian.app.account.Account;
import name.walnut.kanjian.app.resource.impl.Resource;
import name.walnut.kanjian.app.resource.impl.ResourceWeave;
import name.walnut.kanjian.app.support.ActionBarFragment;
import name.walnut.kanjian.app.ui.Constants;
import name.walnut.kanjian.app.views.SettingItemView;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;

public class MyFragment extends ActionBarFragment {

    private static final int ACTIVITY_REQUEST_FRIENDS_REQUEST = 0;
    private static final int ACTIVITY_REQUEST_FRIENDS = 1;
    private static final int ACTIVITY_REQUEST_ADD_FRIENDS = 2;
    private static final int ACTIVITY_REQUEST_SETTING = 3;

    @ResourceWeave(actionClass = RelationCountAction.class)
    public Resource relationCountResource;

    @InjectView(R.id.my_avatar)
    SimpleDraweeView avatarView;
    @InjectView(R.id.my_nickname)
    TextView nicknameTv;
    @InjectView(R.id.my_title_friend_request)
    SettingItemView friendRequestView;
    @InjectView(R.id.my_title_friends)
    SettingItemView friendsView;
    @InjectView(R.id.my_title_add_friend)
    SettingItemView addFriendsView;
    @InjectView(R.id.my_title_setting)
    SettingItemView settingView;

    @Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.fragment_main, container,false);
        ButterKnife.inject(this, rootView);
		return rootView;
	}

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        showAccount();
        fetchRelationCount();
    }

    // 刷新好友关系
    private void fetchRelationCount() {
        relationCountResource.send();
    }

    @Override
    public void onDestroyView() {
        ButterKnife.reset(this);
        super.onDestroyView();
    }

    @OnClick(R.id.my_title_friend_request)
    public void startFriendRequestsActivity() {
        Intent intent = new Intent(Constants.Action.FRIEND_REQUEST_ACTION);
        startActivityForResult(intent, ACTIVITY_REQUEST_FRIENDS_REQUEST);
    }

    @OnClick(R.id.my_title_friends)
    public void startFriendsActivity() {
        Intent intent = new Intent(Constants.Action.FRIENDS_ACTION);
        startActivityForResult(intent, ACTIVITY_REQUEST_FRIENDS);
    }

    @OnClick(R.id.my_title_add_friend)
    public void startAddFriendActivity() {
        Intent intent = new Intent(Constants.Action.ADD_FRIENDS_ACTION);
        startActivityForResult(intent, ACTIVITY_REQUEST_ADD_FRIENDS);
    }
    
    @OnClick(R.id.my_title_setting)
    public void startSettingActivity() {
        Intent intent = new Intent(Constants.Action.SETTING_ACTION);
        startActivityForResult(intent, ACTIVITY_REQUEST_SETTING);
    }

    @Override
    public String getTitle() {
        return getString(R.string.title_fragment_my);
    }

    @Override
    public boolean showBack() {
        return false;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case ACTIVITY_REQUEST_FRIENDS:
                fetchRelationCount();
                break;
            case ACTIVITY_REQUEST_SETTING:
                // 从设置回来，刷新账号信息
                showAccount();
                break;
        }
    }

    /**
     * 显示账号相关信息
     */
    public void showAccount() {
        showNickname(Account.INSTANCE.getNickname());
        showAvatar(Account.INSTANCE.getHeadPhotoPath());
    }

    private void showAvatar(String headPhotoPath) {
        avatarView.setImageURI(Constants.getFrescoUrl(headPhotoPath));
    }

    private void showNickname(String nickname) {
        nicknameTv.setText(nickname);
    }

    public void showRelationCount(int friendCount, int unreadCount) {
        showFriendCount(friendCount);
        showUnreadCount(unreadCount);
    }

    private void showFriendCount(int friendCount) {
        friendsView.setExtra(friendCount+"");
        friendsView.showDot(false);
    }

    private void showUnreadCount(int unreadCount) {
        friendRequestView.setExtra(unreadCount+"");
        friendRequestView.showDot(unreadCount > 0);
    }

}
