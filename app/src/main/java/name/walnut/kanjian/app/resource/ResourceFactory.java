package name.walnut.kanjian.app.resource;

import com.squareup.okhttp.Interceptor;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import java.io.IOException;

import name.walnut.kanjian.app.resource.support.Constant;
import retrofit.GsonConverterFactory;
import retrofit.Retrofit;

/**
 * Created by walnut on 15/10/12.
 */
public class ResourceFactory {
    public static <T> T createResource(Class<T> clazz) {

       return new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .client(OkClientInstance.INSTANCE).baseUrl(Constant.BASE_URL)
                .build().create(clazz);
    }

    static void addSessionIdIntercept(final String sessionId) {

        OkClientInstance.INSTANCE.interceptors().add(new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Request request = chain.request().newBuilder().addHeader("Cookie",
                        "JSESSIONID=" + sessionId + ";")
                        .build();
                Response response = chain.proceed(request);
                return response;
            }
        });
    }
}

interface OkClientInstance {
    OkHttpClient INSTANCE = new OkHttpClient();
}
