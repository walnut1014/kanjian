package name.walnut.kanjian.app.utils.image;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Environment;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * 图片工具类
 */
public class BitmapUtils {

    /**
     *
     * @param path 图片原路径
     * @param scaleName 新图片文件名
     * @param options 压缩参数，损耗、分辨率、图片格式
     * @return
     */
    public static String decodeFile(Context context, String path, String scaleName, final CompressOptions options) {
        String imagePath = null;
        Bitmap scaledBitmap = null;

        try {
            // Part 1: Decode image
            Bitmap unscaledBitmap = ScalingUtils.decodeFile(
                    path,
                    options.desWidth,
                    options.desHeight,
                    ScalingUtils.ScalingLogic.FIT);

            if (!(unscaledBitmap.getWidth() <= options.desWidth &&
                    unscaledBitmap.getHeight() <= options.desHeight)) {

                // Part 2: Scale image
                scaledBitmap = ScalingUtils.createScaledBitmap(
                        unscaledBitmap,
                        options.desWidth,
                        options.desHeight,
                        ScalingUtils.ScalingLogic.FIT);
            } else {
                unscaledBitmap.recycle();
                return path;
            }

            imagePath = saveBitmapToSdCard(context, scaledBitmap, scaleName, options);

            scaledBitmap.recycle();

        } catch (Throwable ignored) {
        }

        return imagePath;

    }

    // Store to tmp file
    private static String saveBitmapToSdCard(Context context, Bitmap bitmap, String fileName, CompressOptions options) {

        FileOutputStream fos = null;
        try {
            File imgFile = new File(context.getCacheDir(), fileName);
            fos = new FileOutputStream(imgFile);
            bitmap.compress(options.compressFormat, options.quality, fos);
            fos.flush();
            fos.close();

            return imgFile.getAbsolutePath();

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (fos != null) {
                try {
                    fos.close();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
        }
        return null;
    }

}
