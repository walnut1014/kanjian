package name.walnut.kanjian.app.ui.main;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import name.walnut.kanjian.app.R;
import name.walnut.kanjian.app.support.AbsListAdapter;
import name.walnut.kanjian.app.utils.Logger;

/**
 * 看照片 adapter
 */
public class PhotosFlowAdapter extends AbsListAdapter<PhotosFlow, PhotosFlowAdapter.ViewHolder> {

    private ViewCache commentsCache;
    private PhotosFlowFragment fragment;

    public PhotosFlowAdapter(PhotosFlowFragment fragment, List<PhotosFlow> list) {
        super(fragment.getActionBarActivity(), list);
        commentsCache = new ViewCache();
        this.fragment = fragment;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.layout_photos_flow, parent, false);
        ViewHolder viewHolder = new ViewHolder(context, view);
        Logger.e("onCreateViewHolder");
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        long startTime = System.currentTimeMillis();
        Logger.e("start:" + startTime);

        final PhotosFlow photosFlow = getItem(position);
        int commentCount = photosFlow.comments.size();
        Logger.e("评论数量：" + commentCount);

        ViewGroup commentView = commentsCache.get(photosFlow);
        if (commentView != null && commentView.getChildCount() != commentCount) {
            commentView = null;
        }
        if (commentView == null) {
            commentView = onCreateCommentView(context, photosFlow.comments);
            commentsCache.put(photosFlow, commentView);
        }
        if (commentView.getParent() != holder.commentsContainer) {
            Logger.e("addView");

            // TODO 将view从之前区域移除，但是好像没有执行这一段
            ViewGroup oldParent = (ViewGroup) commentView.getParent();
            if (oldParent != null) {
                Logger.e("removeView");
                oldParent.removeView(commentView);
            }

            // 添加
            holder.commentsContainer.addView(commentView);
        }

        holder.messageBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fragment.showCommentArea(photosFlow);
            }
        });

        long useTime = System.currentTimeMillis() - startTime;
        Logger.e("useTime:" + useTime);
    }

    @Override
    public void onViewRecycled(ViewHolder holder) {
        super.onViewRecycled(holder);
        Logger.e("onViewRecycled");
        holder.commentsContainer.removeAllViews();  // 移除评论Views
    }

    private ViewGroup onCreateCommentView(Context context, List<Comment> commentList) {
        long startTime = System.currentTimeMillis();
        Logger.e("onCreateCommentView："+startTime);

        LinearLayout container = new LinearLayout(context);
        container.setOrientation(LinearLayout.VERTICAL);

        container.setLayoutParams(
                new ViewGroup.LayoutParams(
                        ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT));

        for (int i = 0; i < commentList.size(); i++) {
            TextView textView = (TextView) inflater.inflate(R.layout.layout_comment, container, false);
            container.addView(textView);
            textView.setText(commentList.get(i).content + "  评论长度：" + commentList.size());
        }

        long useTime = System.currentTimeMillis() - startTime;
        Logger.e("onCreateCommentView：" + useTime);

        return container;
    }

    protected static class ViewHolder extends RecyclerView.ViewHolder {

        @InjectView(R.id.comments_container)
        FrameLayout commentsContainer;
        @InjectView(R.id.message)
        ImageButton messageBtn;


        public ViewHolder(Context context, View itemView) {
            super(itemView);
            ButterKnife.inject(this, itemView);
        }
    }

}