package name.walnut.kanjian.app.resource;

import com.google.gson.Gson;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.MediaType;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import name.walnut.kanjian.app.resource.dto.LoginParam;
import name.walnut.kanjian.app.resource.support.Constant;

/**
 * 登陆与注册的rest接口
 *
 * @author walnut
 */
public enum PassportResource {

    INSTANCE;

    /**
     * 登陆
     *
     * @param loginParam 登陆参数
     */
    public void login(LoginParam loginParam) {

        Request request = createRequest(LOGIN_URI, loginParam);

        OkClientInstance.INSTANCE.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Request request, IOException e) {
            }

            @Override
            public void onResponse(Response response) throws IOException {
                String sessionId = response.header("Set-Cookie").split(";")[0].split("=")[1];
                ResourceFactory.addSessionIdIntercept(sessionId);
            }
        });
    }

    /**
     * 验证短信验证码
     * @param phone
     * @param code
     */
    public void validateSMSCode(String phone, String code) {

        Map<String, String> param = new HashMap<>();
        param.put("phone", phone);
        param.put("zone", "86");
        param.put("code", code);
        Request request = createRequest(LOGIN_URI, param);

        OkClientInstance.INSTANCE.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Request request, IOException e) {
            }

            @Override
            public void onResponse(Response response) throws IOException {
                String sessionId = response.header("Set-Cookie").split(";")[0].split("=")[1];
                ResourceFactory.addSessionIdIntercept(sessionId);
            }
        });

    }

     private Request createRequest(String uri, Object param) {
         RequestBody requestBody = RequestBody
                 .create(MediaType.parse("application/json; charset=utf-8"),
                         new Gson().toJson(param));
         Request request = new Request.Builder().url(uri).post(requestBody).build();

         return request;

     }

    private static final String LOGIN_URI = Constant.BASE_URL+"/passport/login";

}
