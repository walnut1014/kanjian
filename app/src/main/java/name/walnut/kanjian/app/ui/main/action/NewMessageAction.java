package name.walnut.kanjian.app.ui.main.action;

import com.android.volley.VolleyError;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import name.walnut.kanjian.app.R;
import name.walnut.kanjian.app.support.BaseResourceAction;
import name.walnut.kanjian.app.ui.main.MessageAdapter;
import name.walnut.kanjian.app.ui.main.MessageFragment;
import name.walnut.kanjian.app.ui.main.bean.Message;
import name.walnut.kanjian.app.ui.util.ToastUtils;
import name.walnut.kanjian.app.utils.Logger;

/**
 * 新消息列表
 */
public class NewMessageAction extends BaseResourceAction {
    @Override
    public void onSuccess(Response response) {
        Logger.e(response.getData());

        List<Message> messageList = decodeResponse(response.getData());

        onLoadingFinish(messageList);

    }

    @Override
    public void onFailed(Response response) {
        ToastUtils.toast(response.getMessage());
        onLoadingFinish(null);
    }


    @Override
    public void onErrorResponse(VolleyError volleyError) {
        ToastUtils.toast(R.string.toast_error_network);
        onLoadingFinish(null);
    }


    /**
     * 显示请求结果
     * @param messageList
     */
    private void onLoadingFinish(List<Message> messageList) {
        MessageFragment fragment = (MessageFragment) getFragment();
        if (fragment != null && !fragment.isDetached()) {

            MessageAdapter adapter = new MessageAdapter(getActivity(), messageList);

            fragment.recyclerView.setAdapter(adapter);
        }
    }

    private List<Message> decodeResponse(String data) {
        List<Message> messageList = new ArrayList<>();
        try {
            JSONArray jsonArray = new JSONArray(data);
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                Message message = new Message();
                message.setSenderId(jsonObject.optLong("messageSenderId"));
                message.setId(jsonObject.optLong("messageId"));
                message.setRemark(jsonObject.optString("remark"));
                message.setPhoto(jsonObject.optString("photoPath"));

                JSONObject repayerJson = jsonObject.getJSONObject("repayer");
                message.setRepayerId(repayerJson.optLong("id"));
                message.setRepayerAvatar(repayerJson.optString("headPhotoPath"));
                message.setRepayerName(repayerJson.optString("nickName"));

                messageList.add(message);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return messageList;
    }
}
