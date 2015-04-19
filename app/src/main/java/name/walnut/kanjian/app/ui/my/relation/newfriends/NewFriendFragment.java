package name.walnut.kanjian.app.ui.my.relation.newfriends;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.malinskiy.superrecyclerview.SuperRecyclerView;

import butterknife.ButterKnife;
import butterknife.InjectView;
import name.walnut.kanjian.app.R;
import name.walnut.kanjian.app.support.ActionBarFragment;

/**
 * 添加好友fragment
 */
public class NewFriendFragment extends ActionBarFragment {

    @InjectView(R.id.list)
    SuperRecyclerView recyclerView;

    public NewFriendFragment() {
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_new_friend, container, false);
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
        return getString(R.string.title_activity_new_friend);
    }

    // 获取好友信息
    private void fetchFriends() {

    }

}