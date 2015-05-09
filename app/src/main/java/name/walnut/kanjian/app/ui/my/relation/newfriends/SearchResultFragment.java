package name.walnut.kanjian.app.ui.my.relation.newfriends;

import android.os.Bundle;
import android.support.annotation.Nullable;
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
import name.walnut.kanjian.app.ui.my.relation.newfriends.action.AgreeInviteAction;
import name.walnut.kanjian.app.ui.my.relation.newfriends.action.InviteFriendAction;
import name.walnut.kanjian.app.ui.my.relation.newfriends.action.QueryUserAction;
import name.walnut.kanjian.app.ui.my.relation.newfriends.pojo.Friend;
import name.walnut.kanjian.app.ui.my.relation.newfriends.pojo.RelationStatus;

/**
 * 搜索结果 fragment
 */
public class SearchResultFragment extends ActionBarFragment{

    public static final String QUERY = "query";

    private SearchResultAdapter adapter;

    private int position;   // 列表位置
    private Friend friend;  // 添加的好友信息

    @InjectView(R.id.list)
    SuperRecyclerView recyclerView;

    @ResourceWeave(actionClass = QueryUserAction.class)
    public Resource queryUserResource;  // 查询好友resource

    @ResourceWeave(actionClass = InviteFriendAction.class)
    public Resource invitFriendResource;    // 添加好友邀请resource

    @ResourceWeave(actionClass = AgreeInviteAction.class)
    public Resource agreeInvitResource; // 接受好友请求


    public static SearchResultFragment newInstance(String query) {
        Bundle args = new Bundle();
        args.putString(QUERY, query);
        SearchResultFragment fragment = new SearchResultFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected String getTitle() {
        return getString(R.string.title_fragment_search_result);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        String query = getArguments().getString(QUERY);

        // 搜索好友
        queryUserResource.addParam("mobilephone", query)
                .send();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_search_result, container, false);
        ButterKnife.inject(this, view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActionBarActivity()));
        return view;
    }

    @Override
    public void onDestroyView() {
        ButterKnife.reset(this);
        adapter = null;
        super.onDestroyView();
    }

    // 显示搜索结果
    public void showSearchResult(List<Friend> friends) {
        if (adapter == null) {
            adapter = new SearchResultAdapter(this, friends);
            recyclerView.setAdapter(adapter);
        } else {
            adapter.setDataSet(friends);
        }
    }

    // 发送添加好友邀请
    public void inviteFriend(Friend friend, int position) {
        this.position = position;
        this.friend = friend;
        showMessage(R.string.dialog_message_invite_friend);
        invitFriendResource.addParam("id", friend.getId())
                .send();
    }

    // 发送好友邀请 结果
    public void onInviteResult(boolean success) {
        if (success) {
            friend.setRelation(RelationStatus.WAIT_VERIFY);
            adapter.notifyItemChanged(this.position);
        }
        dismissMessage();
        position = -1;
        friend = null;
    }

    // 接受好友请求
    public void acceptInvite(Friend friend, int position) {
        this.position = position;
        this.friend = friend;
        showMessage(R.string.dialog_message_agree_invite);
        agreeInvitResource.addParam("id", friend.getId())
                .send();
    }

    // 接受好友请求 结果
    public void onAgreeInviteResult(boolean success) {
        dismissMessage();
        if (success) {
            friend.setRelation(RelationStatus.AGREE);
            adapter.notifyItemChanged(position);
        }
        friend = null;
        position = -1;
    }
}
