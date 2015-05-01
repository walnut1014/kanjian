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
    relationCountResource("relation/count", RequestMethod.GET), //未读消息数以及好友数
    relationListResource("relation/list", RequestMethod.GET), //获得所有添加好友请求,包括已同意，未同意和待验证
    //邀请好友(long id)
    invitFriendResource("relation/invit", RequestMethod.POST),
    friendsResource("relation/friends", RequestMethod.GET),//获得所有的好友

    /*-----------------------公共模块-----------------------*/
    //通过手机号查询用户（String mobilePhone）
    queryUserResource("user", RequestMethod.GET)
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
