//package name.walnut.kanjian.app.ui.main.action;
//
//import com.android.volley.VolleyError;
//
//import name.walnut.kanjian.app.R;
//import name.walnut.kanjian.app.ui.main.PhotosFlowFragment;
//import name.walnut.kanjian.app.ui.util.ToastUtils;
//import name.walnut.kanjian.app.utils.Logger;
//
///**
// * 消息列表小黑条
// */
//public class NewMessageCountAction extends BaseResourceAction {
//    @Override
//    public void onSuccess(Response response) {
//        Logger.e(response.getData());
//
//        int count = 0;
//        try {
//            count = Integer.decode(response.getData());
//        } catch (NumberFormatException e) {
//            e.printStackTrace();
//        }
//        PhotosFlowFragment fragment = (PhotosFlowFragment) getFragment();
//        if (fragment != null && !fragment.isDetached()) {
//            fragment.showNewsTip(count);
//        }
//    }
//
//    @Override
//    public void onFailed(Response response) {
//    }
//
//    @Override
//    public void onErrorResponse(VolleyError volleyError) {
//        ToastUtils.toast(R.string.toast_error_network);
//    }
//}
