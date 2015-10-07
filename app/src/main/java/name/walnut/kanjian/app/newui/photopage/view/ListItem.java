package name.walnut.kanjian.app.newui.photopage.view;

import android.content.Context;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.LinearLayout;

import name.walnut.kanjian.app.R;

/**
 * Created by Administrator on 2015/9/30.
 */
public class ListItem extends LinearLayout {

    private int indexOfRow;

    public ListItem(Context context) {
        super(context);
        initView(context);
    }

    public ListItem(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView(context);
    }

    private void initView(Context context) {
        setOrientation(HORIZONTAL);
        LayoutParams layoutParams = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, getWindowWidth() / 4);
        setLayoutParams(layoutParams);
        for(int i = 0; i < 4; i++){
            ItemPhoto image = new ItemPhoto(context);
            LayoutParams layout = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
            layout.weight = 1;
            image.setLayoutParams(layout);
            addView(image);
        }
    }

    public void setIndexOfRow(int indexOfRow){
        this.indexOfRow = indexOfRow;
        for(int i = 0; i < 4 ; i ++){
            ItemPhoto child = (ItemPhoto) getChildAt(3 - i);
            child.setIndexOfPhoto(indexOfRow * 4 + i);
            child.getPhoto().setImageDrawable(null);
            child.getNameView().setText("");
            if(indexOfRow % 2 == 0) {
                if(i % 2 == 0){
                    child.setBackgroundResource(R.color.colorGray);
                }else{
                    child.setBackgroundResource(R.color.colorBlack);
                }
            }else{
                if(i % 2 == 0){
                    child.setBackgroundResource(R.color.colorBlack);
                }else{
                    child.setBackgroundResource(R.color.colorGray);
                }
            }
        }
    }

    public void setPhotoOnTouchListener(OnTouchListener onTouchListener){
        for(int i = 0; i < 4; i++){
            getItemPhoto(i).setPhotoOnTouchListener(onTouchListener);
        }
    }

    public void setPhotoOnClickListener(OnClickListener onClickListener){
        for(int i = 0; i < 4; i++){
            getItemPhoto(i).setPhotoOnClickListener(onClickListener);
        }
    }

    public int getIndexOfRow(){
        return indexOfRow;
    }

    public ItemPhoto getItemPhoto(int index){
        if(index < 4 && index > -1){
            return (ItemPhoto) getChildAt(index);
        }else
            return null;
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
}
