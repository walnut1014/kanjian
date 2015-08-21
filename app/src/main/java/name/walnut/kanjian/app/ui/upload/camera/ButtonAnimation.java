
package name.walnut.kanjian.app.ui.upload.camera;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.RelativeLayout;

import java.lang.ref.WeakReference;
import java.util.Timer;
import java.util.TimerTask;

import name.walnut.kanjian.app.R;

/**
 * Created by GongGaohong on 15-8-19.
 * E-main Sagittarius_Ggh@126.com
 */
public class ButtonAnimation implements View.OnTouchListener{

    private static final String TAG = "ButtonAnimation";

    private UiHandler handler;
    private ViewHolder viewHolder;

    private OnClickActionUpListener onClickActionUpListener;

    private Timer progressTimer;

    private float scaleRate = 1f;
    private int progressCount = 0;

    private static final int UI_SCALE_ACTION_DOWN = 10000;
    private static final int UI_SCALE_ACTION_UP =10001;

    private static final int BUTTON_SCALE_ACTION_DOWN = 20000;
    private static final int BUTTON_SCALE_ACTION_UP = 20001;

    public ButtonAnimation(Context context,RelativeLayout view){
        handler = new UiHandler(context);
        viewHolder = new ViewHolder();
        viewHolder.layout = view;
        viewHolder.blackMasking = view.findViewById(R.id.blackMasking);
        viewHolder.redMasking = view.findViewById(R.id.redMasking);
        viewHolder.writeCircle = (RoundProgressBar)view.findViewById(R.id.writeCircle);
        viewHolder.writeMasking = view.findViewById(R.id.writeMasking);
    }

    public ButtonAnimation(Context context,ViewHolder viewHolder){
        handler = new UiHandler(context);
        this.viewHolder = viewHolder;
    }

    public ButtonAnimation (Context context, View view, OnClickActionUpListener onClickActionUpListener){
        handler = new UiHandler(context);
        this.onClickActionUpListener = onClickActionUpListener;
    }

    public interface OnClickActionUpListener{
        public void onActionUp();
    }


    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {
        switch(view.getId()){
            case R.id.layout:
                switch(motionEvent.getAction()){
                    case MotionEvent.ACTION_DOWN:
                        viewHolder.blackMasking.setVisibility(View.VISIBLE);
                        viewHolder.redMasking.setVisibility(View.VISIBLE);
                        handler.sendEmptyMessage(UI_SCALE_ACTION_DOWN);
                        progressTimer = new Timer();
                        progressTimer.schedule(new TimerTask() {
                            @Override
                            public void run() {
                                if(progressCount <= 100){
                                    progressCount += 1;
                                    viewHolder.writeCircle.setProgress(progressCount);
                                }else{
                                    progressTimer.cancel();
                                }
                            }
                        },30,30);
                        break;
                    case MotionEvent.ACTION_UP:
                        progressTimer.cancel();
                        handler.sendEmptyMessage(UI_SCALE_ACTION_UP);
                        break;
                    default:
                        break;
                }
                return true;
            case R.id.camera_button_personal:
            case R.id.camera_button_photo:
            case R.id.camera_button_message:
                switch(motionEvent.getAction()){
                    case MotionEvent.ACTION_DOWN:
                        handler.sendMessage(handler.obtainMessage(BUTTON_SCALE_ACTION_DOWN, view));
                        break;
                    case MotionEvent.ACTION_UP:
                        handler.sendMessage(handler.obtainMessage(BUTTON_SCALE_ACTION_UP, view));
                        onClickActionUpListener.onActionUp();
                        break;
                    default:
                        break;
                }
                return true;
            default:
                return true;
        }
    }

    private class UiHandler extends Handler {
        private WeakReference<Context> context;

        public UiHandler(Context context){
            this.context = new WeakReference<Context>(context);
        }

        @Override
        public void handleMessage(Message message){
            Context context = this.context.get();
            if(context != null){
                View view;
                switch (message.what){
                    case UI_SCALE_ACTION_DOWN:
                        if(scaleRate < 1.3f){
                            scaleRate += 0.01f;
                            viewHolder.layout.setScaleX(scaleRate);
                            viewHolder.layout.setScaleY(scaleRate);
                            viewHolder.redMasking.setScaleX(1 + 7 * (scaleRate - 1));
                            viewHolder.redMasking.setScaleY(1 + 7 * (scaleRate - 1));
                            handler.sendEmptyMessageDelayed(UI_SCALE_ACTION_DOWN, 33);
                        }
                        break;
                    case UI_SCALE_ACTION_UP:
                        handler.removeMessages(UI_SCALE_ACTION_DOWN);
                        scaleRate = 1.0f;
                        viewHolder.layout.setScaleX(scaleRate);
                        viewHolder.layout.setScaleY(scaleRate);
                        viewHolder.redMasking.setVisibility(View.GONE);
                        viewHolder.blackMasking.setVisibility(View.GONE);
                        progressCount = 0;
                        viewHolder.writeCircle.setProgress(progressCount);
                        break;
                    case BUTTON_SCALE_ACTION_DOWN:
                        view = (View)message.obj;
                        if(scaleRate < 1.3f){
                            scaleRate += 0.015;
                            view.setScaleX(scaleRate);
                            view.setScaleY(scaleRate);
                            handler.sendMessageDelayed(handler.obtainMessage(BUTTON_SCALE_ACTION_DOWN, view), 10);
                        }
                        break;
                    case BUTTON_SCALE_ACTION_UP:
                        view = (View)message.obj;
                        handler.removeMessages(BUTTON_SCALE_ACTION_DOWN);
                        scaleRate = 1.0f;
                        view.setScaleX(scaleRate);
                        view.setScaleY(scaleRate);
                        break;
                    default:
                        break;
                }
            }
        }

    }

    /**
     *
     */
    public class ViewHolder {
        /**
         *
         */
        public View layout;
        public RoundProgressBar writeCircle;
        public View writeMasking;
        public View blackMasking;
        public View redMasking;
    }
}
