package name.walnut.kanjian.app.resource.support;

import com.squareup.okhttp.MediaType;
import com.squareup.okhttp.RequestBody;

import java.io.File;

/**
 * Created by walnut on 15/10/19.
 */
public class UploadUtils {
    public static RequestBody getImageRequestBody(File file) {
        return RequestBody.create(MediaType.parse("image/jpeg"), file);
    }
}
