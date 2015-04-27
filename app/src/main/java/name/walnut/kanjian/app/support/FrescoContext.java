package name.walnut.kanjian.app.support;

import android.content.Context;

import com.facebook.drawee.backends.pipeline.Fresco;

/**
 * 图片加载
 */
public enum FrescoContext {
    INSTANCE
    ;

    public void init(Context context) {
        Fresco.initialize(context);
    }
}
