package name.walnut.kanjian.app.resource;

public enum ResourceRegister {

    loginResource("passport/login", RequestMethod.POST), //登陆Resource

    registerResource("passport/register", RequestMethod.POST), //完善资料完成注册 Resource
    registerSendResource("passport/register/sendCode", RequestMethod.POST), //发送注册手机验证码
    registerVerifyResource("passport/register/validateCode", RequestMethod.POST), // 注册提交验证码

    forgetPasswordSendResource("passport/forgotPassword/sendCode", RequestMethod.POST), //找回密码发送手机号验证码
    @Deprecated
    forgetPasswordResendResource("passport/forgotPassword/sendCode", RequestMethod.POST), //找回密码重新发送手机号验证码

    forgetPasswordVerifyResource("passport/forgotPassword", RequestMethod.POST), //找回密码提交验证码
    forgetPasswordResetResource("passport/setPassword", RequestMethod.POST), //重设密码
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
