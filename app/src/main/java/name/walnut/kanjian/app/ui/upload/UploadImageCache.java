package name.walnut.kanjian.app.ui.upload;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import name.walnut.kanjian.app.account.Account;

/**
 * 选择图片缓存
 */
public enum UploadImageCache {

    INSTANCE;

    private static final String CACHE_FILE_NAME = "upload_image";

    private List<Uri> tmpImageUris; // 临时选择的图片

    private List<Uri> imageUris;

    /**
     * 临时保存uri
     * @param uris
     */
    public void addToTmpCache(List<Uri> uris) {
        if (uris == null) {
            return;
        }
        if (tmpImageUris == null) {
            tmpImageUris = new ArrayList<>();
        }
        tmpImageUris.addAll(uris);
    }

    public void clearTmpCache() {
        tmpImageUris.clear();
    }

    /**
     * 将临时路径缓存到本地
     * @param context
     */
    public void cacheImage(Context context) {
        String key = Account.INSTANCE.getMobilePhone();

        imageUris.clear();
        imageUris.addAll(tmpImageUris);
        tmpImageUris.clear();

        Set<String> urisStr = new HashSet<>();
        for (Uri uri : imageUris) {
            urisStr.add(uri.toString());
        }

        SharedPreferences preferences = context.getApplicationContext().getSharedPreferences(CACHE_FILE_NAME, Context.MODE_PRIVATE);
        preferences.edit()
                .putStringSet(key, urisStr).apply();
    }

    /**
     * 获取缓存的图片, 需在{@link name.walnut.kanjian.app.account.Account#init(android.content.Context)}后调用
     * @param context
     * @return
     */
    public void init(Context context) {
        String key = Account.INSTANCE.getMobilePhone();

        imageUris = new ArrayList<>();

        Set<String> tmp = new HashSet<>();
        SharedPreferences preferences = context.getApplicationContext().getSharedPreferences(CACHE_FILE_NAME, Context.MODE_PRIVATE);
        Set<String> urisStr = preferences.getStringSet(key, tmp);

        for (String uriStr : urisStr) {
            imageUris.add(Uri.parse(uriStr));
        }
    }

    public List<Uri> getImageUris() {
        return imageUris;
    }
}
