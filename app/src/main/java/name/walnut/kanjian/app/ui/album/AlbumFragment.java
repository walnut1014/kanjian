package name.walnut.kanjian.app.ui.album;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import com.malinskiy.superrecyclerview.OnMoreListener;
import com.malinskiy.superrecyclerview.SuperRecyclerView;

import butterknife.ButterKnife;
import butterknife.InjectView;
import name.walnut.kanjian.app.R;
import name.walnut.kanjian.app.account.Account;
import name.walnut.kanjian.app.entity.Comment;
import name.walnut.kanjian.app.entity.PhotosFlow;
import name.walnut.kanjian.app.resource.impl.Resource;
import name.walnut.kanjian.app.resource.impl.ResourceWeave;
import name.walnut.kanjian.app.support.ActionBarFragment;
import name.walnut.kanjian.app.support.DividerItemDecoration;
import name.walnut.kanjian.app.ui.album.action.AlbumDeleteMessageAction;
import name.walnut.kanjian.app.ui.album.action.AlbumRepayAction;
import name.walnut.kanjian.app.ui.album.action.SelfAlbumAction;
import name.walnut.kanjian.app.ui.album.action.UserAlbumAction;
import name.walnut.kanjian.app.views.CommentView;

/**
 * 个人主页
 */
public class AlbumFragment extends ActionBarFragment
        implements OnMoreListener{

    private static final int ITEM_LEFT_TO_LOAD_MORE = 1;
    @InjectView(R.id.list)
    public SuperRecyclerView recyclerView;
    @InjectView(R.id.comment_area)
    CommentView commentArea;

    private PhotosFlow targetCommentPhotosFlow; // 评论的消息流
    private Comment targetComment;  // 评论目标

    @ResourceWeave(actionClass = SelfAlbumAction.class)
    public Resource selfPhotoResource;

    @ResourceWeave(actionClass = UserAlbumAction.class)
    public Resource userPhotoResource;

    @ResourceWeave(actionClass = AlbumDeleteMessageAction.class)
    public Resource deleteMessageResource;

    @ResourceWeave(actionClass = AlbumRepayAction.class)
    public Resource repayResource;

    private long deletePhotoFlowId;  // 被删除的照片id

    private String userName = "";
    private long userId;
    private int page = 1;
    private boolean loading = false;
    private boolean isLastPage = false;

    public static AlbumFragment newInstance() {
        AlbumFragment fragment = new AlbumFragment();
        return fragment;
    }

    @Override
    protected String getTitle() {
        return userName;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent intent = getActivity().getIntent();
        userId = intent.getLongExtra("userId", 0);
        userName = intent.getStringExtra("userName");

        if (isSelfAlbum()) {
            userName = Account.INSTANCE.getNickname();
            userId = Account.INSTANCE.getId();
        }

        fetchPhotoAlbum();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_personal_album, container, false);
        ButterKnife.inject(this, view);

        initRecyclerView();
        initCommentArea();
        return view;
    }

    private void initRecyclerView() {
        recyclerView.setLayoutManager(new LinearLayoutManager(getActionBarActivity()));

        // 设置分割线
        final Drawable divider = getResources().getDrawable(R.drawable.divider);
        RecyclerView.ItemDecoration decoration =
                new DividerItemDecoration(
                        getActionBarActivity(), divider, DividerItemDecoration.VERTICAL_LIST) {
                    @Override
                    public boolean showVerticalFirst() {
                        return true;
                    }
                };
        recyclerView.addItemDecoration(decoration);
        recyclerView.setupMoreListener(this, ITEM_LEFT_TO_LOAD_MORE);

        recyclerView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                hideCommentArea();
                return false;
            }

        });
    }

    /**
     * 是否自己的相簿
     * @return
     */
    private boolean isSelfAlbum() {
        return userId == 0 || userId == Account.INSTANCE.getId();
    }

    private void fetchPhotoAlbum() {
        if (isSelfAlbum()) {
            selfPhotoResource.addParam("page", page).send();
        } else {
            userPhotoResource.addParam("userId", userId)
                    .addParam("page", page)
                    .send();
        }
    }

    @Override
    public void onMoreAsked(int numberOfItems, int numberBeforeMore, int currentItemPos) {
        recyclerView.hideMoreProgress();
        if (!isLastPage && !loading) {
            AlbumAdapter adapter = (AlbumAdapter) recyclerView.getAdapter();
            adapter.showFooter();
            loadNextPage();
        }
    }

    public AlbumAdapter getAdapter() {
        return (AlbumAdapter) recyclerView.getAdapter();
    }

    private void loadFirstPage() {
        loading = true;
        page = 1;
        fetchPhotoAlbum();
    }

    /**
     * 获取下一页
     */
    private void loadNextPage() {
        loading = true;
        page ++;
        fetchPhotoAlbum();
    }

    public void onLoadFailed() {
        AlbumAdapter adapter = getAdapter();
        if (adapter == null) {
            adapter = new AlbumAdapter(this, null);
        }
        recyclerView.setAdapter(adapter);
        loading = false;
        recyclerView.setLoadingMore(false);
        recyclerView.hideMoreProgress();
        adapter.hideFooter();
    }

    public void onLoadSuccess() {
        loading = false;
        recyclerView.setLoadingMore(false);
        recyclerView.hideMoreProgress();
        AlbumAdapter adapter = getAdapter();
        adapter.hideFooter();
    }

    public void setLastPage(boolean isLastPage) {
        this.isLastPage = isLastPage;
    }


    /**
     * 显示评论框
     */
    public void showCommentArea(AlbumViewHolder viewHolder, PhotosFlow photosFlow) {
        showCommentArea(viewHolder, photosFlow, null);
    }

    private void initCommentArea() {
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
    }

    /**
     * 显示评论框
     * @param photosFlow
     * @param comment
     */
    public void showCommentArea(final AlbumViewHolder viewHolder,
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

        // 适当的加一些延迟
        commentArea.postDelayed(new Runnable() {
            @Override
            public void run() {
                scrollList(viewHolder);
            }
        }, 200);
    }

    /**
     * 将列表滚动到评论框上面
     */
    private void scrollList(AlbumViewHolder viewHolder) {

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
        getAdapter().removeItemById(deletePhotoFlowId);
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
}
