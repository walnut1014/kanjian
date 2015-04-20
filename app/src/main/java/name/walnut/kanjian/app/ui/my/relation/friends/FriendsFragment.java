package name.walnut.kanjian.app.ui.my.relation.friends;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.malinskiy.superrecyclerview.SuperRecyclerView;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import name.walnut.kanjian.app.R;
import name.walnut.kanjian.app.resource.impl.Resource;
import name.walnut.kanjian.app.resource.impl.ResourceWeave;
import name.walnut.kanjian.app.support.ActionBarFragment;
import name.walnut.kanjian.app.ui.Constants;

/**
 * 好友列表 fragment
 */
public class FriendsFragment extends ActionBarFragment {

    @InjectView(R.id.list)
    SuperRecyclerView recyclerView;
    @InjectView(R.id.add_friend_button)
    Button addFriendBtn;

    private FriendsAdapter adapter;

    @ResourceWeave(actionClass = FetchFriendsAction.class)
    public Resource friendsResource;

    public FriendsFragment() {
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        fetchFriends();
    }

    private void fetchFriends() {
        friendsResource.send();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_friends, container, false);
        ButterKnife.inject(this, rootView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        return rootView;
    }

    @Override
    public void onDestroyView() {
        ButterKnife.reset(this);
        super.onDestroyView();
    }

    @Override
    protected String getTitle() {
        return getString(R.string.title_activity_friends);
    }

    @Override
    protected View getActionBarMenuView() {
        TextView textView = (TextView) LayoutInflater.from(getActionBarActivity())
                .inflate(R.layout.action_bar_menu_text, null);
        textView.setText(getString(R.string.menu_add_friend));
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startAddFriendActivity();
            }
        });
        return textView;
    }

    @OnClick(R.id.add_friend_button)
    public void startAddFriendActivity() {
        getActivity().finish();
        Intent intent = new Intent(Constants.Action.ADD_FRIENDS_ACTION);
        startActivity(intent);
    }

    public void showFriends(List<Friend> friendList) {
        if (adapter == null) {
            adapter = new FriendsAdapter(getActionBarActivity(), friendList);
            recyclerView.setAdapter(adapter);
        } else {
            adapter.setDataSet(friendList);
        }
    }

}