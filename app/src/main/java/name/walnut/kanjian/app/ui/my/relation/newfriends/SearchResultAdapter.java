package name.walnut.kanjian.app.ui.my.relation.newfriends;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import name.walnut.kanjian.app.R;
import name.walnut.kanjian.app.support.AbsListAdapter;
import name.walnut.kanjian.app.ui.Constants;
import name.walnut.kanjian.app.ui.my.relation.newfriends.pojo.Friend;

/**
 * 搜索结果 adapter
 */
public class SearchResultAdapter extends AbsListAdapter<Friend, SearchResultAdapter.ViewHolder> {

    private SearchResultFragment fragment;

    public SearchResultAdapter(SearchResultFragment fragment, List<Friend> list) {
        super(fragment.getActionBarActivity(), list);
        this.fragment = fragment;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = inflater.inflate(R.layout.layout_friend_search, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, final int position) {
        final Friend friend = getItem(position);
        viewHolder.avatar.setImageURI(Constants.getFileUri(friend.getAvatar()));
        viewHolder.nickNameTv.setText(friend.getNickName());

        String contactsName = friend.getContactsName();
        contactsName = (contactsName == null)
                ? context.getString(R.string.friend_request_unknown)
                : context.getString(R.string.friend_request_name, contactsName);
        viewHolder.introTv.setText(contactsName);

        switch (friend.getRelation()) {
            case NO_RELATION:   // 非好友
                viewHolder.statusTv.setVisibility(View.GONE);
                viewHolder.statusTv.setText("");
                viewHolder.sendBtn.setVisibility(View.VISIBLE);
                viewHolder.sendBtn.setText(R.string.request_send);
                viewHolder.sendBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        fragment.inviteFriend(friend, position);
                    }
                });
                break;
            case WAIT_VERIFY:   // 等待好友验证
                viewHolder.sendBtn.setVisibility(View.GONE);
                viewHolder.statusTv.setVisibility(View.VISIBLE);
                viewHolder.statusTv.setText(R.string.relation_wait_verify);
                break;
            case ACCEPT:    // 接受好友请求
                viewHolder.statusTv.setVisibility(View.GONE);
                viewHolder.statusTv.setText("");
                viewHolder.sendBtn.setVisibility(View.VISIBLE);
                viewHolder.sendBtn.setText(R.string.request_accept);
                viewHolder.sendBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        // 接受好友请求
                        fragment.acceptInvite(friend, position);
                    }
                });
                break;
            case AGREE:     // 已加为好友
                viewHolder.sendBtn.setVisibility(View.GONE);
                viewHolder.statusTv.setVisibility(View.VISIBLE);
                viewHolder.statusTv.setText(R.string.relation_agree);
                break;
            default:
                break;
        }
    }

    protected static class ViewHolder extends RecyclerView.ViewHolder {

        @InjectView(R.id.avatar)
        SimpleDraweeView avatar;
        @InjectView(R.id.nickname)
        TextView nickNameTv;
        @InjectView(R.id.intro)
        TextView introTv;
        @InjectView(R.id.request_send)
        Button sendBtn;
        @InjectView(R.id.request_status)
        TextView statusTv;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.inject(this, itemView);
        }
    }
}
