package name.walnut.kanjian.app.resource.impl;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.BaseJsonHttpResponseHandler;
import com.loopj.android.http.JsonStreamerEntity;
import com.loopj.android.http.RequestParams;

import org.apache.http.Header;

import org.json.JSONObject;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import name.walnut.kanjian.app.resource.RequestMethod;
import name.walnut.kanjian.app.resource.ResourceAction;
import name.walnut.kanjian.app.resource.ResourceRegister;
import name.walnut.kanjian.app.support.rest.JsonRequestBuilder;
import name.walnut.kanjian.app.support.rest.RequestQueueContext;
import name.walnut.kanjian.app.utils.Logger;

/**
 * 网络请求类
 */
public final class Resource{


    public Resource(ResourceRegister register) {
        this.url = register.getUrl();
        this.method = register.getMethod();
    }

    public Resource addParam(String key, Object value){
        paramMap.put(key, value);
        return this;
    }

    public void clear() {
        paramMap.clear();
    }

    public void setResourceAction(ResourceAction resourceAction) {
        this.resourceAction = resourceAction;
        this.resourceAction.setResource(this);
    }

    public void send(){

        if(method != RequestMethod.UPLOAD) sendJSON();
        else upload();

        clear();
    }

    private void sendJSON() {

        int _method = method == RequestMethod.GET ? Request.Method.GET : Request.Method.POST;
        JsonObjectRequest jsonRequest = JsonRequestBuilder.newJsonRequest(_method,
                getUrl(url), paramMap, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject jsonObject) {
                        resourceAction.onResponse(jsonObject);
                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        resourceAction.onErrorResponse(volleyError);
                    }
                });

        RequestQueueContext.INSTANCE.getRequestQueue().add(jsonRequest);
    }

    // 使用 android-async-http库完成图片上传
    private void upload() {

        try {
            RequestParams params = new RequestParams();
            Set<Map.Entry<String, Object>> sets = paramMap.entrySet();
            for (Map.Entry<String, Object> entry : sets) {
                String key = entry.getKey();
                Object value = entry.getValue();
                if (value instanceof File) {
                    //TODO 对文件类型检查
                    params.put(key, (File) value);
                } else {
                    params.put(key, value);
                }
            }
            AsyncHttpClient client = new AsyncHttpClient();
            String _url = getUrl(url);

            client.addHeader("Cookie", "JSESSIONID="+ RequestQueueContext.INSTANCE.getSessionId() + ";");
            client.post(_url, params, new BaseJsonHttpResponseHandler<JSONObject>() {

                @Override
                public void onSuccess(int statusCode, Header[] headers, String rawJsonResponse, JSONObject response) {
                    resourceAction.onResponse(response);
                }

                @Override
                public void onFailure(int statusCode, Header[] headers, Throwable throwable,
                                                    String rawJsonData, JSONObject errorResponse) {

                    VolleyError volleyError = new VolleyError(throwable);
                    resourceAction.onErrorResponse(volleyError);
                }

                @Override
                protected JSONObject parseResponse(String rawJsonData, boolean isFailure) throws Throwable {
                    Logger.d(rawJsonData);
                    return new JSONObject(rawJsonData);
                }
            });
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            VolleyError volleyError = new VolleyError(e);
            resourceAction.onErrorResponse(volleyError);
        }
    }

    public static String getUrl(String uri) {
        if(!uri.startsWith("/"))
            uri = "/" + uri;
        return CONTEXT_PATH + uri;
    }

    private Map<String, Object> paramMap = new HashMap<>();

    private ResourceAction resourceAction;

    private String url;
    private RequestMethod method;
//    private final static String CONTEXT_PATH = "http://120.25.201.172:8080";
    private final static String CONTEXT_PATH = "http://192.168.1.105:8080";
}
