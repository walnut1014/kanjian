package name.walnut.kanjian.app.ui.my.relation.request;

import android.content.Context;
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
import name.walnut.kanjian.app.support.AbsListAdapter;

/**
 * 好友请求 adapter
 */
public class FriendRequestAdapter extends AbsListAdapter<FriendRequest, FriendRequestAdapter.ViewHolder>{


    public FriendRequestAdapter(Context context, List<FriendRequest> list) {
        super(context, list);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
        View view = inflater.inflate(R.layout.layout_friend_request, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int i) {
        FriendRequest friendRequest = getItem(i);
        viewHolder.nicknameTv.setText(friendRequest.getMobilePhone());
        String name = friendRequest.getContactsName();
        if (null == name) {
            name = context.getString(R.string.friend_request_unknown);
        } else {
            name = context.getString(R.string.friend_request_name, name);
        }
        viewHolder.introTv.setText(name);
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

    static class ViewHolder extends RecyclerView.ViewHolder {

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
