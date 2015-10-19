package name.walnut.kanjian.app.resource;

import name.walnut.kanjian.app.resource.dto.RelationCount;
import retrofit.Call;
import retrofit.http.GET;

/**
 * Created by walnut on 15/10/12.
 */
public interface RelationResource {

    @GET("/relation/count")
    Call<RelationCount> getRelationCount();
}
