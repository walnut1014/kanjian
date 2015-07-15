package name.walnut.kanjian.app.ui.album;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.method.LinkMovementMethod;
import android.util.LruCache;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import name.walnut.kanjian.app.R;
import name.walnut.kanjian.app.account.Account;
import name.walnut.kanjian.app.entity.Comment;
import name.walnut.kanjian.app.entity.EmptyFooter;
import name.walnut.kanjian.app.entity.PhotosFlow;
import name.walnut.kanjian.app.support.HeaderRecyclerViewAdapter;
import name.walnut.kanjian.app.support.KJAlertDialogFragment;
import name.walnut.kanjian.app.ui.Constants;
import name.walnut.kanjian.app.ui.main.CommentClickableSpan;
import name.walnut.kanjian.app.ui.main.DetailPhotoDialogFragment;
import name.walnut.kanjian.app.utils.Logger;
import name.walnut.kanjian.app.views.KJAlertDialog;

/**
 * 相册adapter
 */
public class AlbumAdapter extends HeaderRecyclerViewAdapter<RecyclerView.ViewHolder, Void, PhotosFlow, EmptyFooter>{

    private LruCache<PhotosFlow, ViewGroup> commentsCache;
    private static final int COMMENT_CACHE_LENGTH = 30;
    private Context context;
    private AlbumFragment fragment;
    private LayoutInflater inflater;

    public AlbumAdapter(AlbumFragment fragment, List<PhotosFlow> photosFlowList) {
        super();
        this.fragment = fragment;
        context = fragment.getActionBarActivity();
        inflater = LayoutInflater.from(fragment.getActionBarActivity());
        commentsCache = new LruCache<>(COMMENT_CACHE_LENGTH);
        setFooter(new EmptyFooter());
        if (null == photosFlowList) {
            photosFlowList = new ArrayList<>();
        }
        setItems(photosFlowList);
    }



    @Override
    protected RecyclerView.ViewHolder onCreateHeaderViewHolder(ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    protected RecyclerView.ViewHolder onCreateItemViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.layout_album, parent, false);
        return new AlbumViewHolder(view);
    }

    @Override
    protected RecyclerView.ViewHolder onCreateFooterViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.layout_main_footer, parent, false);
        return new FooterViewHolder(view);
    }

    @Override
    protected void onBindHeaderViewHolder(RecyclerView.ViewHolder holder, int position) {
        // empty
    }

    @Override
    protected void onBindItemViewHolder(RecyclerView.ViewHolder holder, int position) {
        // hasHeader is false
        if (!(holder instanceof AlbumViewHolder)) {
            return;
        }
        final AlbumViewHolder viewHolder = (AlbumViewHolder) holder;
        final PhotosFlow photosFlow = getItem(position);


        /*
         * 初始化评论内容
         */
        initCommentView(viewHolder, photosFlow);

        /*
         * 没有评论，不显示评论区
         */
        if (photosFlow.getComments().size() == 0) {
            viewHolder.commentsContainer.setVisibility(View.GONE);
        } else {
            viewHolder.commentsContainer.setVisibility(View.VISIBLE);
        }

        /*
         * 照片
         */
        viewHolder.photoView.setImageURI(Constants.getFileUri(photosFlow.getPhotoPath()));
        viewHolder.photoView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DetailPhotoDialogFragment.showDialog(
                        fragment.getFragmentManager(), photosFlow.getPhotoPath());
            }
        });

        /*
         * 留言按钮点击事件
         */
        viewHolder.messageBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fragment.showCommentArea(viewHolder, photosFlow);
            }
        });

        /*
         * 如果浏览到自己的图片，显示“删除”按钮
         */
        if (Account.INSTANCE.getId() == photosFlow.getSenderId()) {
            viewHolder.deleteBtn.setVisibility(View.VISIBLE);
        } else {
            viewHolder.deleteBtn.setVisibility(View.INVISIBLE);
        }

        /*
         * 删除按钮点击事件
         */
        viewHolder.deleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 删除图片
                final long timeNow = System.currentTimeMillis();
                // 如果上传时间距今超过24小时，则允许删除
                boolean allowDelete = (timeNow - photosFlow.getSendTime()) > 1000 * 60 * 60 * 24;

                if (true) {

                    new KJAlertDialogFragment()
                            .setContent(context.getString(R.string.dialog_delete_confirm_title))
                            .setPositiveClickListener(new KJAlertDialog.OnKJClickListener() {
                                @Override
                                public void onClick(KJAlertDialog dialog) {
                                    // 确认删除
                                    fragment.onDeleteClick(photosFlow);
                                }
                            })
                            .show(fragment.getFragmentManager());

                } else {
                    new KJAlertDialogFragment()
                            .setContent(context.getString(R.string.dialog_delete_not_allowed_title))
                            .showNegativeButton(false)
                            .setPositiveText(context.getString(R.string.dialog_delete_not_allowed_button_positive))
                            .show(fragment.getFragmentManager());
                }

            }
        });

    }

    @Override
    protected void onBindFooterViewHolder(RecyclerView.ViewHolder holder, int position) {
        // empty
    }

    @Override
    protected final boolean hasHeader() {
        return false;
    }

    @Override
    public void onViewRecycled(RecyclerView.ViewHolder holder) {
        super.onViewRecycled(holder);
        if (holder instanceof AlbumViewHolder) {
            ((AlbumViewHolder) holder).commentsContainer.removeAllViews();  // 移除评论Views
        }
    }

    /**
     * 删除指定photoflowId 的行
     * @param photoflowId
     */
    public void removeItemById(long photoflowId) {
        int position = -1;
        List<PhotosFlow> photosFlowList = getItems();
        for (PhotosFlow photosFlow : photosFlowList) {
            if (photosFlow.getId() == photoflowId) {
                position = photosFlowList.indexOf(photosFlow);
                break;
            }
        }
        if (position == -1) {
            return;
        }
        photosFlowList.remove(position);
        if (hasHeader()) {
            position ++;
        }
        notifyItemRemoved(position);
    }

    private void initCommentView(AlbumViewHolder holder, final PhotosFlow photosFlow) {

        final int commentCount = photosFlow.getComments().size(); // 评论总数

        /**
         * 构建评论区
         */
        ViewGroup commentView = commentsCache.get(photosFlow);

        // 如果缓存中commentView不在holder.commentsContainer中，将view从之前区域移除
        if (commentView != null) {
            ViewGroup oldParent = (ViewGroup) commentView.getParent();
            if (oldParent != null && oldParent != holder.commentsContainer) {
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

        onBindCommentView(commentView, holder, photosFlow);

        if (commentView.getParent() != holder.commentsContainer) {
            // 添加
            holder.commentsContainer.addView(commentView);
        }
    }

    /**
     * 创建评论区View，并设置文字等效果
     * @param context
     * @param photosFlow
     * @return
     */
    private ViewGroup onCreateCommentView(
            final Context context,
            final PhotosFlow photosFlow) {

        LinearLayout container = new LinearLayout(context);
        container.setOrientation(LinearLayout.VERTICAL);

        container.setLayoutParams(
                new ViewGroup.LayoutParams(
                        ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT));

        final List<Comment> commentList = photosFlow.getComments();

        for (int i = 0; i < commentList.size(); i++) {
            View view = inflater.inflate(R.layout.layout_comment, container, false);
            container.addView(view);
        }

        return container;
    }

    /**
     * 绑定评论数据
     * @param commentView
     * @param viewHolder
     * @param photosFlow
     */
    private void onBindCommentView(ViewGroup commentView,
                                   final AlbumViewHolder viewHolder,
                                   final PhotosFlow photosFlow) {

        final List<Comment> commentList = photosFlow.getComments();

        int childCount = commentView.getChildCount();
        for (int i = 0; i < childCount; i++) {
            View view = commentView.getChildAt(i);

            final TextView textView = (TextView) view.findViewById(R.id.comment_text);
            textView.setMovementMethod(LinkMovementMethod.getInstance());

            final Comment comment = commentList.get(i);

            /**
             * 构造评论内容：
             * xx回复xx：XXX
             * xx：XXX
             */
            String commentStr;

            final String replyStr = context.getString(R.string.reply);

            if (TextUtils.isEmpty(comment.getReceiver())) {
                commentStr = comment.getSender() + "：" + comment.getContent();
            } else {
                commentStr = comment.getSender() + replyStr + comment.getReceiver() + "：" + comment.getContent();
            }

            /**
             * 评论名 点击事件
             */
            SpannableStringBuilder builder = new SpannableStringBuilder(commentStr);
            builder.setSpan(
                    new CommentClickableSpan(
                            context, comment.getSenderId(), comment.getSender()),
                    0,
                    comment.getSender().length(),
                    Spanned.SPAN_EXCLUSIVE_INCLUSIVE);

            if (!TextUtils.isEmpty(comment.getReceiver())) {
                // 回复主消息

                int start = comment.getSender().length() + replyStr.length();
                builder.setSpan(
                        new CommentClickableSpan(
                                context, comment.getReceiverId(), comment.getReceiver()),
                        start,
                        start + comment.getReceiver().length(),
                        Spanned.SPAN_EXCLUSIVE_INCLUSIVE);
            }

            textView.setText(builder);

            /**
             * 评论点击事件
             */
            view.setEnabled(Account.INSTANCE.getId() != comment.getSenderId());

            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Logger.e("comment  onClick");

                    fragment.showCommentArea(viewHolder, photosFlow, comment);
                }
            });
        }
    }



    static class FooterViewHolder extends RecyclerView.ViewHolder {

        public FooterViewHolder(View itemView) {
            super(itemView);
        }
    }

}
