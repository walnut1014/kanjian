package name.walnut.kanjian.app.support;

import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;

import java.io.File;

/**
 * Created by walnut on 15/10/14.
 */
public class PhotoUtils {

    public static File getImage() {

        Uri uri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
        Cursor cursor = null;
        try {
            cursor = AppContext.getAppContentResolver().query(uri, null,
                    MediaStore.Images.Media.MIME_TYPE + "=?",
                    new String[]{"image/jpeg"}, null);
            if (cursor == null || !cursor.moveToFirst()) {
                return null;
            }
            return new File(cursor.getString(cursor.getColumnIndex(MediaStore.MediaColumns.DATA)));
        }finally {
            cursor.close();
        }
    }
}
