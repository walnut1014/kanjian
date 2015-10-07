package name.walnut.kanjian.app.newui.photopage;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import name.walnut.kanjian.app.R;
import name.walnut.kanjian.app.newui.camera.CameraActivity;
import name.walnut.kanjian.app.newui.photopage.utils.DealWithPicture;
import name.walnut.kanjian.app.newui.photopage.utils.StoragePicture;
import name.walnut.kanjian.app.newui.photopage.view.ItemPhoto;
import name.walnut.kanjian.app.newui.photopage.view.PullLayout;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class PhotoPageActivity extends Activity{
    List<Integer> photoList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo_page);

        photoList = new ArrayList<>();
        addList();
        PullLayout pullLayout = (PullLayout) findViewById(R.id.pull_layout);
        pullLayout.setPhotoOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ItemPhoto itemPhoto = (ItemPhoto) v;
                Log.e("Activity","index"+itemPhoto.getIndexOfPhoto());
                switch (itemPhoto.getIndexOfPhoto()) {
                    case -1:
                        finish();
                        break;
                    case -2:
                        break;
                    default:
                        Intent intent = new Intent(PhotoPageActivity.this, CameraActivity.class);
                        startActivityForResult(intent, itemPhoto.getIndexOfPhoto());
                        break;
                }
            }
        });
        pullLayout.setOnPhotoAdapter(new PullLayout.OnPhotoAdapter() {
            @Override
            public int onLoadCount() {
                return StoragePicture.getFileList(PhotoPageActivity.this).length;
            }

            @Override
            public Bitmap onLoading(int position) {
                return StoragePicture.readPicture(PhotoPageActivity.this, StoragePicture.getFileList(PhotoPageActivity.this)[position]);
            }

            @Override
            public int onRefreshCount() {
                return photoList.size();
            }

            ;

            @Override
            public Bitmap onRefreshing(int position) {
                if (photoList.size() > 0) {
                    int resId = photoList.get(0);
                    photoList.remove(new Integer(resId));
                    Bitmap bitmap = DealWithPicture.getCircleThumbnail(PhotoPageActivity.this, resId);
                    StoragePicture.writePicture(PhotoPageActivity.this, bitmap, "picture" + System.currentTimeMillis());
                    return bitmap;
                } else
                    return null;
            }

            @Override
            public boolean refresh() {
                addList();
                return true;
            }
        });
    }

    private void addList(){
        int count = random(0,10);
        for(int i = 0; i < count; i++){
            int resId = random(0,9);
            if(resId == 0){
                photoList.add(R.mipmap.img_num_0);
            }else if(resId == 1){
                photoList.add(R.mipmap.img_num_1);
            }else if(resId == 2){
                photoList.add(R.mipmap.img_num_2);
            }else if(resId == 3){
                photoList.add(R.mipmap.img_num_3);
            }else if(resId == 4){
                photoList.add(R.mipmap.img_num_4);
            }else if(resId == 5){
                photoList.add(R.mipmap.img_num_5);
            }else if(resId == 6){
                photoList.add(R.mipmap.img_num_6);
            }else if(resId == 7){
                photoList.add(R.mipmap.img_num_7);
            }else if(resId == 8){
                photoList.add(R.mipmap.img_num_8);
            }else if(resId == 9){
                photoList.add(R.mipmap.img_num_9);
            }
        }
    }

    private int random(int min, int max){
        Random random = new Random();
        return random.nextInt(max) % (max - min + 1) + min;
    }

}
