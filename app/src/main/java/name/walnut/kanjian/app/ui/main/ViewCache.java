package name.walnut.kanjian.app.ui.main;

import android.util.LruCache;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;

/**
 * Created by baiya on 15/6/6.
 */
public class ViewCache extends LruCache<PhotosFlow, View> {

    public ViewCache() {
        super(10);
    }

    @Override
    protected void entryRemoved(boolean evicted, PhotosFlow key, View oldValue, View newValue) {
        super.entryRemoved(evicted, key, oldValue, newValue);

        if (oldValue != null) {
//            ViewGroup parent = (ViewGroup) oldValue.getParent();
//            if (parent != null) {
//                parent.removeView(parent);
//            }
        }
    }
}
