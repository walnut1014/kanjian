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
import name.walnut.kanjian.app.ui.main.Comment;
import name.walnut.kanjian.app.ui.main.PhotosFlow;
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
        PhotosFlowAdapter adapter;
        if (flowFragment != null && !flowFragment.isDetached()) {
            adapter = flowFragment.getPhotosFlowAdapter();
            // 如果获取的是第一页，需要清空列表后再加入新数据
            if (flowFragment.isFirstPage()) {
                adapter.setItems(new ArrayList<PhotosFlow>());
            }
        } else {
            return;
        }

        try {
            List<PhotosFlow> photosFlows = new ArrayList<>();

            JSONArray jsonArray = new JSONArray(response.getData());
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

                Collections.sort(commentList, new Comparator<Comment>() {
                    @Override
                    public int compare(Comment lhs, Comment rhs) {
                        return (int) (lhs.getSenderTime() - rhs.getSenderTime());
                    }
                });

                photosFlow.setComments(commentList);

                photosFlows.add(photosFlow);
            }

            List<PhotosFlow> photosFlowList = adapter.getItems();
            photosFlowList.addAll(photosFlows);

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

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onFailed(Response response) {
        Logger.e(response.getMessage());
        ToastUtils.toast(response.getMessage());
    }

    @Override
    public void onErrorResponse(VolleyError volleyError) {
        Logger.e(volleyError+"");
        ToastUtils.toast(R.string.toast_error_network);
    }
}
