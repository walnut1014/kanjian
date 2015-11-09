package name.walnut.kanjian.app.test;

import retrofit.Call;
import retrofit.http.GET;

/**
 * Created by walnut on 15/10/11.
 */
public interface RetrofitService {

    @GET("/user/")
    Call<User> list();
}
