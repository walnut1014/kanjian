package name.walnut.kanjian.app.resource;

import name.walnut.kanjian.app.resource.support.ResourceResult;
import retrofit.Call;
import retrofit.http.Field;
import retrofit.http.GET;

/**
 * 验证相关rest接口
 * @author walnut
 */
public interface VerificationResource {

    /**
     * 判断手机号是否存在
     * @param phone 手机号
     * @return
     */
    @GET("/phone/existence")
    Call<ResourceResult> phoneIsExist(@Field("phone") String phone);



}
