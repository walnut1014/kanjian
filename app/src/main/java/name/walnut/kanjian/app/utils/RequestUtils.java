package name.walnut.kanjian.app.utils;

import com.android.volley.Request;
import com.android.volley.toolbox.JsonObjectRequest;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.apache.http.Header;
import org.json.JSONObject;

import java.util.Map;

import name.walnut.kanjian.app.support.JsonRequestBuilder;
import name.walnut.kanjian.app.support.RequestQueueContext;

import com.android.volley.Request;
import com.android.volley.toolbox.JsonObjectRequest;

/**
 * 发送http请求工具类
 * @author walnut
 */
public class RequestUtils {

    /**
     * 发送post请求
     * @param uri 请求路径
     * @param param 请求参数，放在RequestBody中
     * @param listener 请求成功后执行的回调
     */
	public static void postJSON(String uri, Map<String, Object> param, DefalutJSONListener listener ){
		
		JsonObjectRequest jsonRequest = JsonRequestBuilder.newJsonRequest(Request.Method.POST, 
				uri, param, listener);
		
		RequestQueueContext.INSTANCE.getRequestQueue().add(jsonRequest);
	}

    /**
     * 发送get请求
     * @param uri 请求路径
     * @param param 请求参数，追加在url后面的search部分
     * @param listener 请求成功后执行的回调
     */
    public static void getJSON(String uri, Map<String, String> param, DefalutJSONListener listener ){

        String paramStr = convertGetParam(param);

        JsonObjectRequest jsonRequest = JsonRequestBuilder.newJsonRequest(Request.Method.GET,
                uri, param, listener);

        RequestQueueContext.INSTANCE.getRequestQueue().add(jsonRequest);
    }

    private static String convertGetParam(Map<String, String> param) {

        if (param == null || param.isEmpty())
            return null;

        StringBuilder sb = new StringBuilder("?");
        for (String s : param.keySet()) {
            sb.append(s);
            sb.append("=");
            sb.append(param.get(s));
            sb.append("&");
        }
        return sb.toString();
    }

    public static void upload(String uri, RequestParams params, final DefalutJSONListener listener ) {

        AsyncHttpClient client = new AsyncHttpClient();
        client.post(uri, params, new JsonHttpResponseHandler(){

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                listener.onResponse(response);
            }

        });
    }
}
