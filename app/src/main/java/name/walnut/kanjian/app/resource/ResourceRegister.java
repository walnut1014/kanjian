package name.walnut.kanjian.app.resource;

public enum ResourceRegister {

    /*-----------------------passport模块-----------------------*/
    /**
     * @param: deviceToken //友盟推送的设备Token
     */
    loginResource("passport/login", RequestMethod.POST), //登陆Resource
    /**
     * @param: deviceToken //友盟推送的设备Token
     */
    registerResource("passport/register", RequestMethod.UPLOAD), //完善资料完成注册 Resource
    registerSendResource("passport/register/sendCode", RequestMethod.GET), //发送注册手机验证码
    forgetPasswordSendResource("passport/forgotPassword/sendCode", RequestMethod.GET), //找回密码发送手机号验证码
    forgetPasswordResetResource("passport/setPassword", RequestMethod.POST), //重设密码

    /*-----------------------relation模块-----------------------*/
    relationCountResource("relation/count", RequestMethod.GET), //未读消息数以及好友数
    /*
     * agree 是否同意
     * invited 是否为被邀请人
     */
    relationListResource("relation/list", RequestMethod.GET), //获得所有添加好友请求,包括已同意，未同意和待验证
    //邀请好友(long id)
    invitFriendResource("relation/invit", RequestMethod.POST),
    //同意他人的邀请(long id)
    agreeInvitResource("relation/agreeInvit", RequestMethod.POST),
    friendsResource("relation/friends", RequestMethod.GET),//获得所有的好友

    /*-----------------------公共模块-----------------------*/
    //通过手机号查询用户（String mobilePhone）
    //查询多手机号(String mobilephones：'13000000000,13000000001,13000000002')
    queryUserResource("user", RequestMethod.GET),
    smsValidateResource("smsvalidate", RequestMethod.POST), //验证验证码
    isLoginResource("passport/isLogin", RequestMethod.GET), //是否登陆
    //当前用户
    currentUserResource("user/current", RequestMethod.GET),

    /*-----------------------设置模块-----------------------*/
    //修改头像（File headPhoto）
    modifyHeedPohotoResource("setting/modifyHeadPhoto", RequestMethod.UPLOAD),
    //修改昵称(Stirng nickName)
    modifyNickNameResource("setting/modifyNickName", RequestMethod.POST),
    //验证手机号是否为当前登陆手机号(String mobilephone),修改密码发送验证码前使用
    isCurrMobilephoneResource("setting/isCurrMobilephone", RequestMethod.GET),
    //退出，*没有返回值
    exitResource("setting/exit", RequestMethod.POST),

    /*-----------------------主功能-----------------------*/
    //设置用户照片选择时间
    setSelectTimeResource("message/photo/selectTime", RequestMethod.POST),
    //上传图片(File photo, String content)
    uploadPhotoResource("message/photo", RequestMethod.UPLOAD),
    //获得用户还剩多长时间可以上传照片
    residueTimeResource("message/photo/residueTime", RequestMethod.GET),
    //通过照片ID删除照片(long id)
    deletePhotoResource("message/photo/delete", RequestMethod.POST),
    //针对某条消息做评论(long id //父消息的ID, String content)
    repayResource("message/repay", RequestMethod.POST),
    //主页消息(int page //页数)
    mainResource("message/main", RequestMethod.GET),
    //获得新消息数
    newMessageCountResource("notification/count", RequestMethod.GET),
    //获得新消息列表数据
    newMessageResource("notification/newMessage", RequestMethod.GET),
    //用户自己的相册(int page //页数)
    selfPhotoResource("message/self", RequestMethod.GET),
    //指定ID的相册(int page //页数, long userId // 用户id)
    userPhotoResource("message", RequestMethod.GET),
    //删除主消息(long id)
    deleteMessageResource("message/delete", RequestMethod.POST),
    //读取新消息(long messageId)
    readNotificationResource("notification/read", RequestMethod.POST)
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
