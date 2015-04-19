package name.walnut.kanjian.app.ui.my.relation;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import name.walnut.kanjian.app.R;
import name.walnut.kanjian.app.resource.impl.Resource;
import name.walnut.kanjian.app.resource.impl.ResourceWeave;
import name.walnut.kanjian.app.support.ActionBarFragment;
import name.walnut.kanjian.app.ui.Constants;
import name.walnut.kanjian.app.views.SettingItemView;

import android.app.ActionBar;
import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class MyFragment extends ActionBarFragment {

    @ResourceWeave(actionClass = FetchRelationCountAction.class)
    public Resource relationCountResource;

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
        fetchRelationCount();
    }

    private void fetchRelationCount() {
        relationCountResource.send();
    }

    @Override
    public void onDestroyView() {
        ButterKnife.reset(this);
        super.onDestroyView();
    }

    @OnClick(R.id.my_title_friend_request)
    public void intoFriendRequestsActivity() {
        Intent intent = new Intent(Constants.Action.FRIEND_REQUEST_ACTION);
        startActivity(intent);
    }

    @OnClick(R.id.my_title_friends)
    public void intoFriendsActivity() {
        Intent intent = new Intent(Constants.Action.FRIENDS_ACTION);
        startActivity(intent);
    }

    @OnClick(R.id.my_title_add_friend)
    public void intoAddFriendActivity() {
        Intent intent = new Intent(Constants.Action.ADD_FRIENDS_ACTION);
        startActivity(intent);
    }

    @Override
    public String getTitle() {
        return getString(R.string.title_fragment_my);
    }

    @Override
    public boolean showBack() {
        return false;
    }
}
