//package name.walnut.kanjian.app.ui.album.action;
//
//import com.android.volley.VolleyError;
//
//import org.json.JSONArray;
//import org.json.JSONException;
//import org.json.JSONObject;
//
//import java.util.ArrayList;
//import java.util.Collections;
//import java.util.Comparator;
//import java.util.List;
//
//import name.walnut.kanjian.app.entity.Comment;
//import name.walnut.kanjian.app.entity.PhotosFlow;
//import name.walnut.kanjian.app.ui.album.AlbumAdapter;
//import name.walnut.kanjian.app.ui.album.AlbumFragment;
//import name.walnut.kanjian.app.utils.Logger;
//
///**
// * 相簿action
// */
//public class BaseAlbumAction extends BaseResourceAction{
//    @Override
//    public void onSuccess(Response response) {
//        Logger.e(response.getData());
//
//
//        AlbumFragment fragment = (AlbumFragment) getFragment();
//        if (fragment == null || fragment.isDetached()) {
//            return;
//        }
//        List<PhotosFlow> flowList = parseResponse(response.getData());
//        // 最后一页
//        fragment.setLastPage(flowList.isEmpty());
//
//        AlbumAdapter adapter = (AlbumAdapter) fragment.recyclerView.getAdapter();
//        if (adapter == null) {
//            adapter = new AlbumAdapter(fragment, null);
//        }
//        List<PhotosFlow> dataSet = adapter.getItems();
//        dataSet.addAll(flowList);
//        // 按时间排序并加入到列表中
//        Collections.sort(dataSet, new Comparator<PhotosFlow>() {
//            @Override
//            public int compare(PhotosFlow lhs, PhotosFlow rhs) {
//                return (int) (rhs.getSendTime() - lhs.getSendTime());
//            }
//        });
//        adapter.setItems(dataSet);
//
//        if (fragment.recyclerView.getAdapter() != null) {
//            adapter.notifyDataSetChanged();
//        } else {
//            fragment.recyclerView.setAdapter(adapter);
//        }
//
//        fragment.onLoadSuccess();
//    }
//
//    @Override
//    public void onFailed(Response response) {
//        AlbumFragment fragment = (AlbumFragment) getFragment();
//        if (fragment != null && fragment.isDetached()) {
//            fragment.onLoadFailed();
//        }
//    }
//
//    @Override
//    public void onErrorResponse(VolleyError volleyError) {
//        AlbumFragment fragment = (AlbumFragment) getFragment();
//        if (fragment != null && fragment.isDetached()) {
//            fragment.onLoadFailed();
//        }
//    }
//
//
//    // 解析结果
//    private List<PhotosFlow> parseResponse(String data) {
//        List<PhotosFlow> photosFlows = new ArrayList<>();
//        try {
//
//            JSONArray jsonArray = new JSONArray(data);
//            for (int i = 0; i < jsonArray.length(); i++) {
//                JSONObject itemJson = jsonArray.getJSONObject(i);
//                PhotosFlow photosFlow = new PhotosFlow();
//                photosFlow.setSender(itemJson.optString("sender"));
//                photosFlow.setId(itemJson.optLong("id"));
//                photosFlow.setContent(itemJson.optString("content"));
//                photosFlow.setRoot(itemJson.optBoolean("root"));
//                photosFlow.setSendTime(itemJson.optLong("sendTime"));
//                photosFlow.setSenderId(itemJson.optLong("senderId"));
//                photosFlow.setPhotoPath(itemJson.optString("photoPath"));
//                if(!itemJson.isNull("headPath"))
//                    photosFlow.setAvatarPath(itemJson.optString("headPath"));
//
//                List<Comment> commentList = new ArrayList<>();
//                JSONArray commentJson = itemJson.getJSONArray("repayMessages");
//                for (int j = 0; j < commentJson.length(); j++) {
//                    JSONObject itemCommentJson = commentJson.getJSONObject(j);
//                    Comment comment = new Comment();
//                    comment.setSender(itemCommentJson.getString("sender"));
//                    comment.setSenderTime(itemCommentJson.getLong("sendTime"));
//                    comment.setParentId(itemCommentJson.getLong("prentId"));
//                    comment.setId(itemCommentJson.getLong("id"));
//                    comment.setContent(itemCommentJson.getString("content"));
//                    comment.setSenderId(itemCommentJson.getLong("senderId"));
//                    comment.setRoot(itemCommentJson.getBoolean("root"));
//                    comment.setReceiver(itemCommentJson.optString("receiver", null));
//                    comment.setReceiverId(itemCommentJson.optLong("receiverId"));
//
//                    commentList.add(comment);
//                }
//
//                // 评论排序
//                Collections.sort(commentList, new Comparator<Comment>() {
//                    @Override
//                    public int compare(Comment lhs, Comment rhs) {
//                        return (int) (lhs.getSenderTime() - rhs.getSenderTime());
//                    }
//                });
//
//                photosFlow.setComments(commentList);
//
//                photosFlows.add(photosFlow);
//            }
//
//
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
//        return photosFlows;
//    }
//}
