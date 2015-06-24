package name.walnut.kanjian.app.ui.main;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.text.Selection;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.TextUtils;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.text.style.ForegroundColorSpan;
import android.text.style.URLSpan;
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
import name.walnut.kanjian.app.account.Account;
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

        /**
         * 构建评论区
         */
        ViewGroup commentView = commentsCache.get(photosFlow);

        // 如果缓存中commentView不在holder.commentsContainer中，将view从之前区域移除
        if (commentView != null) {
            ViewGroup oldParent = (ViewGroup) commentView.getParent();
            if (oldParent != null && oldParent != holder.commentsContainer) {
                Logger.e("removeView");
                oldParent.removeView(commentView);
            }
        }

        // commentView不存在或者评论数量变化时，删除该CommentView
        if (commentView != null && commentView.getChildCount() != commentCount) {
            // 移除commentView
            if (commentView.getParent() != null) {
                ((ViewGroup)commentView.getParent()).removeView(commentView);
            }
            commentView = null;
        }
        if (commentView == null) {
            // 评论区View没有缓存或者缓存失效，创建新的评论区View
            commentView = onCreateCommentView(context, photosFlow);
            commentsCache.put(photosFlow, commentView);
        }
        if (commentView.getParent() != holder.commentsContainer) {
            Logger.e("addView");
            // 添加
            holder.commentsContainer.addView(commentView);
        }

        /**
         * 没有评论，不显示评论区
         */
        if (photosFlow.comments.size() == 0) {
            holder.commentsContainer.setVisibility(View.GONE);
        } else {
            holder.commentsContainer.setVisibility(View.VISIBLE);
        }

        /**
         * 留言按钮点击事件
         */
        holder.messageBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fragment.showCommentArea(photosFlow);
            }
        });

        /**
         * 如果浏览到自己的图片，显示“删除”按钮
         */
        if (Account.INSTANCE.getNickname().equals(photosFlow.sender)) {
            holder.deleteBtn.setVisibility(View.VISIBLE);
        } else {
            holder.deleteBtn.setVisibility(View.INVISIBLE);
        }

        /**
         * 删除按钮点击事件
         */
        holder.deleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 删除图片
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

    /**
     * 创建评论区View，并设置文字等效果
     * @param context
     * @param photosFlow
     * @return
     */
    private ViewGroup onCreateCommentView(final Context context, final PhotosFlow photosFlow) {
        long startTime = System.currentTimeMillis();
        Logger.e("onCreateCommentView："+startTime);

        LinearLayout container = new LinearLayout(context);
        container.setOrientation(LinearLayout.VERTICAL);

        container.setLayoutParams(
                new ViewGroup.LayoutParams(
                        ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT));

        final List<Comment> commentList = photosFlow.comments;

        for (int i = 0; i < commentList.size(); i++) {
            View view = inflater.inflate(R.layout.layout_comment, container, false);
            container.addView(view);

            final TextView textView = (TextView) view.findViewById(R.id.comment_text);
            textView.setMovementMethod(LinkMovementMethod.getInstance());

            final Comment comment = commentList.get(i);

            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Logger.e("comment  onClick");
                    fragment.showCommentArea(photosFlow, comment);
                }
            });

            /**
             * 构造评论内容：
             * xx回复xx：XXX
             * xx：XXX
             */
            String commentStr;

            final String replyStr = context.getString(R.string.reply);

            if (TextUtils.isEmpty(comment.receiver)) {
                commentStr = comment.sender + "：" + comment.content;
            } else {
                commentStr = comment.sender + replyStr + comment.receiver + "：" + comment.content;
            }

            /**
             * 评论名点击事件
             */
            SpannableStringBuilder builder = new SpannableStringBuilder(commentStr);
            builder.setSpan(new CommentClickableSpan(context, comment.senderId),
                    0, comment.sender.length(), Spanned.SPAN_EXCLUSIVE_INCLUSIVE);

            if (!TextUtils.isEmpty(comment.receiver)) {
                // 回复主消息

                int start = comment.sender.length() + replyStr.length();
                builder.setSpan(new CommentClickableSpan(context, comment.senderId),
                        start, start + comment.receiver.length(), Spanned.SPAN_EXCLUSIVE_INCLUSIVE);
            }

            textView.setText(builder);

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
        @InjectView(R.id.action_delete)
        TextView deleteBtn;
        @InjectView(R.id.time)
        TextView time;

        public ViewHolder(Context context, View itemView) {
            super(itemView);
            ButterKnife.inject(this, itemView);
        }
    }


    /**
     * 评论昵称点击事件
     */
    private static class CommentClickableSpan extends ClickableSpan {

        private Context context;
        private long userId;    // 被点中的用户id

        public CommentClickableSpan(Context context, long userId) {
            this.context = context;
            this.userId = userId;
        }

        @Override
        public void onClick(View widget) {
            Logger.e(userId + "onClick");
            Spannable spannable = (Spannable) ((TextView) widget).getText();
            Selection.removeSelection(spannable);
        }

        @Override
        public void updateDrawState(TextPaint ds) {
            super.updateDrawState(ds);
            ds.setColor(context.getResources().getColor(R.color.text_purple_dark));
            ds.setUnderlineText(false);
        }
    }

}
