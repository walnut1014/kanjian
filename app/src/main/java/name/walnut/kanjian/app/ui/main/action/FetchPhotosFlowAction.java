package name.walnut.kanjian.app.ui.main.action;

import com.android.volley.VolleyError;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import name.walnut.kanjian.app.support.BaseResourceAction;
import name.walnut.kanjian.app.ui.main.Comment;
import name.walnut.kanjian.app.ui.main.PhotosFlow;
import name.walnut.kanjian.app.ui.main.PhotosFlowAdapter;
import name.walnut.kanjian.app.ui.main.PhotosFlowFragment;
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
        if (flowFragment != null) {
            adapter = flowFragment.getPhotosFlowAdapter();
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

                    photosFlow.addComment(comment);
                }

                photosFlows.add(photosFlow);
            }

            List<PhotosFlow> oldDataSet = adapter.getItems();
            oldDataSet.addAll(photosFlows);
            adapter.setItems(oldDataSet);


            if (flowFragment.recyclerView.getAdapter() != null) {
                adapter.notifyDataSetChanged();
            } else {
                flowFragment.recyclerView.setAdapter(adapter);
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onFailed(Response response) {
        Logger.e(response.getMessage());
    }

    @Override
    public void onErrorResponse(VolleyError volleyError) {
        Logger.e(volleyError+"");
    }
}
