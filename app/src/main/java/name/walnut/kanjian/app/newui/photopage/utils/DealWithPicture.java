package name.walnut.kanjian.app.newui.photopage.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.util.Log;

/**
 * Created by Administrator on 2015/10/2.
 */
public class DealWithPicture {

    public static Bitmap getCircleThumbnail(Context context,int resId){
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeResource(context.getResources(), resId, options);
        options.inJustDecodeBounds = false;
        int outHeight = options.outHeight;
        int outWidth = options.outWidth;
        int outSide = outHeight < outWidth ? outHeight : outWidth;
        int be = (int) (outSide / (float) 100);
        if (be <= 0) {
            be = 1;
        }
        options.inSampleSize = be;
        Bitmap bitmap = BitmapFactory.decodeResource(context.getResources(), resId);
        return getCircleImage(bitmap);
    }

    public static Bitmap getCircleImage(Bitmap bitmap){
        Paint paint = new Paint();
        paint.setAntiAlias(true);
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();
        int d = width > height ? height : width;
        Bitmap target = Bitmap.createBitmap(d, d, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(target);
        canvas.drawCircle(d/2, d/2, d/2, paint);
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        canvas.drawBitmap(bitmap, 0, 0, paint);
        bitmap.recycle();
        return target;
    }

    public static Bitmap getSquareImage(Context context, int resId){
        Bitmap bitmap = BitmapFactory.decodeResource(context.getResources(), resId);
        //BitmapFactory.decodeResource(context.getResources(), resId, BitmapFactory.Options.);
        return getSquareImage(bitmap);
    }

    public static Bitmap getSquareImage(Bitmap bitmap){
        int w = bitmap.getWidth();
        int h = bitmap.getHeight();
        if(w>h){
            bitmap = Bitmap.createBitmap(bitmap, (w-h)/2, 0, h, h);
        }else{
            bitmap = Bitmap.createBitmap(bitmap, 0, (h-w)/2, w, w);
        }
        return bitmap;
    }

    public static Bitmap getSquareThumbnail(Context context, int resId){
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeResource(context.getResources(), resId, options);
        options.inJustDecodeBounds = false;
        int outHeight = options.outHeight;
        int outWidth = options.outWidth;
        int outSide = outHeight < outWidth ? outHeight : outWidth;
        Log.e("test","side"+outSide);
        int be = (int) (outSide / (float) 200);
        if (be <= 0) {
            be = 1;
        }
        options.outWidth = outSide;
        options.outHeight = outSide;
        options.inSampleSize = be;
        return BitmapFactory.decodeResource(context.getResources(), resId, options);
    }
}
