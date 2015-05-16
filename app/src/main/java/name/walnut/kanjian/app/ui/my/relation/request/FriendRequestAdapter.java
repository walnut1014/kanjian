package name.walnut.kanjian.app.ui.my.relation.request;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
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

/**
 * 好友请求 adapter
 */
public class FriendRequestAdapter extends AbsListAdapter<FriendRequest, FriendRequestAdapter.ViewHolder>{

    private FriendRequestFragment fragment;

    public FriendRequestAdapter(FriendRequestFragment fragment, List<FriendRequest> list) {
        super(fragment.getActionBarActivity(), list);
        this.fragment = fragment;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
        View view = inflater.inflate(R.layout.layout_friend_request, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, final int position) {
        final FriendRequest friendRequest = getItem(position);
        viewHolder.nicknameTv.setText(friendRequest.getNickName());
        String name = friendRequest.getContactsName();
        name = (name == null)
                    ? context.getString(R.string.friend_request_unknown)
                    : context.getString(R.string.friend_request_name, name);
        viewHolder.introTv.setText(name);
        viewHolder.avatarImg.setImageURI(Constants.getFileUri(friendRequest.getAvatar()));
        if (friendRequest.isAgree()) {
            // 已同意
            viewHolder.acceptBtn.setVisibility(View.GONE);
            viewHolder.statusTv.setVisibility(View.VISIBLE);
            viewHolder.statusTv.setText("已加为好友");
            viewHolder.acceptBtn.setOnClickListener(null);

        } else if (!friendRequest.isInvited()) {
            // 接受
            viewHolder.acceptBtn.setVisibility(View.VISIBLE);
            viewHolder.statusTv.setVisibility(View.GONE);
            viewHolder.statusTv.setText("");
            viewHolder.acceptBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // 接受好友邀请
                    fragment.agreeInvite(friendRequest, position);
                }
            });

        } else {
            // 待验证
            viewHolder.acceptBtn.setVisibility(View.GONE);
            viewHolder.statusTv.setVisibility(View.VISIBLE);
            viewHolder.statusTv.setText("待验证");
            viewHolder.acceptBtn.setOnClickListener(null);
        }

    }

    protected static class ViewHolder extends RecyclerView.ViewHolder {

        @InjectView(R.id.avatar)
        public SimpleDraweeView avatarImg;
        @InjectView(R.id.nickname)
        public TextView nicknameTv;
        @InjectView(R.id.intro)
        public TextView introTv;
        @InjectView(R.id.request_status)
        public TextView statusTv;
        @InjectView(R.id.request_accept)
        public Button acceptBtn;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.inject(this, itemView);
        }
    }
}
