package name.walnut.kanjian.app.resource;

import name.walnut.kanjian.app.resource.support.ResourceResult;
import retrofit.Call;
import retrofit.http.Field;
import retrofit.http.GET;
import retrofit.http.POST;
import retrofit.http.Query;

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
    Call<ResourceResult> phoneIsExist(@Query("phone") String phone);

    /**
     * 验证短信验证码
     * @return
     */
    @POST("/sms/validation")
    Call<ResourceResult> smsCodeValidation(@Query("phone") String phone,
                                           @Query("zone") String zone,
                                           @Query("code") String code);

}
