package name.walnut.kanjian.app.resource;

import name.walnut.kanjian.app.resource.dto.RelationCount;
import retrofit.Call;
import retrofit.http.GET;

/**
 * 好友关系几口
 */
public interface RelationResource {

    /**
     * 获得关系添加未读消息数以及好友数
     * @return
     */
    @GET("/relation/count")
    Call<RelationCount> getRelationCount();
}
