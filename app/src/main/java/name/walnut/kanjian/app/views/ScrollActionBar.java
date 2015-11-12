package name.walnut.kanjian.app.views;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Scroller;

/**
 * Created by linxunjian on 15/11/10.
 */

public class ScrollActionBar extends ViewGroup {

    private static final String TAG = "ScrollActionBar";

    public static final int SCREEN_CONTENT = 0; //第一个页面,内容显示页面
    public static final int SCREEN_ACTION = 1;  //第二个页面,操作按钮页面
    private static final int SCREEN_INVALID = -1;
    private int mCurrentScreen;     //当前页面
    private int mNextScreen = SCREEN_INVALID;

    private Scroller mScroller;
    private VelocityTracker mVelocityTracker;
    private int mTouchSlop;

    private float mLastMotionX;     //上一次操作的X坐标
    private float mLastMotionY;     //上一次操作的Y坐标

    private final static int TOUCH_STATE_REST = 0;          //滑动状态:滑动完成
    private final static int TOUCH_STATE_SCROLLING = 1;     //滑动状态:滑动中
    private static final int SNAP_VELOCITY = 1000;          //滑动速度
    public int mTouchState = TOUCH_STATE_REST;              //当前滑动状态
    private boolean mAllowLongPress;

    public ScrollActionBar(Context context) {
        this(context, null, 0);

    }

    public ScrollActionBar(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        mScroller = new Scroller(getContext());
        mCurrentScreen = SCREEN_CONTENT;
        //获得最小触控感应单位
        mTouchSlop = ViewConfiguration.get(getContext()).getScaledTouchSlop();

    }

    public ScrollActionBar(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        measureViews(widthMeasureSpec, heightMeasureSpec);
    }


    public void measureViews(int widthMeasureSpec, int heightMeasureSpec) {
        View viewContent = getChildAt(0);
        View viewAction = getChildAt(1);
        viewContent.measure(widthMeasureSpec, heightMeasureSpec);
        viewAction.measure(viewAction.getLayoutParams().width, heightMeasureSpec);
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        int childCount = getChildCount();
        if (childCount != 2) {
            throw new IllegalStateException("The childCount of SlidingMenu must be 2");
        }
        WindowManager wm = (WindowManager) getContext().getSystemService(Context.WINDOW_SERVICE);
        Display display = wm.getDefaultDisplay();
        DisplayMetrics metrics = new DisplayMetrics();
        display.getMetrics(metrics);
        View viewContent = getChildAt(0);
        View viewAction = getChildAt(1);
        viewContent.layout(0, 0, metrics.widthPixels, viewContent.getMeasuredHeight());
        viewAction.layout(viewContent.getRight(), 0, metrics.widthPixels + viewAction.getMeasuredWidth(), viewAction.getMeasuredHeight());
    }

   /*
    *加载完成,获得焦点
    */
    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        Log.i(TAG,"onFinishInflate");
        View child;
        for (int i = 0; i < getChildCount(); i++) {
            child = getChildAt(i);
            child.setFocusable(true);
            child.setClickable(true);
        }
    }

    /*
    *事件预处理,主要用于加载布局,以及判断滑动状态.
    *
    *
    */
    @Override
    public boolean onInterceptTouchEvent(MotionEvent event) {

        final int action = event.getAction();
        if ((action == MotionEvent.ACTION_MOVE) && (mTouchState != TOUCH_STATE_REST)) {
            return true;
        }

        final float x = event.getX();
        final float y = event.getY();
        switch (action) {
            case MotionEvent.ACTION_MOVE:
                final int xDiff = (int) Math.abs(x - mLastMotionX);
                final int yDiff = (int) Math.abs(y - mLastMotionY);
                final int touchSlop = mTouchSlop;
                boolean xMoved = xDiff > touchSlop;
                boolean yMoved = yDiff > touchSlop;
                //若移动距离大于标准值
                if (xMoved || yMoved) {
                    if (xMoved) {
                        mTouchState = TOUCH_STATE_SCROLLING;
                        enableChildrenCache();
                    }
                    if (mAllowLongPress) {
                        mAllowLongPress = false;
                        final View currentScreen = getChildAt(mCurrentScreen);
                        //截断长按事件
                        currentScreen.cancelLongPress();
                    }
                }
                break;

            case MotionEvent.ACTION_DOWN:
                mLastMotionX = x;
                mLastMotionY = y;
                mAllowLongPress = true;
                mTouchState = mScroller.isFinished() ? TOUCH_STATE_REST : TOUCH_STATE_SCROLLING;
                break;
            case MotionEvent.ACTION_CANCEL:
            case MotionEvent.ACTION_UP:
                clearChildrenCache();
                mTouchState = TOUCH_STATE_REST;
                mAllowLongPress = false;
                break;
            default:
                break;
        }
        return mTouchState != TOUCH_STATE_REST;
    }

    void enableChildrenCache() {
        final int count = getChildCount();
        for (int i = 0; i < count; i++) {
            final View layout = (View) getChildAt(i);
            layout.setDrawingCacheEnabled(true);
        }
    }

    void clearChildrenCache() {
        final int count = getChildCount();
        for (int i = 0; i < count; i++) {
            final View layout = (View) getChildAt(i);
            layout.setDrawingCacheEnabled(false);
        }
    }


    /**
     * 点击/滑动时事件
     * @param event 动作事件
     *
     */
    @Override
    public boolean onTouchEvent(MotionEvent event) {

        if (mVelocityTracker == null) {
            mVelocityTracker = VelocityTracker.obtain();
        }
        mVelocityTracker.addMovement(event);

        final int action = event.getAction();
        //每次点击都会判断x坐标
        final float x = event.getX();
        switch (action) {
            case MotionEvent.ACTION_DOWN:
                Log.i(TAG,"onTouchEvent.ACTION_DOWN");
                if (!mScroller.isFinished()) {
                    mScroller.abortAnimation();
                }
                mLastMotionX = x;
                break;
            case MotionEvent.ACTION_MOVE:
                Log.i(TAG,"onTouchEvent.ACTION_MOVE");
                if (mTouchState == TOUCH_STATE_SCROLLING) {
                    final int deltaX = (int) (mLastMotionX - x);
                    mLastMotionX = x;
                    if (deltaX > 0) {
                        //滑动范围
                        if (deltaX + getScrollX() <= getChildAt(1).getWidth()) {
                            scrollBy(deltaX, 0);
                        }
                    } else if (deltaX < 0) {
                        if (getScrollX() > 0) {
                            scrollBy(deltaX, 0);
                        }
                    }

                }
                break;
            case MotionEvent.ACTION_CANCEL: //和Action_up流程相同
                if(event.equals(MotionEvent.ACTION_CANCEL)){
                    Log.i(TAG,"onTouchEvent.ACTION_CANCEL");
                }
            case MotionEvent.ACTION_UP:
                if(event.equals(MotionEvent.ACTION_UP)){
                    Log.i(TAG,"onTouchEvent.ACTION_UP");
                }
                if (mTouchState == TOUCH_STATE_SCROLLING) {
                    final VelocityTracker velocityTracker = mVelocityTracker;
                    velocityTracker.computeCurrentVelocity(1000);
                    int velocityX = (int) velocityTracker.getXVelocity();
                    Log.i(TAG,"velocityX:"+velocityX);
                    //速率距离,当力道距离大于阈值之后就
                    if (velocityX > SNAP_VELOCITY && mCurrentScreen == SCREEN_ACTION) {
                        //在二屏,并且滑动距离大于阈值,二屏转到一屏
                        Log.i(TAG,"snap to SCREEN_CONTENT");
                        snapToScreen(SCREEN_CONTENT);
                    } else if (velocityX < -SNAP_VELOCITY && mCurrentScreen == SCREEN_CONTENT) {
                        //在一屏,并且滑动距离大于阈值,一屏转到二屏
                        Log.i(TAG,"snap to SCREEN_ACTION");
                        snapToScreen(SCREEN_ACTION);
                    } else {
                        //滑动距离不大于阈值,要自动补全滑动
                        Log.i(TAG,"snap to OTHER");
                        snapToDestination();
                    }
                    if (mVelocityTracker != null) {
                        mVelocityTracker.recycle();
                        mVelocityTracker = null;
                    }
                }
                mTouchState = TOUCH_STATE_REST;
                break;
        }
        return true;
    }

    /**
     * 滑动到一屏还是而二屏
     * @param whichScreen 屏幕数量
     *
     */
    protected void snapToScreen(int whichScreen) {
        enableChildrenCache();
        whichScreen = Math.max(0, Math.min(whichScreen, getChildCount() - 1));
        boolean changingScreens = whichScreen != mCurrentScreen;
        mNextScreen = whichScreen;
        View focusedChild = getFocusedChild();
        if (focusedChild != null && changingScreens && focusedChild == getChildAt(mCurrentScreen)) {
            focusedChild.clearFocus();
        }
        final int newX = (whichScreen) * getChildAt(1).getWidth();
        final int delta = newX - getScrollX();
        mScroller.startScroll(getScrollX(), 0, delta, 0, Math.abs(delta) * 2);
        invalidate();
    }

    protected void snapToDestination() {
        if (getScrollX() == 0) {
            return;
        }
        final int screenWidth = getChildAt(1).getWidth();
        final int whichScreen = (screenWidth + getScrollX() + (screenWidth / 2)) / screenWidth;
        snapToScreen(whichScreen);
    }

    @Override
    public void computeScroll() {
        if (mScroller.computeScrollOffset()) {
            scrollTo(mScroller.getCurrX(), mScroller.getCurrY());
        } else if (mNextScreen != SCREEN_INVALID) {
            mCurrentScreen = Math.max(0, Math.min(mNextScreen, getChildCount() - 1));
            mNextScreen = SCREEN_INVALID;
            clearChildrenCache();
        }
    }

    @Override
    public void scrollTo(int x, int y) {
        super.scrollTo(x, y);
        postInvalidate();
    }

    @Override
    protected void dispatchDraw(Canvas canvas) {
        final int scrollX = getScrollX();
        System.out.println("dispatchDraw:" + scrollX);

        super.dispatchDraw(canvas);
        canvas.translate(scrollX, 0);
    }

    @Override
    public boolean dispatchUnhandledMove(View focused, int direction) {
        if (direction == View.FOCUS_LEFT) {
            if (getCurrentScreen() > 0) {
                snapToScreen(getCurrentScreen() - 1);
                return true;
            }
        } else if (direction == View.FOCUS_RIGHT) {
            if (getCurrentScreen() < getChildCount() - 1) {
                snapToScreen(getCurrentScreen() + 1);
                return true;
            }
        }
        return super.dispatchUnhandledMove(focused, direction);
    }

    public int getCurrentScreen() {
        return mCurrentScreen;
    }


    /*
    *   跳转到屏幕
    */
    public void showScreen(int screen) {
        if (screen != SCREEN_ACTION && screen != SCREEN_CONTENT) {
            return;
        }
        mCurrentScreen = screen;
        snapToScreen(mCurrentScreen);
    }

}
