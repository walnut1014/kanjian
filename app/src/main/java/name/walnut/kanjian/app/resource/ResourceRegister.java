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
     * 获得所有添加好友请求,包括已同意，未同意和待验证
     * 返回 ｛
     *          realtions:[{
     *              mobilephone:'1300000000',
     *              status:0,1,2 //(0发送请求待验证，1接受他人请求，2已建立好友关系)
     *          }]
     *       ｝
     */
    relationListResource("relation/list", RequestMethod.GET),

    /**
     * 邀请好友
     * 请求参数 {mobilephone:"130909998888"}
     * 返回 {
     *          success:true
     *      }
     */
    invitFriendResource("relation/invit", RequestMethod.POST),

    /**
     * 获得所有的好友
     * 返回 {
     *    friends[{
     *              mobilephone:'1300000000',
     *              photoCoount: 5 //照片数
     *          }]
     * }
     */
    friendsResource("relation/friends", RequestMethod.GET)
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
