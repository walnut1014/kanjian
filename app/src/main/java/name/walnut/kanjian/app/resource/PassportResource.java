package name.walnut.kanjian.app.resource;

import android.util.Log;

import com.google.gson.Gson;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.MediaType;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;

import java.io.IOException;

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

        RequestBody requestBody = RequestBody
                                    .create(MediaType.parse("application/json; charset=utf-8"),
                                            new Gson().toJson(loginParam));
        Request request = new Request.Builder().url(LOGIN_URI).post(requestBody).build();


        OkClientInstance.INSTANCE.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Request request, IOException e) {
                Log.e("11", "失败");
                e.printStackTrace();
            }

            @Override
            public void onResponse(Response response) throws IOException {
                String sessionId = response.header("Set-Cookie").split(";")[0].split("=")[1];
                ResourceFactory.addSessionIdIntercept(sessionId);
            }
        });
    }

    private static final String LOGIN_URI = Constant.BASE_URL+"/passport/login";

    private static final MediaType MEDIA_TYPE_FORM
            = MediaType.parse("application/x-www-form-urlencoded; charset=utf-8");
}
