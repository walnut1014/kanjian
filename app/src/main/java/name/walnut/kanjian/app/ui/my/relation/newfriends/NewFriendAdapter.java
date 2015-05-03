package name.walnut.kanjian.app.ui.my.relation.newfriends;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import butterknife.ButterKnife;
import name.walnut.kanjian.app.support.AbsListAdapter;

/**
 * 添加好友 adapter
 */
public class NewFriendAdapter extends AbsListAdapter<Friend, NewFriendAdapter.ViewHolder> {

    public NewFriendAdapter(Context context, List<Friend> list) {
        super(context, list);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        return null;
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int i) {

    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.inject(this, itemView);
        }
    }
}
