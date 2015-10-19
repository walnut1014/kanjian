package name.walnut.kanjian.app.resource.support;

import android.util.Log;

import java.io.IOException;

import retrofit.Callback;
import retrofit.Response;
import retrofit.Retrofit;

/**
 * Created by walnut on 15/10/12.
 */
public abstract class DefaultCallback<T extends ResourceResult> implements Callback {

    @Override
    public void onResponse(Response response, Retrofit retrofit) {

        if(response.isSuccess()) {
            final ResourceResult result = (ResourceResult) response.body();
            if(result.isSuccess())
                success((T)result);
            else
                failure(result.getMessage());
        }else {
            Log.e("Server Error", "服务器异常");
            try {
                Log.e("sdf", response.errorBody().source().readUtf8());
            } catch (IOException e) {
                e.printStackTrace();
            }
            error();
        }
    }

    @Override
    public void onFailure(Throwable t) {
        Log.e("Server Error", "服务器异常", t);
        error();
    }

    public abstract void success(T t);

    public abstract void failure(String message);

    public abstract void error();

}
