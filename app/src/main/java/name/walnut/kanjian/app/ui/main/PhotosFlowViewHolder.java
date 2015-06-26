package name.walnut.kanjian.app.ui.main;


import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;

import butterknife.ButterKnife;
import butterknife.InjectView;
import name.walnut.kanjian.app.R;

class PhotosFlowViewHolder extends RecyclerView.ViewHolder {

    @InjectView(R.id.comments_container)
    FrameLayout commentsContainer;
    @InjectView(R.id.message)
    ImageButton messageBtn;
    @InjectView(R.id.action_delete)
    TextView deleteBtn;
    @InjectView(R.id.time)
    TextView timeTv;
    @InjectView(R.id.photo)
    SimpleDraweeView photoView;
    @InjectView(R.id.description)
    TextView descriptionTv;
    @InjectView(R.id.submitter_name)
    TextView submitterTv;
    @InjectView(R.id.avatar)
    SimpleDraweeView avatarView;

    public PhotosFlowViewHolder(Context context, View itemView) {
        super(itemView);
        ButterKnife.inject(this, itemView);
    }
}
