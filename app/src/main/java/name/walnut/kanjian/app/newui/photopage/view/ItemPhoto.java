package name.walnut.kanjian.app.newui.photopage.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.AttributeSet;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

/**
 * Created by Administrator on 2015/9/30.
 */
public class ItemPhoto extends RelativeLayout{

    public int indexOfPhoto;

    public ItemPhoto(Context context) {
        super(context);
        initView(context);
    }

    public ItemPhoto(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView(context);
    }

    private void initView(Context context) {
        addView(new ImageView(context), new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
        LayoutParams textLayout = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        textLayout.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
        textLayout.addRule(RelativeLayout.CENTER_HORIZONTAL);
        addView(new TextView(context), textLayout);
    }

    public ImageView getPhoto(){
        return (ImageView) getChildAt(0);
    }

    public TextView getNameView(){
        return (TextView) getChildAt(1);
    }

    public void setImageBitmap(Bitmap bitmap, String name){
        getPhoto().setImageBitmap(bitmap);
        getNameView().setText(name);
    }

    public void setIndexOfPhoto(int indexOfPhoto){
        this.indexOfPhoto = indexOfPhoto;
    }

    public int getIndexOfPhoto(){
        return indexOfPhoto;
    }

    public void setPhotoOnTouchListener(OnTouchListener onTouchListener){
        setOnTouchListener(onTouchListener);
    }

    public void setPhotoOnClickListener(OnClickListener onClickListener){
        setOnClickListener(onClickListener);
    }

}
