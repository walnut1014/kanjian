package name.walnut.kanjian.app.newui.photopage.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.WindowManager;
import android.widget.LinearLayout;

import name.walnut.kanjian.app.R;

/**
 * Created by Administrator on 2015/9/28.
 */
public class PullLayout extends LinearLayout implements ListLayout.OnListAdapter , ListLayout.OnRefreshingAndLoadingListener{

    private static final String TAG = "PullLayout";

    private ListItem titleItem;

    private ListLayout listLayout;

    private OnPhotoAdapter onPhotoAdapter;

    public PullLayout(Context context) {
        super(context);
    }

    public PullLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView(context);
    }

    private void initView(final Context context) {
        setOrientation(VERTICAL);
        titleItem = new ListItem(context);
        titleItem.setIndexOfRow(1);
        addView(titleItem);
        ItemPhoto back = titleItem.getItemPhoto(0);
        back.getPhoto().setBackgroundResource(R.mipmap.bt_back_home);
        back.setIndexOfPhoto(-1);
        ItemPhoto msgNum = titleItem.getItemPhoto(1);
        msgNum.getPhoto().setBackgroundResource(R.mipmap.msg_num_more);
        msgNum.setIndexOfPhoto(-2);
        listLayout = new ListLayout(context);
        LayoutParams layoutParams = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
        listLayout.setLayoutParams(layoutParams);
        addView(listLayout);
        listLayout.setOnListAdapter(this);
        listLayout.setOnRefreshingAndLoadingListener(this);
    }


    private DisplayMetrics getMetrics(){
        DisplayMetrics dm = new DisplayMetrics();
        WindowManager wm = (WindowManager) getContext().getSystemService(Context.WINDOW_SERVICE);
        wm.getDefaultDisplay().getMetrics(dm);
        return dm;
    }

    private int getWindowWidth(){
        return getMetrics().widthPixels;
    }

    private int getWindowHeight(){
        return getMetrics().heightPixels;
    }

    @Override
    public boolean onRefreshing() {
        if(onPhotoAdapter != null){
            return onPhotoAdapter.refresh();
        }else
            return false;
    }

    @Override
    public boolean onLoading() {

        return true;
    }

    private class AnimationTask extends AsyncTask<Bitmap, Integer, Bitmap> {

        private ItemPhoto itemPhoto;

        public AnimationTask(ItemPhoto itemPhoto){
            this.itemPhoto = itemPhoto;
        }

        @Override
        protected Bitmap doInBackground(Bitmap... params) {
            int color = 0xffc8c8c8;
            while(color < 0xffffffff){
                color += 0x010505;
                publishProgress(color);
                sleep();
            }
            return params[0];
        }

        private void sleep(){
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        @Override
        protected void onProgressUpdate(Integer... params){
            itemPhoto.getPhoto().setBackgroundColor(params[0]);
        }

        @Override
        protected void onPostExecute(Bitmap bitmap){
            itemPhoto.getPhoto().setBackgroundColor(0x00000000);
            itemPhoto.setImageBitmap(bitmap, "Lily");
        }
    }

    @Override
    public int getCount() {
        if(onPhotoAdapter != null)
            return onPhotoAdapter.onLoadCount() + onPhotoAdapter.onRefreshCount();
        else
            return 0;
    }

    @Override
    public void setPhoto(ItemPhoto itemPhoto) {
        if(onPhotoAdapter != null){
            int index = itemPhoto.getIndexOfPhoto();
            if(index < onPhotoAdapter.onLoadCount()){
                itemPhoto.setImageBitmap(onPhotoAdapter.onLoading(index), "Andy");
            }else if(index < onPhotoAdapter.onRefreshCount() + onPhotoAdapter.onLoadCount()) {
                new AnimationTask(itemPhoto).execute(onPhotoAdapter.onRefreshing(index));
            }
        }
    }

    @Override
    public ItemPhoto setTitlePhoto(int index) {
        if(index == 0){
            return titleItem.getItemPhoto(3);
        }else if(index == 1){
            return titleItem.getItemPhoto(2);
        }else
            return null;
    }

    public void setPhotoOnClickListener(OnClickListener onClickListener){
        titleItem.setPhotoOnClickListener(onClickListener);
        listLayout.setPhotoOnClickListener(onClickListener);
    }

    public void setOnPhotoAdapter(OnPhotoAdapter onPhotoAdapter){
        this.onPhotoAdapter = onPhotoAdapter;
    }

    public interface OnPhotoAdapter{
        int onLoadCount();
        Bitmap onLoading(int position);
        int onRefreshCount();
        Bitmap onRefreshing(int position);
        boolean refresh();
    }
}
