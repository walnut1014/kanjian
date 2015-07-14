package name.walnut.kanjian.app.ui.main.action;

import com.android.volley.VolleyError;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import name.walnut.kanjian.app.R;
import name.walnut.kanjian.app.support.BaseResourceAction;
import name.walnut.kanjian.app.ui.main.bean.Comment;
import name.walnut.kanjian.app.ui.main.bean.PhotosFlow;
import name.walnut.kanjian.app.ui.main.PhotosFlowAdapter;
import name.walnut.kanjian.app.ui.main.PhotosFlowFragment;
import name.walnut.kanjian.app.ui.util.ToastUtils;
import name.walnut.kanjian.app.utils.Logger;

/**
 * 首页看照片action
 */
public class FetchPhotosFlowAction extends BaseResourceAction {
    @Override
    public void onSuccess(Response response) {
        Logger.e(response.getData());

        PhotosFlowFragment flowFragment = (PhotosFlowFragment) getFragment();
        if (flowFragment == null || flowFragment.isDetached()) {
            return;
        }

        PhotosFlowAdapter adapter = flowFragment.getPhotosFlowAdapter();
        // 如果获取的是第一页，需要清空列表后再加入新数据
        if (flowFragment.isFirstPage()) {
            adapter.setItems(new ArrayList<PhotosFlow>());
        }

        List<PhotosFlow> newPhotosFlows = decodeResult(response.getData());

        // 设置是否为最后一页
        flowFragment.setLastPage(newPhotosFlows.isEmpty());

        List<PhotosFlow> photosFlowList = adapter.getItems();
        photosFlowList.addAll(newPhotosFlows);

        // 按时间排序并加入到列表中
        Collections.sort(photosFlowList, new Comparator<PhotosFlow>() {
            @Override
            public int compare(PhotosFlow lhs, PhotosFlow rhs) {
                return (int) (rhs.getSendTime() - lhs.getSendTime());
            }
        });

        adapter.setItems(photosFlowList);

        if (flowFragment.recyclerView.getAdapter() != null) {
            adapter.notifyDataSetChanged();
        } else {
            flowFragment.recyclerView.setAdapter(adapter);
        }
        adapter.hideFooter();

        flowFragment.onLoadingResult();

    }

    @Override
    public void onFailed(Response response) {
        Logger.e(response.getMessage());
        ToastUtils.toast(response.getMessage());

        onResult();
    }

    @Override
    public void onErrorResponse(VolleyError volleyError) {
        Logger.e(volleyError+"");
        ToastUtils.toast(R.string.toast_error_network);

        onResult();
    }

    private void onResult() {
        PhotosFlowFragment flowFragment = (PhotosFlowFragment) getFragment();
        PhotosFlowAdapter adapter;
        if (flowFragment != null && !flowFragment.isDetached()) {
            adapter = flowFragment.getPhotosFlowAdapter();
            // 如果获取的是第一页，需要清空列表后再加入新数据
            flowFragment.recyclerView.setAdapter(adapter);
            adapter.hideFooter();

            flowFragment.onLoadingResult();
        }
    }

    private List<PhotosFlow> decodeResult(String data) {
        List<PhotosFlow> photosFlows = new ArrayList<>();
        try {

            JSONArray jsonArray = new JSONArray(data);
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject itemJson = jsonArray.getJSONObject(i);
                PhotosFlow photosFlow = new PhotosFlow();
                photosFlow.setSender(itemJson.optString("sender"));
                photosFlow.setId(itemJson.optLong("id"));
                photosFlow.setContent(itemJson.optString("content"));
                photosFlow.setRoot(itemJson.optBoolean("root"));
                photosFlow.setSendTime(itemJson.optLong("sendTime"));
                photosFlow.setSenderId(itemJson.optLong("senderId"));
                photosFlow.setPhotoPath(itemJson.optString("photoPath"));
                if(!itemJson.isNull("headPath"))
                    photosFlow.setAvatarPath(itemJson.optString("headPath"));

                List<Comment> commentList = new ArrayList<>();
                JSONArray commentJson = itemJson.getJSONArray("repayMessages");
                for (int j = 0; j < commentJson.length(); j++) {
                    JSONObject itemCommentJson = commentJson.getJSONObject(j);
                    Comment comment = new Comment();
                    comment.setSender(itemCommentJson.getString("sender"));
                    comment.setSenderTime(itemCommentJson.getLong("sendTime"));
                    comment.setParentId(itemCommentJson.getLong("prentId"));
                    comment.setId(itemCommentJson.getLong("id"));
                    comment.setContent(itemCommentJson.getString("content"));
                    comment.setSenderId(itemCommentJson.getLong("senderId"));
                    comment.setRoot(itemCommentJson.getBoolean("root"));
                    comment.setReceiver(itemCommentJson.optString("receiver", null));
                    comment.setReceiverId(itemCommentJson.optLong("receiverId"));

                    commentList.add(comment);
                }

                // 评论排序
                Collections.sort(commentList, new Comparator<Comment>() {
                    @Override
                    public int compare(Comment lhs, Comment rhs) {
                        return (int) (lhs.getSenderTime() - rhs.getSenderTime());
                    }
                });

                photosFlow.setComments(commentList);

                photosFlows.add(photosFlow);
            }


        } catch (JSONException e) {
            e.printStackTrace();
        }
        return photosFlows;
    }
}
