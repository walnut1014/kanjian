package name.walnut.kanjian.app.ui.my.relation.friends;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.malinskiy.superrecyclerview.SuperRecyclerView;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import name.walnut.kanjian.app.R;
import name.walnut.kanjian.app.resource.impl.Resource;
import name.walnut.kanjian.app.resource.impl.ResourceWeave;
import name.walnut.kanjian.app.support.ActionBarFragment;

/**
 * 好友列表 fragment
 */
public class FriendsFragment extends ActionBarFragment {

    @InjectView(R.id.list)
    SuperRecyclerView recyclerView;

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
    public String getTitle() {
        return getString(R.string.title_activity_friends);
    }


    public void showFriends(List<Friend> friendList) {

    }

}