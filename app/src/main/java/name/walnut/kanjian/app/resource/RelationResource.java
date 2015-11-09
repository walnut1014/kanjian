package name.walnut.kanjian.app.resource;

import name.walnut.kanjian.app.resource.dto.MobileRelation;
import name.walnut.kanjian.app.resource.dto.RelationCount;
import name.walnut.kanjian.app.resource.support.ResourceResult;
import retrofit.Call;
import retrofit.http.GET;
import retrofit.http.POST;
import retrofit.http.Path;

/**
 * 好友关系接口
 * @author walnut
 */
public interface RelationResource {

    /**
     * 获得关系添加未读消息数以及好友数
     * @return
     */
    @GET("/relation/count")
    Call<RelationCount> getRelationCount();

    /**
     * 获得所有的用户关系记录,包括邀请以及被邀请
     * @return
     */
    @GET("/relation")
    Call<MobileRelation> getAll();


    /**
     * 邀请用户
     * @return
     */
    @POST("/invite/{id}")
    Call<ResourceResult> invite(@Path("id")long id);

    /**
     * 同意某用户的邀请
     * @param id
     * @return
     */
    @POST("/invite/agreement/{id}")
    Call<ResourceResult> agreeInvite(@Path("id")long id);
}
