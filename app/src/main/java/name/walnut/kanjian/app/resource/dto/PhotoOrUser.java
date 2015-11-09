package name.walnut.kanjian.app.resource.dto;

import name.walnut.kanjian.app.resource.support.ResourceResult;

/**
 * 照片列表页
 *
 * 包括用户头像和发出的照片,用isPhoto字段区分
 * @author walnut
 */
public class PhotoOrUser extends ResourceResult {

    /** 标识照片或者用户头像*/
    private boolean isPhoto;
    /** 现实图片路径
     *
     *  如果是照片则是图片缩略图路径
     *  如果是头像则是头像路径
     **/
    private String imagePath;
    /** 用户ID*/
    private long userId;
    /** 照片ID*/
    private long photoId;
    /** 照片路径*/
    private String photoPath;

    public boolean isPhoto() {
        return isPhoto;
    }

    public void setIsPhoto(boolean isPhoto) {
        this.isPhoto = isPhoto;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public long getPhotoId() {
        return photoId;
    }

    public void setPhotoId(long photoId) {
        this.photoId = photoId;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public String getPhotoPath() {
        return photoPath;
    }

    public void setPhotoPath(String photoPath) {
        this.photoPath = photoPath;
    }
}
