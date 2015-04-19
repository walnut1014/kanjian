package name.walnut.kanjian.app.resource;

public enum ResourceRegister {

    /*-----------------------passport模块-----------------------*/
    loginResource("passport/login", RequestMethod.POST), //登陆Resource
    registerResource("passport/register", RequestMethod.UPLOAD), //完善资料完成注册 Resource
    registerSendResource("passport/register/sendCode", RequestMethod.GET), //发送注册手机验证码
    forgetPasswordSendResource("passport/forgotPassword/sendCode", RequestMethod.POST), //找回密码发送手机号验证码
    forgetPasswordResetResource("passport/setPassword", RequestMethod.POST), //重设密码
    smsValidateResource("passport/smsvalidate", RequestMethod.POST), //验证验证码

    /*-----------------------relation模块-----------------------*/
    /**
     * 获得好友请求数以及我的好友数
     * 返回 ｛
     *              invitedCount：0, //被邀请为好友的个数
     *              friendCount：0 //好友数
     *          ｝
     */
    relationCountResource("relation/count", RequestMethod.GET),

    

    /**
     * 邀请好友
     * 请求参数 {mobilephone:"130909998888"}
     * 返回 {
     *          success:true
     *      }
     */
    invitFriendResource("relation/invit", RequestMethod.POST)
    ;

    ResourceRegister(String url, RequestMethod method) {
        this.url = url;
        this.method = method;
    }

    public String getUrl() {
        return url;
    }

    public RequestMethod getMethod() {
        return method;
    }

    private String url;
    private RequestMethod method;
}
