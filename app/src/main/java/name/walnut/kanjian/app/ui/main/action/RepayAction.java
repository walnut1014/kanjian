//package name.walnut.kanjian.app.ui.main.action;
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
//import name.walnut.kanjian.app.R;
//import name.walnut.kanjian.app.entity.Comment;
//import name.walnut.kanjian.app.entity.PhotosFlow;
//import name.walnut.kanjian.app.ui.main.PhotosFlowAdapter;
//import name.walnut.kanjian.app.ui.main.PhotosFlowFragment;
//import name.walnut.kanjian.app.ui.util.ToastUtils;
//import name.walnut.kanjian.app.utils.Logger;
//
///**
// * 消息回复action
// */
//public class RepayAction extends BaseResourceAction{
//    @Override
//    public void onSuccess(Response response) {
//        Logger.e(response.getData());
//
//        try {
//
//            PhotosFlow photosFlow = parseResponse(response.getData());
//
//            // 刷新
//            PhotosFlowFragment flowFragment = (PhotosFlowFragment) getFragment();
//            PhotosFlowAdapter adapter = flowFragment.getPhotosFlowAdapter();
//            List<PhotosFlow> flowList = adapter.getItems();
//            List<PhotosFlow> removeList = new ArrayList<>();
//            for (PhotosFlow item : flowList) {
//                if (item.getId() == photosFlow.getId()) {
//                    removeList.add(item);
//                }
//            }
//            for (PhotosFlow item : removeList) {
//                flowList.remove(item);
//            }
//            removeList.clear();
//            flowList.add(photosFlow);
//
//            Collections.sort(flowList, new Comparator<PhotosFlow>() {
//                @Override
//                public int compare(PhotosFlow lhs, PhotosFlow rhs) {
//                    return (int) (rhs.getSendTime() - lhs.getSendTime());
//                }
//            });
//
//            adapter.setItems(flowList);
//            adapter.notifyDataSetChanged();
//
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
//
//    }
//
//    @Override
//    public void onFailed(Response response) {
//        Logger.e(response.getMessage());
//        ToastUtils.toast(response.getMessage());
//    }
//
//    @Override
//    public void onErrorResponse(VolleyError volleyError) {
//        Logger.e(volleyError.getMessage());
//        ToastUtils.toast(R.string.toast_error_network);
//    }
//
//    private PhotosFlow parseResponse(String data) throws JSONException {
//        PhotosFlow photosFlow = new PhotosFlow();
//        // 解析数据为PhotosFlow对象
//        JSONObject itemJson = null;
//        itemJson = new JSONObject(data);
//        photosFlow.setSender(itemJson.optString("sender"));
//        photosFlow.setId(itemJson.optLong("id"));
//        photosFlow.setContent(itemJson.optString("content"));
//        photosFlow.setRoot(itemJson.optBoolean("root"));
//        photosFlow.setSendTime(itemJson.optLong("sendTime"));
//        photosFlow.setSenderId(itemJson.optLong("senderId"));
//        photosFlow.setPhotoPath(itemJson.optString("photoPath"));
//        photosFlow.setAvatarPath(itemJson.getString("headPath"));
//
//        List<Comment> commentList = new ArrayList<>();
//        JSONArray commentJson = itemJson.getJSONArray("repayMessages");
//        for (int j = 0; j < commentJson.length(); j++) {
//            JSONObject itemCommentJson = commentJson.getJSONObject(j);
//            Comment comment = new Comment();
//            comment.setSender(itemCommentJson.getString("sender"));
//            comment.setSenderTime(itemCommentJson.getLong("sendTime"));
//            comment.setParentId(itemCommentJson.getLong("prentId"));
//            comment.setId(itemCommentJson.getLong("id"));
//            comment.setContent(itemCommentJson.getString("content"));
//            comment.setSenderId(itemCommentJson.getLong("senderId"));
//            comment.setRoot(itemCommentJson.getBoolean("root"));
//            comment.setReceiver(itemCommentJson.optString("receiver", null));
//            comment.setReceiverId(itemCommentJson.optLong("receiverId"));
//
//            commentList.add(comment);
//        }
//
//        Collections.sort(commentList, new Comparator<Comment>() {
//            @Override
//            public int compare(Comment lhs, Comment rhs) {
//                return (int) (lhs.getSenderTime() - rhs.getSenderTime());
//            }
//        });
//
//        photosFlow.setComments(commentList);
//
//        return photosFlow;
//    }
//}
