package name.walnut.kanjian.app.ui.main;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import com.malinskiy.superrecyclerview.OnMoreListener;
import com.malinskiy.superrecyclerview.SuperRecyclerView;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import name.walnut.kanjian.app.R;
import name.walnut.kanjian.app.entity.EmptyFooter;
import name.walnut.kanjian.app.push.PushBusProvider;
import name.walnut.kanjian.app.push.message.MessagePushEvent;
import name.walnut.kanjian.app.resource.impl.Resource;
import name.walnut.kanjian.app.resource.impl.ResourceWeave;
import name.walnut.kanjian.app.support.ActionBarFragment;
import name.walnut.kanjian.app.support.DividerItemDecoration;
import name.walnut.kanjian.app.ui.Constants;
import name.walnut.kanjian.app.ui.MainActivity;
import name.walnut.kanjian.app.ui.main.action.DeleteMessageAction;
import name.walnut.kanjian.app.ui.main.action.FetchPhotosFlowAction;
import name.walnut.kanjian.app.ui.main.action.NewMessageCountAction;
import name.walnut.kanjian.app.ui.main.action.RepayAction;
import name.walnut.kanjian.app.entity.Comment;
import name.walnut.kanjian.app.entity.PhotosFlow;
import name.walnut.kanjian.app.utils.Logger;
import name.walnut.kanjian.app.views.CommentView;

/**
 * 首页看照片 Fragment
 */
public class PhotosFlowFragment extends ActionBarFragment
        implements OnMoreListener, SwipeRefreshLayout.OnRefreshListener{

    private final int ITEM_LEFT_TO_LOAD_MORE = 1;

    @InjectView(R.id.list)
    public SuperRecyclerView recyclerView;
    @InjectView(R.id.comment_area)
    CommentView commentArea;

    @ResourceWeave(actionClass = FetchPhotosFlowAction.class)
    public Resource mainResource;

    @ResourceWeave(actionClass = RepayAction.class)
    public Resource repayResource;

    @ResourceWeave(actionClass = NewMessageCountAction.class)
    public Resource newMessageCountResource;

    @ResourceWeave(actionClass = DeleteMessageAction.class)
    public Resource deleteMessageResource;

    private PhotosFlowAdapter photosFlowAdapter;

    private PhotosFlow targetCommentPhotosFlow; // 评论的消息流
    private Comment targetComment;  // 评论目标

    private Header header;  // 顶部提示

    private int page = 1;   // 当前显示页
    private boolean loading = false;    // 是否正在加载

    private long deletePhotoFlowId;  // 被删除的照片id

    private boolean isLastPage = false;   // 是否为最后一页

    @Override
    protected String getTitle() {
        return getString(R.string.title_activity_photos_flow);
    }

    @Override
    public boolean showBack() {
        return false;
    }

    @Override
    protected View getActionBarMenuView() {
        ImageButton view = (ImageButton) LayoutInflater.from(getActionBarActivity()).inflate(R.layout.action_bar_menu_button, null, false);
        view.setImageResource(R.drawable.icon_add_friend);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Constants.Action.ADD_FRIENDS_ACTION);
                startActivity(intent);
            }
        });
        return view;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_photos_flow, container, false);

        ButterKnife.inject(this, view);

        recyclerView.setLayoutManager(new LinearLayoutManager(getActionBarActivity()));

        // 设置分割线
        final Drawable divider = getResources().getDrawable(R.drawable.divider);
        RecyclerView.ItemDecoration decoration =
                new DividerItemDecoration(
                        getActionBarActivity(), divider, DividerItemDecoration.VERTICAL_LIST);
        recyclerView.addItemDecoration(decoration);

        recyclerView.setupMoreListener(this, ITEM_LEFT_TO_LOAD_MORE);
        recyclerView.setRefreshListener(this);

        commentArea.setSendClickListener(new CommentView.OnSendClickListener() {
            @Override
            public void onSend(final String message, CommentView view) {
                final PhotosFlow photosFlow = targetCommentPhotosFlow;
                final Comment comment = targetComment;
                if (photosFlow != null) {
                    long targetId = (comment != null) ? comment.getId() : photosFlow.getId();
                    repayResource.addParam("id", targetId)
                            .addParam("content", message)
                            .send();

                }
                commentArea.resetComment();
                hideCommentArea();
            }
        });

        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        if (photosFlowAdapter == null) {
            List<PhotosFlow> photosFlowList = new ArrayList<>();
            photosFlowAdapter = new PhotosFlowAdapter(this, photosFlowList);
            fetchFirstPagePhotos();
        } else {
            recyclerView.setAdapter(photosFlowAdapter);
        }

        header = new Header();
        photosFlowAdapter.setHeader(header);
        photosFlowAdapter.setFooter(new EmptyFooter());
        showNewsTip(0);
//        showRemindTip(false);

        recyclerView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                hideCommentArea();
                return false;
            }

        });

        PushBusProvider.getInstance().registerSticky(this);

    }

    @Override
    public void onDestroyView() {
        PushBusProvider.getInstance().unregister(this);
        super.onDestroyView();
        ButterKnife.reset(this);
        loading = false;
    }

    @Override
    public void onResume() {
        super.onResume();
        newMessageCountResource.send();
    }

    /**
     * 顶部小黑条
     * @param event
     */
    public void onEventMainThread(MessagePushEvent event) {
        int count = event.getCount();
        showNewsTip(count);
    }

    @OnClick(R.id.action_camera)
    void startPublicPhoto() {
        // 开始发表图片
        Intent intent = new Intent(Constants.Action.UPLOAD_PIC_ACTION);
        startActivity(intent);
    }

    @Override
    public void onMoreAsked(int numberOfItems, int numberBeforeMore, int currentItemPos) {
        Logger.e("onMoreAsked");

        recyclerView.hideMoreProgress();
        if (!isLastPage && !loading) {
            photosFlowAdapter.showFooter();
            fetchNextPagePhotos();
        }
    }

    /**
     * 显示评论框
     */
    public void showCommentArea(PhotosFlowViewHolder viewHolder, PhotosFlow photosFlow) {
        showCommentArea(viewHolder, photosFlow, null);
    }

    /**
     * 显示评论框
     * @param photosFlow
     * @param comment
     */
    public void showCommentArea(final PhotosFlowViewHolder viewHolder,
                                final PhotosFlow photosFlow,
                                final Comment comment) {
        if (targetCommentPhotosFlow != photosFlow || targetComment != comment) {
            commentArea.setVisibility(View.GONE);
        }
        targetCommentPhotosFlow = photosFlow;
        targetComment = comment;
        commentArea.setVisibility(View.VISIBLE);
        if (targetComment != null) {
            String replyStr = getString(R.string.reply);
            commentArea.setHint(replyStr + "：" + targetComment.getSender());
        } else {
            commentArea.setHint("");
        }
        MainActivity activity = (MainActivity) getActionBarActivity();
        activity.hideTab();

        // 适当的加一些延迟
        commentArea.postDelayed(new Runnable() {
            @Override
            public void run() {
                scrollList(viewHolder, photosFlow);
            }
        }, 200);
    }

    /**
     * 将列表滚动到评论框上面
     * @param photosFlow
     */
    private void scrollList(PhotosFlowViewHolder viewHolder, PhotosFlow photosFlow) {

        int[] location = new int[2];
        viewHolder.itemView.getLocationInWindow(location);
        int height = viewHolder.itemView.getHeight();

        int[] commentAreaLocation = new int[2];
        commentArea.getLocationInWindow(commentAreaLocation);

        int offset = (location[1] + height) - commentAreaLocation[1];

        recyclerView.getRecyclerView().scrollBy(0, offset);
    }

    /**
     * 隐藏评论框
     */
    public void hideCommentArea() {
        targetCommentPhotosFlow = null;
        commentArea.setVisibility(View.GONE);
        MainActivity activity = (MainActivity) getActionBarActivity();
        activity.showTab();
    }

    /**
     * 获取第一页照片
     */
    private void fetchFirstPagePhotos() {

        loading = true;
        page = 1;
        fetchPhotos(page);
    }

    /**
     * 获取下一页照片
     */
    private void fetchNextPagePhotos() {

        loading = true;
        page ++;
        fetchPhotos(page);
    }

    /**
     * 根据页码获取照片
     * @param page
     */
    private void fetchPhotos(int page) {
        mainResource.addParam("page", page).send();
    }

    public PhotosFlowAdapter getPhotosFlowAdapter() {
        return photosFlowAdapter;
    }

    /**
     * 显示顶部新消息提醒, 仅消息数量大于0才显示
     * @param newsCount
     */
    public void showNewsTip(int newsCount) {
        showNewsTip(newsCount > 0, newsCount);
    }

    /**
     * 显示顶部新消息提醒
     * @param show
     */
    public void showNewsTip(boolean show, int newsCount) {
        header.setNewsCount(newsCount);
        header.showNewsTip(show);
        photosFlowAdapter.notifyItemChanged(0);
    }

    /**
     * 显示顶部“发照片”提醒
     * @param show
     */
    public void showRemindTip(boolean show) {
        header.setShowRemindTip(show);
        photosFlowAdapter.notifyItemChanged(0);
    }

    @Override
    public void onRefresh() {
        if (!loading) {
            fetchFirstPagePhotos();
        }
    }

    /**
     * 是否为第一页
     * @return
     */
    public boolean isFirstPage() {
        return page == 1;
    }

    /**
     * 删除照片
     */
    public void onDeleteClick(PhotosFlow photosFlow) {
        showMessage(R.string.message_delete_loading);
        deletePhotoFlowId = photosFlow.getId();
        deleteMessageResource
                .addParam("id", Long.toString(photosFlow.getId()))
                .send();
    }

    /**
     * 删除成功
     */
    public void onDeleteSuccess() {
        photosFlowAdapter.removeItemById(deletePhotoFlowId);
        deletePhotoFlowId = 0;
        dismissMessage();
    }

    /**
     * 删除失败
     */
    public void onDeleteFailed() {
        deletePhotoFlowId = 0;
        dismissMessage();
    }

    /**
     * @param isLastPage
     */
    public void setLastPage(boolean isLastPage) {
        this.isLastPage = isLastPage;
    }

    public void onLoadingResult() {
        loading = false;
    }

}
