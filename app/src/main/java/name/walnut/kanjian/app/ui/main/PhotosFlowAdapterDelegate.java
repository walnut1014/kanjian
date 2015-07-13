package name.walnut.kanjian.app.ui.main;

import android.content.Context;
import android.content.Intent;
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

import java.util.List;

import name.walnut.kanjian.app.R;
import name.walnut.kanjian.app.account.Account;
import name.walnut.kanjian.app.support.KJAlertDialogFragment;
import name.walnut.kanjian.app.ui.Constants;
import name.walnut.kanjian.app.ui.main.bean.Comment;
import name.walnut.kanjian.app.ui.main.bean.PhotosFlow;
import name.walnut.kanjian.app.ui.util.ToastUtils;
import name.walnut.kanjian.app.utils.Logger;
import name.walnut.kanjian.app.utils.TimeUtils;
import name.walnut.kanjian.app.views.KJAlertDialog;

/**
 * 照片列表 adapter
 */
public class PhotosFlowAdapterDelegate implements RecyclerViewAdapterDelegate<PhotosFlow> {

    private LruCache<PhotosFlow, ViewGroup> commentsCache;
    private static final int COMMENT_CACHE_LENGTH = 30;
    private PhotosFlowFragment fragment;
    private LayoutInflater inflater;
    private Context context;
    private List<PhotosFlow> photosFlowList;

    public PhotosFlowAdapterDelegate(PhotosFlowFragment fragment, List<PhotosFlow> list) {
        super();
        commentsCache = new LruCache<>(COMMENT_CACHE_LENGTH);
        this.fragment = fragment;
        inflater = LayoutInflater.from(fragment.getActionBarActivity());
        context = fragment.getActionBarActivity();
        photosFlowList = list;
    }

    @Override
    public PhotosFlowViewHolder onCreateItemViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.layout_photos_flow, parent, false);
        PhotosFlowViewHolder viewHolder = new PhotosFlowViewHolder(context, view);
        return viewHolder;
    }

    @Override
    public void setDataSet(List<PhotosFlow> dataSet) {
        photosFlowList = dataSet;
    }

    @Override
    public List<PhotosFlow> getDataSet() {
        return photosFlowList;
    }

    @Override
    public void onBindItemViewHolder(final PhotosFlowViewHolder holder, int position) {
        long startTime = System.currentTimeMillis();

        final PhotosFlow photosFlow = photosFlowList.get(position);

        /*
         * 初始化评论内容
         */
        initCommentView(holder, photosFlow);

        /*
         * 没有评论，不显示评论区
         */
        if (photosFlow.getComments().size() == 0) {
            holder.commentsContainer.setVisibility(View.GONE);
        } else {
            holder.commentsContainer.setVisibility(View.VISIBLE);
        }

        /*
         * 留言按钮点击事件
         */
        holder.messageBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fragment.showCommentArea(holder, photosFlow);
            }
        });

        /*
         * 如果浏览到自己的图片，显示“删除”按钮
         */
        if (Account.INSTANCE.getNickname().equals(photosFlow.getSender())) {
            holder.deleteBtn.setVisibility(View.VISIBLE);
        } else {
            holder.deleteBtn.setVisibility(View.INVISIBLE);
        }

        /*
         * 删除按钮点击事件
         */
        holder.deleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 删除图片
                final long timeNow = System.currentTimeMillis();
                // 如果上传时间距今超过24小时，则允许删除
                boolean allowDelete = (timeNow - photosFlow.getSendTime()) > 1000 * 60 * 60 * 24;

                if (allowDelete) {

                    new KJAlertDialogFragment()
                            .setContent(context.getString(R.string.dialog_delete_confirm_title))
                            .setPositiveClickListener(new KJAlertDialog.OnKJClickListener() {
                                @Override
                                public void onClick(KJAlertDialog dialog) {
                                    // 确认删除
                                    ToastUtils.toast("确认删除");
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


        // 设置内容
        holder.descriptionTv.setText(photosFlow.getContent());
        if (TextUtils.isEmpty(photosFlow.getContent())) {
            holder.descriptionTv.setVisibility(View.GONE);
        } else {
            holder.descriptionTv.setVisibility(View.VISIBLE);
        }
        holder.submitterTv.setText(photosFlow.getSender());
        holder.photoView.setImageURI(Constants.getFileUri(photosFlow.getPhotoPath()));
        holder.timeTv.setText(TimeUtils.getTimeDiff(photosFlow.getSendTime()));
        if(photosFlow.getAvatarPath() != null)
         holder.avatarView.setImageURI(Constants.getFileUri(photosFlow.getAvatarPath()));
        // 个人界面的点击监听器
        View.OnClickListener personalPageClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startPersonalPageActivity(context, photosFlow.getSenderId(), photosFlow.getSender());
            }
        };
        holder.submitterTv.setOnClickListener(personalPageClickListener);
        holder.avatarView.setOnClickListener(personalPageClickListener);
        holder.photoView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DetailPhotoDialogFragment.showDialog(
                        fragment.getFragmentManager(), photosFlow.getPhotoPath());
            }
        });

        long useTime = System.currentTimeMillis() - startTime;
    }

    @Override
    public void onViewRecycled(PhotosFlowViewHolder holder) {
        holder.commentsContainer.removeAllViews();  // 移除评论Views
    }


    private void initCommentView(PhotosFlowViewHolder holder, final PhotosFlow photosFlow) {

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
            commentView = onCreateCommentView(context, holder, photosFlow);
            commentsCache.put(photosFlow, commentView);
        }
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
            final PhotosFlowViewHolder viewHolder,
            final PhotosFlow photosFlow) {
        long startTime = System.currentTimeMillis();
        Logger.e("onCreateCommentView："+startTime);

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

            final TextView textView = (TextView) view.findViewById(R.id.comment_text);
            textView.setMovementMethod(LinkMovementMethod.getInstance());

            final Comment comment = commentList.get(i);

            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Logger.e("comment  onClick");
                    fragment.showCommentArea(viewHolder, photosFlow, comment);
                }
            });

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
             * 评论名点击事件
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

        }

        long useTime = System.currentTimeMillis() - startTime;
        Logger.e("onCreateCommentView：" + useTime);

        return container;
    }


    /**
     * 跳转到个人主页
     * @param context
     * @param userId
     * @param userName
     */
    private void startPersonalPageActivity(Context context, long userId, String userName) {
        Intent intent = new Intent();
        intent.putExtra("userId", userId);
        intent.putExtra("userName", userName);
        intent.setAction(Constants.Action.PERSONAL_PAGE_ACTION);
        context.startActivity(intent);
    }

}
