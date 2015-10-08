package name.walnut.kanjian.app.ui.my.relation.newfriends;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.malinskiy.superrecyclerview.SuperRecyclerView;

import java.util.List;
import java.util.Map;
import java.util.Set;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import name.walnut.kanjian.app.R;
import name.walnut.kanjian.app.account.Account;
import name.walnut.kanjian.app.resource.impl.Resource;
import name.walnut.kanjian.app.resource.impl.ResourceWeave;
import name.walnut.kanjian.app.support.ActionBarFragment;
import name.walnut.kanjian.app.ui.my.relation.newfriends.action.AgreeInviteAction;
import name.walnut.kanjian.app.ui.my.relation.newfriends.action.InviteFriendAction;
import name.walnut.kanjian.app.ui.my.relation.newfriends.action.QueryUserAction;
import name.walnut.kanjian.app.ui.my.relation.newfriends.pojo.Friend;
import name.walnut.kanjian.app.ui.my.relation.newfriends.pojo.RelationStatus;
import name.walnut.kanjian.app.utils.ContactsUtils;
import name.walnut.kanjian.app.utils.Logger;

/**
 * 添加好友fragment
 */
public class NewFriendFragment extends ActionBarFragment {

    @InjectView(R.id.list)
    SuperRecyclerView recyclerView;
    @InjectView(R.id.search)
    View searchView;

    @ResourceWeave(actionClass = QueryUserAction.class)
    public Resource queryUserResource;

    @ResourceWeave(actionClass = InviteFriendAction.class)
    public Resource invitFriendResource;    // 添加好友邀请resource

    @ResourceWeave(actionClass = AgreeInviteAction.class)
    public Resource agreeInvitResource; // 接受好友请求

    private Map<String, String> contactsMap;    // 联系人, key是手机号, value为通讯录名称

    private List<Friend> friendList;    // 缓存匹配信息

    private NewFriendAdapter adapter;

    private int position;   // 列表位置
    private Friend friend;  // 添加的好友信息

    private boolean isFetchingContacts = false;

    public NewFriendFragment() {}

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // 获取本地所有手机号
        contactsMap = ContactsUtils.getAllContacts(getActionBarActivity());
        //TODO 快速解决方案
        for(String key : contactsMap.keySet()) {
            if(key.equals(Account.INSTANCE.getMobilePhone()))
                contactsMap.remove(key);
        }

        fetchContactsFriends();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_new_friend, container, false);
        ButterKnife.inject(this, rootView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        showContactsFriends();
        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();
        if (isFetchingContacts) {
            showMessage(R.string.dialog_message_fetch_contacts);
        } else {
            dismissMessage();
        }
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

    // 获取通讯录好友信息
    private void fetchContactsFriends() {
        isFetchingContacts = true;
        String params = getMobilePhones();
        queryUserResource.addParam("phones", params)
                .send();
    }

    // 从缓存中显示通讯录好友
    private void showContactsFriends() {
        boolean fetching = isFetchingContacts;
        showContactsFriends(friendList);
        isFetchingContacts = fetching;
    }

    // 显示通讯录好友
    public void showContactsFriends(List<Friend> friendList) {
        isFetchingContacts = false;
        dismissMessage();
        this.friendList = friendList;
        if (adapter == null) {
            adapter = new NewFriendAdapter(this, friendList);
            recyclerView.setAdapter(adapter);
        } else {
            adapter.setDataSet(friendList);
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

    // 把手机号拼接成字符串
    private String getMobilePhones() {

        StringBuilder builder = new StringBuilder();
        for (String key : contactsMap.keySet()) {
            String phone =  key.replaceAll(" ","");
            builder.append(phone).append(",");
        }

        if (builder.length() > 0) {
            builder.deleteCharAt(builder.length() - 1 );
        }
        return builder.toString();
    }
}