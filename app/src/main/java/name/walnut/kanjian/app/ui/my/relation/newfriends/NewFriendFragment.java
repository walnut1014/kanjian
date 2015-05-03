package name.walnut.kanjian.app.ui.my.relation.newfriends;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.malinskiy.superrecyclerview.SuperRecyclerView;

import java.util.List;
import java.util.Map;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import name.walnut.kanjian.app.R;
import name.walnut.kanjian.app.support.ActionBarFragment;
import name.walnut.kanjian.app.utils.ContactsUtils;

/**
 * 添加好友fragment
 */
public class NewFriendFragment extends ActionBarFragment {

    @InjectView(R.id.list)
    SuperRecyclerView recyclerView;
    @InjectView(R.id.search)
    View searchView;

    private NewFriendAdapter adapter;

    public NewFriendFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_new_friend, container, false);
        ButterKnife.inject(this, rootView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        showContactsFriends(null);
        return rootView;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        // 获取本地所有手机号
        Map<String, String> contacts = ContactsUtils.getAllContacts(getActionBarActivity());

    }

    @Override
    public void onDestroyView() {
        ButterKnife.reset(this);
        adapter = null;
        super.onDestroyView();
    }

    @Override
    protected String getTitle() {
        return getString(R.string.title_activity_new_friend);
    }

    @OnClick(R.id.search)
    public void startSearchFragment() {
        SearchDialogFragment.show(getFragmentManager());
    }

    // 获取好友信息
    private void fetchFriends() {

    }

    public void showContactsFriends(List<Friend> friendList) {
        if (adapter == null) {
            adapter = new NewFriendAdapter(getActionBarActivity(), friendList);
            recyclerView.setAdapter(adapter);
        } else {
            adapter.setDataSet(friendList);
        }
    }

}