package name.walnut.kanjian.app.resource.dto;

/**
 * 照片信息
 *
 * 包括照片拍摄时间和照片描述
 * @author walnut
 */
public class PhotoInfo {
    private long dateTimeOriginal;
    private String describe;

    public long getDateTimeOriginal() {
        return dateTimeOriginal;
    }

    public void setDateTimeOriginal(long dateTimeOriginal) {
        this.dateTimeOriginal = dateTimeOriginal;
    }

    public String getDescribe() {
        return describe;
    }

    public void setDescribe(String describe) {
        this.describe = describe;
    }
}
