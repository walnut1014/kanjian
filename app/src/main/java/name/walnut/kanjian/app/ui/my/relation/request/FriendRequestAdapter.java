package name.walnut.kanjian.app.ui.my.relation.request;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import name.walnut.kanjian.app.R;

/**
 * 好友请求 adapter
 */
public class FriendRequestAdapter extends RecyclerView.Adapter<FriendRequestAdapter.ViewHolder>{

    private List<FriendRequest> friendRequestList;

    public FriendRequestAdapter(List<FriendRequest> friendRequestList) {
        setFriendRequestList(friendRequestList);
    }

    public void setFriendRequestList(List<FriendRequest> friendRequestList) {
        this.friendRequestList = friendRequestList;
        if (this.friendRequestList == null) {
            this.friendRequestList = new ArrayList<>();
        }
        notifyDataSetChanged();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
        View view = inflater.inflate(R.layout.layout_friend_request, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int i) {
        FriendRequest friendRequest = friendRequestList.get(i);
        viewHolder.nicknameTv.setText(friendRequest.getMobilePhone());
        viewHolder.introTv.setText(friendRequest.getMobilePhone());
        // TODO
        switch (friendRequest.getState()) {
            case waitVerify:
                viewHolder.acceptBtn.setVisibility(View.GONE);
                viewHolder.statusTv.setVisibility(View.VISIBLE);
                viewHolder.statusTv.setText("待验证");
                break;
            case sendRequest:
                viewHolder.acceptBtn.setVisibility(View.GONE);
                viewHolder.statusTv.setVisibility(View.VISIBLE);
                viewHolder.statusTv.setText("已加为好友");
                break;
            case accept:
                viewHolder.acceptBtn.setVisibility(View.VISIBLE);
                viewHolder.statusTv.setVisibility(View.GONE);
                viewHolder.statusTv.setText("");
                break;
        }
    }

    @Override
    public int getItemCount() {
        return friendRequestList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        @InjectView(R.id.avatar)
        public ImageView avatarImg;
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
