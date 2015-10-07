package name.walnut.kanjian.app.newui.photopage.view;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import java.lang.ref.WeakReference;

import name.walnut.kanjian.app.R;

/**
 * Created by Administrator on 2015/9/28.
 */
public class ListLayout extends RelativeLayout implements View.OnTouchListener {

    private static final String TAG = "ListLayout";

    private Context context;

    private UiHandler handler;

    private View downHeader;
    private View upHeader;
    private LayoutParams downLayout;
    private LayoutParams upLayout;

    private boolean loadOnce = true;
    private int layoutHeight;
    private int layoutWidth;
    private int headerHeight;

    private int photoHeight;

    private LinearLayout listGroup;
    private LayoutParams listLayout;
    private ListItem tempView;

    private OnListAdapter onListAdapter;
    private OnClickListener onClickListener;

    private OnRefreshingAndLoadingListener onRefreshingAndLoadingListener;

    //去除第一行后一页的行数
    private int numOfRowsPage;
    //总的照片数量
    private int numOfPhotos;
    //总的行数
    private int numOfRows;
    //总的页数
    private int numOfPages;
    private int indexOfPage;
    /*private int indexOfPhotoRow;
    private int indexOfPhoto;/**/

    private int indexStartRow;
    private int indexEndRow;
    private int indexStartRowPage;

    private float yDown;
    private float yMove;
    private float distance;
    private int touchSlop;

    private int state;
    public static final int PULL_DOWN_TO_REFRESH = 0;
    public static final int PULL_UP_TO_LOAD = 1;
    public static final int PULL_LIST_TO_CHANGE = 2;
    public static final int PULL_DOWN_REFRESHING = 3;
    public static final int PULL_UP_LOADING = 4;

    public ListLayout(Context context) {
        super(context);
        initView(context);
    }

    public ListLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView(context);
    }

    private void initView(Context context) {
        this.context = context;
        handler = new UiHandler(context);
        setOnTouchListener(this);
        downHeader = View.inflate(context, R.layout.pull_header, null);
        addView(downHeader);
        upHeader = View.inflate(context, R.layout.pull_header, null);
        addView(upHeader);
        tempView = new ListItem(context);
        listGroup = new LinearLayout(context);
        listGroup.setOrientation(LinearLayout.VERTICAL);
        addView(listGroup);
        touchSlop = ViewConfiguration.get(context).getScaledTouchSlop();
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom){
        super.onLayout(changed, left, top, right, bottom);
        if(loadOnce && changed){
            layoutHeight = getHeight();
            layoutWidth = getWidth();
            headerHeight = downHeader.getHeight();
            photoHeight = layoutWidth / 4;
            downLayout = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
            downLayout.topMargin = - headerHeight;
            downHeader.setLayoutParams(downLayout);
            upLayout = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
            upLayout.topMargin = headerHeight + layoutHeight;
            upHeader.setLayoutParams(upLayout);
            listLayout = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
            listLayout.topMargin = 0;
            listGroup.setLayoutParams(listLayout);
            initList();
            loadOnce = false;
        }
    }

    private void initList() {
        //获取一屏的行数
        numOfRowsPage = getDivideUpData(layoutHeight, photoHeight);
        int intNum = numOfRowsPage;
        //尾索引，指向可见列表中最上面一个Item
        indexEndRow = indexStartRowPage = numOfRowsPage - 1;
        while (intNum > 0){
            ListItem view = new ListItem(getContext());
            view.setPhotoOnTouchListener(this);
            view.setPhotoOnClickListener(onClickListener);
            listGroup.addView(view);
            //头索引，指向可见列表中最下面一个Item
            indexStartRow = --intNum ;
            //设置行索引并且设置背景
            view.setIndexOfRow(indexStartRow );
        }
        notifyDataSetChanged();
    }

    private int getDivideUpData(int divided, int divider){
        double doubleNum = (double)divided / (double) divider;
        int intNum = divided / divider;
        return doubleNum > intNum ? ++ intNum : intNum;
    }

    private void notifyDataSetChanged() {
        if(onListAdapter != null){
            //numOfPhotos = onListAdapter.onLoadCount()+onListAdapter.onRefreshCount();
            //照片总数
            numOfPhotos = onListAdapter.getCount();
            //照片总行数
            int numOfRowsPhoto = getDivideUpData(numOfPhotos, 4);
            //照片总页数
            numOfPages = getDivideUpData(numOfRowsPhoto, numOfRowsPage / 2 * 2);
            //背景总行数
            if(numOfRowsPage % 2 == 0){
                numOfRows = numOfPages * numOfRowsPage;
            }else{
                numOfRows = numOfPages * (numOfRowsPage - 1) + 1;
            }
            indexOfPage = numOfPages - 1;
        }else{
            numOfPhotos = 0;
            numOfRows = numOfRowsPage;
            numOfPages = 1;
            indexOfPage = 0;
        }
        for(int i = indexStartRow; i <= indexEndRow; i++){
            ListItem listItem = (ListItem) listGroup.getChildAt(indexEndRow - i);
            getItem(listItem);
        }
    }

    private void getItem(ListItem listItem){
        if(onListAdapter != null){
            for(int j = 4; j > 0;){
                ItemPhoto itemPhoto = listItem.getItemPhoto(--j);
                int index = itemPhoto.getIndexOfPhoto();
                int first = getFirstVisibleItemPhotoPosition();
                if(index < numOfPhotos){
                    onListAdapter.setPhoto(itemPhoto);
                    if(index == first){
                        if(numOfPhotos - index == 2){
                            itemPhoto = onListAdapter.setTitlePhoto(0);
                            itemPhoto.setIndexOfPhoto(++index);
                            onListAdapter.setPhoto(itemPhoto);
                            itemPhoto = onListAdapter.setTitlePhoto(1);
                            itemPhoto.setImageBitmap(null, "");
                        }else if(numOfPhotos - index >= 3){
                            itemPhoto = onListAdapter.setTitlePhoto(0);
                            itemPhoto.setIndexOfPhoto(++index);
                            onListAdapter.setPhoto(itemPhoto);
                            itemPhoto = onListAdapter.setTitlePhoto(1);
                            itemPhoto.setIndexOfPhoto(++index);
                            onListAdapter.setPhoto(itemPhoto);
                        }
                    }
                }else{
                    if(index < first){
                        itemPhoto = onListAdapter.setTitlePhoto(0);
                        itemPhoto.setImageBitmap(null, "");
                        itemPhoto = onListAdapter.setTitlePhoto(1);
                        itemPhoto.setImageBitmap(null, "");
                    }
                    break;
                }
            }
        }
    }

    public void setOnListAdapter(OnListAdapter onListAdapter){
        this.onListAdapter = onListAdapter;
    }

    public interface OnListAdapter{
        int getCount();
        void setPhoto(ItemPhoto itemPhoto);
        ItemPhoto setTitlePhoto(int indexOfPhoto);
    }

    public int getFirstVisibleItemPhotoPosition(){
        return indexStartRowPage * 4 + 3;
    }

    public void setPhotoOnClickListener(OnClickListener onClickListener){
        this.onClickListener = onClickListener;
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                yMove = yDown = event.getRawY();
                indexStartRowPage = indexEndRow;
                break;
            case MotionEvent.ACTION_MOVE:
                float rawY = event.getRawY();
                int offset = (int) (rawY - yMove);
                yMove = rawY;
                distance = yMove - yDown;
                moveItem(offset, (int) distance);
                break;
            case MotionEvent.ACTION_UP:
                if(state == PULL_DOWN_TO_REFRESH){
                    if(distance > headerHeight){
                        handler.sendMessage(handler.obtainMessage(PULL_DOWN_BACK_REFRESHING, 0, (int) distance));
                    }else{
                        handler.sendMessage(handler.obtainMessage(PULL_DOWN_BACK_REFRESHED, 0, (int) distance));
                    }
                }else if(state == PULL_UP_TO_LOAD){
                    if(distance < - headerHeight - 7 * photoHeight + layoutHeight){
                        handler.sendMessage(handler.obtainMessage(PULL_UP_BACK_LOADING, 0, (int) distance));
                    }else{
                        handler.sendMessage(handler.obtainMessage(PULL_UP_BACK_LOADED,0, (int) distance));
                    }
                }else if(state == PULL_LIST_TO_CHANGE){
                    if(distance > 2 * photoHeight){
                        indexStartRowPage = indexStartRowPage + 6;
                        handler.sendMessage(handler.obtainMessage(PULL_CHANGE_PAGE, 0, indexStartRowPage));
                    }else if(distance < - 2 * photoHeight){
                        indexStartRowPage = indexStartRowPage - 6;
                        handler.sendMessage(handler.obtainMessage(PULL_CHANGE_PAGE, 0, indexStartRowPage));
                    }else{
                        handler.sendMessage(handler.obtainMessage(PULL_CHANGE_PAGE,0,indexStartRowPage));
                    }
                }
                if(Math.abs(distance) < touchSlop){
                    v.performClick();
                }
                break;
            default:
                break;
        }
        return true;
    }

    private void moveToLoad(float distance) {
        listLayout.topMargin = (int) distance;
        listGroup.setLayoutParams(listLayout);
        downLayout.topMargin = (int) (numOfRowsPage * photoHeight + distance);
        downHeader.setLayoutParams(downLayout);
    }

    private void moveToRefresh(float distance) {
        listLayout.topMargin = (int) distance;
        listGroup.setLayoutParams(listLayout);
        upLayout.topMargin = (int) (- headerHeight + distance);
        upHeader.setLayoutParams(upLayout);
    }

    private void moveItem(int offset,int distance) {
        int childCount = listGroup.getChildCount();
        listLayout.topMargin = listLayout.topMargin + offset;
        if(distance < 0 && indexStartRow <= 0){
            moveToLoad(distance);
            state = PULL_UP_TO_LOAD;
        }else if(offset < 0 && listLayout.topMargin < -photoHeight){
            tempView = (ListItem) listGroup.getChildAt(0);
            listGroup.removeView(tempView);
            listLayout.topMargin = listLayout.topMargin + photoHeight;
            listGroup.setLayoutParams(listLayout);
            tempView.setIndexOfRow(--indexStartRow);
            indexEndRow--;
            getItem(tempView);
            listGroup.addView(tempView);
            state = PULL_LIST_TO_CHANGE;
        }else if(distance > 0 && indexEndRow >= numOfRows - 1){
            moveToRefresh(distance);
            state = PULL_DOWN_TO_REFRESH;
        }else if(offset > 0 && listLayout.topMargin > 0){
            tempView = (ListItem) listGroup.getChildAt(childCount -1);
            listGroup.removeView(tempView);
            listLayout.topMargin = listLayout.topMargin - photoHeight;
            listGroup.setLayoutParams(listLayout);
            tempView.setIndexOfRow(++indexEndRow);
            indexStartRow++;
            getItem(tempView);
            listGroup.addView(tempView, 0);
            state = PULL_LIST_TO_CHANGE;
        }else{
            listGroup.setLayoutParams(listLayout);
            state = PULL_LIST_TO_CHANGE;
        }
    }

    private static final int UI_TIMER = 10000;
    private static final int PULL_DOWN_BACK_REFRESHING = 10001;
    private static final int PULL_DOWN_BACK_REFRESHED = 10002;
    private static final int PULL_CHANGE_PAGE = 10003;
    private static final int REFRESHING_CHANGE_COLOR = 10004;
    private static final int PULL_UP_BACK_LOADED = 10005;
    private static final int PULL_UP_BACK_LOADING = 10006;

    private class UiHandler extends Handler {

        private int scrollLength = 30;

        private WeakReference<Context> context;

        public UiHandler(Context context){
            this.context = new WeakReference<Context>(context);
        }

        @Override
        public void handleMessage(Message message){
            if(context.get() != null){
                int distance = 0;
                int indexOfFirstDetail;
                switch (message.what){
                    case  UI_TIMER:
                        sendMessageDelayed(obtainMessage(message.arg2, 0, message.arg1), 10);
                        break;
                    case PULL_DOWN_BACK_REFRESHED:
                        distance = message.arg2;
                        removeMessages(PULL_DOWN_BACK_REFRESHED);
                        if(distance < scrollLength && distance > - scrollLength){
                            distance = 0;
                        }else{
                            distance = distance - scrollLength;
                            sendMessage(obtainMessage(UI_TIMER,distance,PULL_DOWN_BACK_REFRESHED));
                        }
                        moveToRefresh(distance);
                        break;
                    case PULL_DOWN_BACK_REFRESHING:
                        distance = message.arg2;
                        removeMessages(PULL_DOWN_BACK_REFRESHING);
                        if(distance < headerHeight){
                            distance = headerHeight;
                            //sendMessage(obtainMessage(REFRESHING_CHANGE_COLOR, 0, 0xffc8c8c8));
                            new ChangeColorTask(upHeader).execute();
                            if(onRefreshingAndLoadingListener != null){
                                if(onRefreshingAndLoadingListener.onRefreshing()){
                                    if(onListAdapter != null){
                                        int count = onListAdapter.getCount();
                                        if(count < numOfPhotos){

                                        }else{
                                            notifyDataSetChanged();
                                        }
                                    }
                                }else{

                                }
                                //removeMessages(REFRESHING_CHANGE_COLOR);
                                //int top = listGroup.getTop();
                                //sendMessage(obtainMessage(PULL_DOWN_BACK_REFRESHED,0,top));
                            }
                        }else{
                            distance = distance - scrollLength;
                            sendMessage(obtainMessage(UI_TIMER, distance, PULL_DOWN_BACK_REFRESHING));
                        }
                        moveToRefresh(distance);
                        break;
                    case PULL_UP_BACK_LOADED:
                        distance = message.arg2;
                        removeMessages(PULL_UP_BACK_LOADED);
                        if(distance < scrollLength && distance > - scrollLength){
                            distance = 0;
                        }else{
                            distance = distance + scrollLength;
                            sendMessage(obtainMessage(UI_TIMER,distance,PULL_UP_BACK_LOADED));
                        }
                        moveToLoad(distance);
                        break;
                    case PULL_UP_BACK_LOADING:
                        distance = message.arg2;
                        removeMessages(PULL_UP_BACK_LOADING);
                        if(distance > - headerHeight - photoHeight * 7 + layoutHeight){
                            distance = - headerHeight - photoHeight * 7 + layoutHeight;
                            //sendMessage(obtainMessage(REFRESHING_CHANGE_COLOR, 0, 0xffc8c8c8));
                            new ChangeColorTask(downHeader).execute();
                            if(onRefreshingAndLoadingListener != null){
                                if(onRefreshingAndLoadingListener.onLoading()){

                                }else{

                                }
                                //removeMessages(REFRESHING_CHANGE_COLOR);
                                int top = listGroup.getTop();
                                //sendMessage(obtainMessage(PULL_UP_BACK_LOADED,0,top));
                            }
                        }else{
                            distance = distance + scrollLength;
                            sendMessage(obtainMessage(UI_TIMER,distance,PULL_UP_BACK_LOADING));
                        }
                        moveToLoad(distance);
                        break;
                    case PULL_CHANGE_PAGE:
                        indexOfFirstDetail = message.arg2;
                        removeMessages(PULL_CHANGE_PAGE);
                        if(indexEndRow > indexOfFirstDetail){
                            sendMessage(obtainMessage(UI_TIMER,indexOfFirstDetail, PULL_CHANGE_PAGE));
                            moveItem(-scrollLength,0);
                        }else if(indexEndRow < indexOfFirstDetail){
                            sendMessage(obtainMessage(UI_TIMER,indexOfFirstDetail, PULL_CHANGE_PAGE));
                            moveItem(scrollLength,0);
                        }else {
                            int top = listGroup.getTop();
                            if(top < - scrollLength){
                                moveItem(scrollLength,0);
                                sendMessage(obtainMessage(UI_TIMER,indexOfFirstDetail, PULL_CHANGE_PAGE));
                            }else
                                moveItem(-listGroup.getTop(),0);
                        }
                        break;
                    case REFRESHING_CHANGE_COLOR:
                        int color = message.arg2;
                        removeMessages(REFRESHING_CHANGE_COLOR);
                        upHeader.setBackgroundColor(color);
                        downHeader.setBackgroundColor(color);
                        if((color+=0x010505)  < 0xffffffff){
                            sendMessage(obtainMessage(UI_TIMER,color,REFRESHING_CHANGE_COLOR));}
                        else{
                            int top = listGroup.getTop();
                            if(top > 0 ){
                                sendMessage(obtainMessage(PULL_DOWN_BACK_REFRESHED,0,top));
                            }else{
                                sendMessage(obtainMessage(PULL_UP_BACK_LOADED,0,top));
                            }
                        }
                        break;
                    default:
                        break;
                }
            }
        }
    }

    private class ChangeColorTask extends AsyncTask<Void, Integer, Void>{

        View view;

        public ChangeColorTask(View view){
            this.view = view;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            int color = 0xffc8c8c8;
            while(color < 0xffffffff){
                publishProgress(color+=0x010505);
                sleep(10);
            }
            return null;
        }

        private void sleep(long time){
            try {
                Thread.sleep(time);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        @Override
        protected void onProgressUpdate(Integer... params){
            view.setBackgroundColor(params[0]);
        }

        @Override
        protected void onPostExecute(Void params){
            int top = listGroup.getTop();
            if(top > 0 ){
                handler.sendMessage(handler.obtainMessage(PULL_DOWN_BACK_REFRESHED,0,top));
            }else{
                handler.sendMessage(handler.obtainMessage(PULL_UP_BACK_LOADED,0,top));
            }
        }
    }

    public void setOnRefreshingAndLoadingListener(OnRefreshingAndLoadingListener onRefreshingAndLoadingListener){
        this.onRefreshingAndLoadingListener = onRefreshingAndLoadingListener;
    }

    public interface OnRefreshingAndLoadingListener{
        boolean onRefreshing();
        boolean onLoading();
    }

    public boolean nextPage(){
        if(indexEndRow < numOfRows){
            handler.sendMessage(handler.obtainMessage(PULL_CHANGE_PAGE, 0, indexStartRow + 6));
            return true;
        }else
            return false;
    }

    public boolean lastPage(){
        if(indexStartRow > 0){
            handler.sendMessage(handler.obtainMessage(PULL_CHANGE_PAGE, 0, indexStartRow - 6));
            return true;
        }else
            return false;
    }
}
