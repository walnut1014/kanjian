package name.walnut.kanjian.app.ui.my.relation.request;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
import name.walnut.kanjian.app.ui.my.relation.request.action.RelationListAction;

/**
 * 好友请求 fragment
 */
public class FriendRequestFragment extends ActionBarFragment {

    @InjectView(R.id.list)
    SuperRecyclerView recyclerView;

    @ResourceWeave(actionClass = RelationListAction.class)
    public Resource relationListResource;

    private FriendRequestAdapter adapter;

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        fetchRelation();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_friend_request, container, false);
        ButterKnife.inject(this, view);

        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.getRecyclerView().setHasFixedSize(true);
        return view;
    }

    @Override
    public void onDestroyView() {
        ButterKnife.reset(this);
        adapter = null;
        super.onDestroyView();
    }

    @Override
    public String getTitle() {
        return getString(R.string.title_activity_friend_request);
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

    // 获取好友请求
    private void fetchRelation() {
        relationListResource.send();
    }

    // 设置数据源并显示
    public void show(List<FriendRequest> requestList) {
        if (adapter == null) {
            adapter = new FriendRequestAdapter(getActionBarActivity(), requestList);
            recyclerView.setAdapter(adapter);
        } else {
            adapter.setDataSet(requestList);
        }
    }
}
