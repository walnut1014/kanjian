package name.walnut.kanjian.app.ui.my.relation.request;

/**
 * 好友请求 entity
 */
public class FriendRequest {

    private String statusStr; // status

    private String mobilePhone; // 手机号

    private RequestState state = RequestState.accept;

    public void setMobilePhone(String mobilePhone) {
        this.mobilePhone = mobilePhone;
    }

    public RequestState getState() {
        return state;
    }

    public void setStatusStr(String statusStr) {
        this.statusStr = statusStr;
        switch (statusStr) {
            case "waitVerify":
                state = RequestState.waitVerify;
                break;
            case "sendRequest":
                state = RequestState.sendRequest;
                break;
            case "accetp":
                state = RequestState.accept;
                break;
            default:
                state = RequestState.unknown;
                break;
        }
    }

    public String getMobilePhone() {
        return mobilePhone;
    }

    public static enum RequestState {
        unknown, waitVerify, sendRequest, accept
    }
}
