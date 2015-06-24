package name.walnut.kanjian.app.ui.main;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
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
import name.walnut.kanjian.app.resource.impl.Resource;
import name.walnut.kanjian.app.resource.impl.ResourceWeave;
import name.walnut.kanjian.app.support.ActionBarFragment;
import name.walnut.kanjian.app.support.DividerItemDecoration;
import name.walnut.kanjian.app.ui.Constants;
import name.walnut.kanjian.app.ui.MainActivity;
import name.walnut.kanjian.app.ui.main.action.FetchPhotosFlowAction;
import name.walnut.kanjian.app.utils.Logger;
import name.walnut.kanjian.app.views.CommentView;

/**
 * 首页看照片 Fragment
 */
public class PhotosFlowFragment extends ActionBarFragment implements OnMoreListener{

    private final int ITEM_LEFT_TO_LOAD_MORE = 1;

    @InjectView(R.id.list)
    SuperRecyclerView recyclerView;
    @InjectView(R.id.comment_area)
    CommentView commentArea;

    @ResourceWeave(actionClass = FetchPhotosFlowAction.class)
    public Resource mainResource;

    private List<PhotosFlow> photosFlowList = new ArrayList<>();
    private PhotosFlowAdapter photosFlowAdapter;

    private PhotosFlow targetCommentPhotosFlow; // 评论的消息流
    private Comment targetComment;  // 评论目标

    private int page = 1;   // 当前显示页

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
        View view = inflater.inflate(R.layout.fragment_photos_flow, container, false);

        ButterKnife.inject(this, view);

        recyclerView.setLayoutManager(new LinearLayoutManager(getActionBarActivity()));

        // 设置分割线
        final Drawable divider = getResources().getDrawable(R.drawable.divider);
        RecyclerView.ItemDecoration decoration =
                new DividerItemDecoration(
                        getActionBarActivity(), divider, DividerItemDecoration.VERTICAL_LIST);
        recyclerView.addItemDecoration(decoration);

        recyclerView.setupMoreListener(this, ITEM_LEFT_TO_LOAD_MORE);

        commentArea.setSendClickListener(new CommentView.OnSendClickListener() {
            @Override
            public void onSend(final String message, CommentView view) {
                final PhotosFlow target = targetCommentPhotosFlow;
                if (target != null) {
                    new AsyncTask<Void, Void, Void>() {

                        @Override
                        protected Void doInBackground(Void... params) {
                            //TODO 发送评论消息
                            return null;
                        }

                        @Override
                        protected void onPostExecute(Void aVoid) {
                            super.onPostExecute(aVoid);
                            commentArea.resetComment();
                            // TODO 模拟评论成功
                            Comment comment = new Comment();
                            comment.content = message;
                            target.comments.add(comment);
                            int position = photosFlowList.indexOf(target);
                            photosFlowAdapter.notifyItemChanged(position);

                        }
                    }.execute();

                }
                hideCommentArea();
            }
        });

        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        if (photosFlowAdapter == null) {
            photosFlowAdapter = new PhotosFlowAdapter(this, photosFlowList);

            for (int i = 0; i < 5; i++) {
                PhotosFlow photosFlow = new PhotosFlow();
                photosFlowAdapter.add(photosFlow);
            }

            fetchFirstPagePhotos();
        }
        recyclerView.setAdapter(photosFlowAdapter);

        recyclerView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                hideCommentArea();
                return false;
            }

        });
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.reset(this);
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
        fetchNextPagePhotos();
    }

    /**
     * 显示评论框
     */
    public void showCommentArea(PhotosFlow photosFlow) {
        showCommentArea(photosFlow, null);
    }

    /**
     * 显示评论框
     * @param photosFlow
     * @param comment
     */
    public void showCommentArea(PhotosFlow photosFlow, Comment comment) {
        if (targetCommentPhotosFlow != photosFlow || targetComment != comment) {
            commentArea.setVisibility(View.GONE);
        }
        targetCommentPhotosFlow = photosFlow;
        targetComment = comment;
        commentArea.setVisibility(View.VISIBLE);
        if (targetComment != null) {
            String replyStr = getString(R.string.reply);
            commentArea.setHint(replyStr + "：" + targetComment.sender);
        } else {
            commentArea.setHint("");
        }
        MainActivity activity = (MainActivity) getActionBarActivity();
        activity.hideTab();

        scrollList(photosFlow);
    }

    private void scrollList(PhotosFlow photosFlow) {

        recyclerView.scrollTo(recyclerView.getScrollX(), 0);
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
        page = 1;
        fetchPhotos(page);
    }

    /**
     * 获取下一页照片
     */
    private void fetchNextPagePhotos() {
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
}
