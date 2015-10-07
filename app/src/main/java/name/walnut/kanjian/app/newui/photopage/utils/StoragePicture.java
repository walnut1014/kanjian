package name.walnut.kanjian.app.newui.photopage.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;

/**
 * Created by Administrator on 2015/10/6.
 */
public class StoragePicture {

    public static void writePicture(Context context,Bitmap bitmap,String name){
        try {
            OutputStream outputStream = context.openFileOutput(name, Context.MODE_PRIVATE);
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, outputStream);
            outputStream.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static Bitmap readPicture(Context context, String name){
        try {
            InputStream inputStream = context.openFileInput(name);
            return BitmapFactory.decodeStream(inputStream);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return  null;
    }

    public static String[] getFileList(Context context){
        return context.fileList();
    }
}
