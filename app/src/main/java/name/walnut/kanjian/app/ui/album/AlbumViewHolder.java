package name.walnut.kanjian.app.ui.album;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;

import butterknife.ButterKnife;
import butterknife.InjectView;
import name.walnut.kanjian.app.R;

/**
 */
class AlbumViewHolder extends RecyclerView.ViewHolder{

    @InjectView(R.id.album_photo)
    SimpleDraweeView photoView;
    @InjectView(R.id.album_time)
    TextView timeTv;
    @InjectView(R.id.album_action_delete)
    TextView deleteBtn;
    @InjectView(R.id.album_message)
    ImageButton messageBtn;
    @InjectView(R.id.album_comments_container)
    FrameLayout commentsContainer;
    @InjectView(R.id.album_date_month)
    TextView monthTv;
    @InjectView(R.id.album_date_day)
    TextView dayTv;

    public AlbumViewHolder(View itemView) {
        super(itemView);
        ButterKnife.inject(this, itemView);
    }
}
