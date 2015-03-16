package name.walnut.kanjian.app.resource.impl;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import name.walnut.kanjian.app.resource.RequestMethod;
import name.walnut.kanjian.app.resource.ResourceAction;
import name.walnut.kanjian.app.resource.ResourceRegister;
import name.walnut.kanjian.app.support.rest.JsonRequestBuilder;
import name.walnut.kanjian.app.support.rest.RequestQueueContext;

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

    public void setResourceAction(ResourceAction resourceAction) {
        this.resourceAction = resourceAction;
        this.resourceAction.setResource(this);
    }

    public void send(){

        if(method != RequestMethod.UPLOAD)
            sendJSON();
        else
            ;
    }

    private void sendJSON() {

        int _method = method == RequestMethod.GET ? Request.Method.GET : Request.Method.POST;
        JsonObjectRequest jsonRequest = JsonRequestBuilder.newJsonRequest(_method,
                url, paramMap, new Response.Listener<JSONObject>() {

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

    private Map<String, Object> paramMap = new HashMap<>();

    private ResourceAction resourceAction;

    private String url;
    private RequestMethod method;

}
