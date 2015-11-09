package name.walnut.kanjian.app.newui.utils;

import android.view.MotionEvent;
import android.view.View;

/**
 * Created by linxunjian on 15/11/9.
 */
public class ActionBarSwipeGesture implements View.OnTouchListener {
    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {

        return false;
    }

    public interface TouchCallbacks {
        void SwipeListView(int position);

        void OnClickListView(int position);

        void LoadDataForScroll(int count);

        void onDismiss();
    }

}
