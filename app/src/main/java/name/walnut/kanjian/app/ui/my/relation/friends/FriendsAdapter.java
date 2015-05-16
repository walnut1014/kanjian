package name.walnut.kanjian.app.ui.my.relation.friends;

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

/**
 * 我的好友adapter
 */
public class FriendsAdapter extends AbsListAdapter<FriendInfo, FriendsAdapter.ViewHolder>{

    private FriendsFragment fragment;

    public FriendsAdapter(FriendsFragment fragment, List<FriendInfo> list) {
        super(fragment.getActionBarActivity(), list);
        this.fragment = fragment;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = inflater.inflate(R.layout.layout_friends, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int i) {
        if (i == getItemCount()-1) {
            // 最后一个，显示添加好友按钮
            viewHolder.addFriendBtn.setVisibility(View.VISIBLE);
            viewHolder.addFriendBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    fragment.startAddFriendActivity();
                }
            });
        } else {
            viewHolder.addFriendBtn.setVisibility(View.GONE);
        }
        FriendInfo friend = getItem(i);
        viewHolder.introTv.setText(context.getString(
                R.string.friends_photo_count, friend.getPhotoCount()));
        viewHolder.avatarImg.setImageURI(Constants.getFileUri(friend.getAvatar()));
        viewHolder.nicknameTv.setText(friend.getNickName());
    }

    protected static class ViewHolder extends RecyclerView.ViewHolder {

        @InjectView(R.id.avatar)
        public SimpleDraweeView avatarImg;
        @InjectView(R.id.nickname)
        public TextView nicknameTv;
        @InjectView(R.id.intro)
        public TextView introTv;
        @InjectView(R.id.add_friend_button)
        public Button addFriendBtn; // 添加好友

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.inject(this, itemView);
            addFriendBtn.setVisibility(View.GONE);
        }
    }
}
