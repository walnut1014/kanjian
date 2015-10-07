package name.walnut.kanjian.app.newui.main;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.method.LinkMovementMethod;
import android.util.Log;
import android.util.LruCache;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import name.walnut.kanjian.app.R;
import name.walnut.kanjian.app.account.Account;
import name.walnut.kanjian.app.entity.Comment;
import name.walnut.kanjian.app.entity.PhotosFlow;
import name.walnut.kanjian.app.ui.main.CommentClickableSpan;
import name.walnut.kanjian.app.utils.Logger;
import name.walnut.kanjian.app.utils.TimeUtils;

/**
 * Created by linxunjian on 15/9/10.
 */
public class PhotoDetailsActivity extends Activity{

    private PhotosFlow photosFlow;
    private FrameLayout mCommentsContainer;
    private Button mBtnComment;
    private ScrollView mSvComment;
    private TextView mTvDescribe,mTvSendTime;
    private ImageView mIvMainPhoto;
    private LayoutInflater inflater;
    private LruCache<PhotosFlow, ViewGroup> commentsCache;
    private static final int COMMENT_CACHE_LENGTH = 30;
    private final int MSG_MOVE_SCOLL = 100;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo_details);
        inflater = LayoutInflater.from(PhotoDetailsActivity.this);
        photosFlow = new PhotosFlow();
        commentsCache = new LruCache<>(COMMENT_CACHE_LENGTH);

        mBtnComment = (Button)findViewById(R.id.lt_comment);
        mSvComment = (ScrollView)findViewById(R.id.sv_photo_details);

        setFillWindows(PhotoDetailsActivity.this, (View) findViewById(R.id.view_picture),0,0);
        setFillWindows(PhotoDetailsActivity.this, (RelativeLayout) findViewById(R.id.rl_portrait_and_name), 0, 0);

        mBtnComment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int hightOfPicture = findViewById(R.id.view_picture).getLayoutParams().height;
                int hightOfDescribe = findViewById(R.id.rl_portrait_and_name).getLayoutParams().height;
                int nowLocation = mSvComment.getScrollY();
                if (nowLocation < hightOfPicture + hightOfDescribe) {
                    for (int i = 0, h = nowLocation, n = 10; i < n; i++) {
                        h = h + (hightOfPicture + hightOfDescribe) / n;
                        Log.i("PhotoDetailsActivity", String.valueOf(h));
                        mHandler.sendMessageDelayed(mHandler.obtainMessage(MSG_MOVE_SCOLL, h, 0), i * 50);
                    }
                }
            }
        });

        List<Comment> commentList = new ArrayList<Comment>();
        for(int i=0;i<50;i++)
        {
            Comment comment = new Comment();
            comment.setId(100 + i);
            comment.setContent("第" + i + "条评论");
            comment.setParentId(1000 + i);

            if (0==i){
                comment.setRoot(true);
            }else{
                comment.setRoot(false);
            }
            if (i!=0&&i!=3&&i!=5)
            {
                comment.setSender("Coder_Lin");
                comment.setReceiver("第" + i + "个Receiver");
            }else {
                comment.setSender("第" + i + "个Receiver");
                comment.setReceiver(null);
            }
            comment.setSenderTime(System.currentTimeMillis());
            comment.setSenderId(10000 + i);
            commentList.add(comment);
        }
        photosFlow.setComments(commentList);

        mCommentsContainer = (FrameLayout)findViewById(R.id.fl_comments_container);
        initCommentView(mCommentsContainer, photosFlow);
        if (photosFlow.getComments().size() == 0) {
            mCommentsContainer.setVisibility(View.GONE);
        } else {
            mCommentsContainer.setVisibility(View.VISIBLE);
        }
    }


    private Handler mHandler = new Handler() {

        public void handleMessage(final Message msg) {

            switch (msg.what) {
                case MSG_MOVE_SCOLL:
                    mSvComment.scrollTo(0, msg.arg1);
                    break;
                default:
                    break;
            }
        }

    };

    private void setFillWindows(Context context,View view,int subWidth,int subHight)
    {
        WindowManager wm = (WindowManager)context.getSystemService(Context.WINDOW_SERVICE);
        ViewGroup.LayoutParams layoutParams = (RelativeLayout.LayoutParams)view.getLayoutParams();
        layoutParams.height = wm.getDefaultDisplay().getHeight()-subHight;
        layoutParams.width= wm.getDefaultDisplay().getWidth()-subWidth;
        view.setLayoutParams(layoutParams);
    }

    private void initCommentView(FrameLayout commentsContainer, final PhotosFlow photosFlow) {

        final int commentCount = photosFlow.getComments().size(); // 评论总数

        /**
         * 构建评论区
         */
        ViewGroup commentView = commentsCache.get(photosFlow);

        // 如果缓存中commentView不在holder.commentsContainer中，将view从之前区域移除
        if (commentView != null) {
            ViewGroup oldParent = (ViewGroup) commentView.getParent();
            if (oldParent != null && oldParent != commentsContainer) {
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
            commentView = onCreateCommentView(PhotoDetailsActivity.this, photosFlow);
            commentsCache.put(photosFlow, commentView);
        }

        onBindCommentView(commentView, commentsContainer, photosFlow);

        if (commentView.getParent() != commentsContainer) {
            // 添加
            commentsContainer.addView(commentView);
        }

    }

    private ViewGroup onCreateCommentView(final Context context, final PhotosFlow photosFlow) {

        LinearLayout container = new LinearLayout(context);
        container.setOrientation(LinearLayout.VERTICAL);
        container.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));

        final List<Comment> commentList = photosFlow.getComments();

        for (int i = 0; i < commentList.size(); i++) {
            View view = inflater.inflate(R.layout.layout_comment, container, false);
            container.addView(view);
        }

        return container;
    }

    public void onViewRecycled(FrameLayout commentsContainer) {
        commentsContainer.removeAllViews();  // 移除评论Views
    }

    private void onBindCommentView(ViewGroup commentView, final FrameLayout commentsContainer, final PhotosFlow photosFlow) {

        final List<Comment> commentList = photosFlow.getComments();

        int childCount = commentView.getChildCount();
        for (int i = 0; i < childCount; i++) {
            View view = commentView.getChildAt(i);
            final TextView tvCommentName = (TextView) view.findViewById(R.id.comment_name);
            final TextView tvComment = (TextView) view.findViewById(R.id.comment_text);
            final TextView tvCommentTime = (TextView) view.findViewById(R.id.tv_comment_time);
            tvCommentName.setMovementMethod(LinkMovementMethod.getInstance());
            final Comment comment = commentList.get(i);
            tvCommentTime.setText(TimeUtils.getTimeDiff(comment.getSenderTime()));
            tvComment.setText(comment.getContent());
            /**
             * 构造评论内容：
             * xx回复xx：XXX
             * xx：XXX
             */
            String commentStr;
            final String replyStr = getResources().getString(R.string.reply);
            if (TextUtils.isEmpty(comment.getReceiver())) {
                commentStr = comment.getSender() +"：";//+ comment.getContent();
            } else {
                commentStr = comment.getSender() + replyStr + comment.getReceiver() + "：" ;//+ comment.getContent();
            }

            /**
             * 评论名 点击事件
             */
            SpannableStringBuilder builder = new SpannableStringBuilder(commentStr);
            builder.setSpan(new CommentClickableSpan(PhotoDetailsActivity.this, comment.getSenderId(), comment.getSender()),0, comment.getSender().length(), Spanned.SPAN_EXCLUSIVE_INCLUSIVE);
            if (!TextUtils.isEmpty(comment.getReceiver())) {
                int start = comment.getSender().length() + replyStr.length();
                builder.setSpan(new CommentClickableSpan(PhotoDetailsActivity.this, comment.getReceiverId(), comment.getReceiver()), start, start + comment.getReceiver().length(), Spanned.SPAN_EXCLUSIVE_INCLUSIVE);
                //builder.setSpan(new CommentTextSpan(PhotoDetailsActivity.this,20, Color.WHITE,true), start + comment.getReceiver().length()+2,commentStr.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                }
            tvCommentName.setText(builder);

            /**
             * 评论点击事件
             */
            if (Account.INSTANCE.getId() == comment.getSenderId()) {
                view.setEnabled(false);
            } else {
                view.setEnabled(true);
            }

            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Logger.e("comment  onClick");
                    //显示评论框
                }
            });
        }
    }



}
