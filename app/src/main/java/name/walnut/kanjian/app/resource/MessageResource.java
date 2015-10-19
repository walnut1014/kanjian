package name.walnut.kanjian.app.resource;

import com.squareup.okhttp.RequestBody;

import java.util.List;

import name.walnut.kanjian.app.resource.dto.PhotoMessage;
import name.walnut.kanjian.app.resource.dto.PhotoOrUser;
import name.walnut.kanjian.app.resource.support.ResourceResult;
import retrofit.Call;
import retrofit.http.GET;
import retrofit.http.Multipart;
import retrofit.http.POST;
import retrofit.http.Part;
import retrofit.http.Path;

/**
 * Created by walnut on 15/10/13.
 */
public interface MessageResource {

    /**
     * 上传照片
     * @param photo 照片请求body
     * @return
     */
    @Multipart
    @POST("/photo")
    Call<ResourceResult> updatePhoto(@Part("photo\"; filename=\"aa\"")
                                     RequestBody photo);

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
    @GET("/photo/message/{id}")
    Call<PhotoMessage> getPhotoMessage(@Path("id")long id);
}
