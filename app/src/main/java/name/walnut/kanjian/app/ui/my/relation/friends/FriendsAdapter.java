package name.walnut.kanjian.app.ui.my.relation.friends;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import name.walnut.kanjian.app.R;
import name.walnut.kanjian.app.support.AbsListAdapter;

/**
 * 我的好友adapter
 */
public class FriendsAdapter extends AbsListAdapter<Friend, FriendsAdapter.ViewHolder>{

    public FriendsAdapter(Context context, List<Friend> list) {
        super(context, list);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = inflater.inflate(R.layout.layout_friends, viewGroup);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int i) {
        Friend friend = getItem(i);
        if (i == getItemCount()-1) {
            // 最后一个，显示添加好友按钮
            viewHolder.addFriendBtn.setVisibility(View.VISIBLE);
        } else {
            viewHolder.addFriendBtn.setVisibility(View.GONE);
        }
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        @InjectView(R.id.avatar)
        public ImageView avatarImg;
        @InjectView(R.id.nickname)
        public TextView nicknameTv;
        @InjectView(R.id.intro)
        public TextView introTv;
        @InjectView(R.id.add_friend_button)
        public Button addFriendBtn;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.inject(this, itemView);
            addFriendBtn.setVisibility(View.GONE);
        }
    }
}
