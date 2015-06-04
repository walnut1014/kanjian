package name.walnut.kanjian.app.utils.image;

import android.graphics.Bitmap;

/**
 * 图片压缩参数
 */
public class CompressOptions {
    public int desWidth;    // 压缩后分辨率 宽度
    public int desHeight;   // 压缩后分辨率 宽度
    public int quality;    // 图片质量， 0 － 100
    public Bitmap.CompressFormat compressFormat;// 图片类型

    public CompressOptions() {
        this(Integer.MAX_VALUE, Integer.MAX_VALUE, 100, Bitmap.CompressFormat.PNG);
    }

    public CompressOptions(int desWidth, int desHeight, int quality, Bitmap.CompressFormat format) {
        this.desHeight = desHeight;
        this.desWidth = desWidth;
        this.quality = quality;
        this.compressFormat = format;
    }
}