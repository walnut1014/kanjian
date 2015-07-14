package name.walnut.kanjian.app.ui.main;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import name.walnut.kanjian.app.R;
import name.walnut.kanjian.app.support.AbsListAdapter;
import name.walnut.kanjian.app.ui.Constants;
import name.walnut.kanjian.app.ui.main.bean.Message;

/**
 * 新消息列表adapter
 */
public class MessageAdapter extends AbsListAdapter<Message, MessageAdapter.MessageViewHolder> {

    public MessageAdapter(Context context, List<Message> list) {
        super(context, list);
    }

    @Override
    public MessageViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.layout_message, parent, false);
        return new MessageViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MessageViewHolder holder, int position) {
        final Message message = getItem(position);

        holder.avatarView.setImageURI(Constants.getFileUri(message.getRepayerAvatar()));
        holder.photoView.setImageURI(Constants.getFileUri(message.getPhoto()));
        holder.nicknameTv.setText(message.getRepayerName());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO 跳转到个人界面
                Intent intent = new Intent(Constants.Action.PERSONAL_PAGE_ACTION);
                context.startActivity(intent);
            }
        });
    }

    class MessageViewHolder extends RecyclerView.ViewHolder {

        @InjectView(R.id.avatar)
        SimpleDraweeView avatarView;
        @InjectView(R.id.photo)
        SimpleDraweeView photoView;
        @InjectView(R.id.nickname)
        TextView nicknameTv;
        @InjectView(R.id.intro)
        TextView introTv;

        public MessageViewHolder(View itemView) {
            super(itemView);
            ButterKnife.inject(this, itemView);
        }
    }
}
