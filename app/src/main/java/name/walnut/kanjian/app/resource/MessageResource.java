package name.walnut.kanjian.app.resource;

import com.squareup.okhttp.RequestBody;

import java.util.List;

import name.walnut.kanjian.app.resource.dto.PhotoAccessories;
import name.walnut.kanjian.app.resource.dto.PhotoInfo;
import name.walnut.kanjian.app.resource.dto.PhotoOrUser;
import name.walnut.kanjian.app.resource.support.ResourceResult;
import retrofit.Call;
import retrofit.http.GET;
import retrofit.http.Multipart;
import retrofit.http.POST;
import retrofit.http.Part;
import retrofit.http.Path;

/**
 * 消息相关的rest接口.
 *
 * @author walnut
 */
public interface MessageResource {

    /**
     * 上传照片
     * @param photo 照片请求body
     * @return
     */
    @Multipart
    @POST("/photo")
    Call<ResourceResult> uploadPhoto(@Part("photo\"; filename=\"aa\"")
                                     RequestBody photo,
                                     @Part("photoInfo")
                                     PhotoInfo photoInfo);

    /**
     * 获得首页列表
     * @return
     */
    @GET("/photoOrUser")
    Call<List<PhotoOrUser>> photoList();

    /**
     * 通过照片ID获得照片的评论以及回复
     * @param id 照片ID
     * @return
     */
    @GET("/photo/{id}/accessories")
    Call<PhotoAccessories> getPhotoMessage(@Path("id")long id);
}
