package name.walnut.kanjian.app.newui.upload;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import name.walnut.kanjian.app.R;
import name.walnut.kanjian.app.utils.TimeUtils;

/**
 * Created by linxunjian on 15/10/5.
 */
public class UploadActivity extends Activity {


    private SharedPreferences mSharedPreferences;
    private long nowDate = 0;
    public void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_upload);

        ImageView IvUploadPicture = (ImageView)findViewById(R.id.iv_upload_picture);
        EditText etUploadDescribe = (EditText)findViewById(R.id.et_upload_describe);
        ImageView mBtnBack = (ImageView)findViewById(R.id.btn_back);
        ImageView mBtnUpload = (ImageView)findViewById(R.id.btn_upload);
        TextView mTvSeclectTime = (TextView)findViewById(R.id.tv_select_time_time);

        mSharedPreferences = this.getSharedPreferences("hzw", MODE_PRIVATE);
        final long lastUpdateDate= mSharedPreferences.getLong("last_update_date", 0);
        nowDate = System.currentTimeMillis();

        mTvSeclectTime.setText(TimeUtils.getTimeyMdHms(lastUpdateDate));
        mBtnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                UploadActivity.this.finish();
            }
        });
        mBtnUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                upLoadAlertDialog();

            }
        });

        if((nowDate-lastUpdateDate)>(86400000))//24*60*60*1000一天之内,第一次选定
        {
            final Bitmap uploadPicture = getRotationBitmap(getIntent().getByteArrayExtra("pictureBtye"));
            IvUploadPicture.setImageBitmap(uploadPicture);
            new Thread(new Runnable() {
                @Override
                public void run() {
                    writePicture(UploadActivity.this, uploadPicture, getLastPictureFileName());
                    savePicture(uploadPicture, getFileNmae());
                    mSharedPreferences.edit().putLong("last_update_date",nowDate).commit();
                }
            }).start();
        }else//第二次选定
        {
            Bitmap uploadPicture = readPicture(UploadActivity.this,getLastPictureFileName());
            IvUploadPicture.setImageBitmap(uploadPicture);
            ImageView mBtnTime = (ImageView)findViewById(R.id.btn_time);
            mBtnTime.setVisibility(View.VISIBLE);
            mBtnTime.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    showNextSelectTimePop(findViewById(R.id.upload_layout), TimeUtils.getTimeHms(144000000 + lastUpdateDate - nowDate)+"  后可重新选图");//加上两天-8小时的时间.
                }
            });
        }
    }


    private void upLoadAlertDialog()
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(UploadActivity.this);
        builder.setMessage("上传的照片要24小时后才能删除哦");
        builder.setPositiveButton("确认上传", new DialogInterface.OnClickListener() {

            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                Toast.makeText(UploadActivity.this, "上传照片", Toast.LENGTH_SHORT).show();
            }
        });
        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                }
        });
        builder.create().show();
    }


    private void showNextSelectTimePop(View view,String toastText) {

        // 一个自定义的布局，作为显示的内容
        View contentView = LayoutInflater.from(UploadActivity.this).inflate(R.layout.popup_next_select_time, null);
        TextView tvNextSelectTime = (TextView) contentView.findViewById(R.id.tv_next_select_time);
        tvNextSelectTime.setText(toastText);
        final PopupWindow nextTimePop = new PopupWindow(contentView, RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT, true);
        nextTimePop.setOutsideTouchable(true);
        nextTimePop.setBackgroundDrawable(new ColorDrawable(Color.argb(256, 256, 256, 256)));
        nextTimePop.showAtLocation(view, Gravity.TOP, 0, 240);
    }


    public Bitmap getRotationBitmap(byte[] pictureBtye)
    {
        Matrix matrix = new Matrix();
        matrix.reset();
        matrix.setRotate(90);
        Bitmap bm = BitmapFactory.decodeByteArray(pictureBtye, 0, pictureBtye.length);
        bm = Bitmap.createBitmap(bm,0,0, bm.getWidth(), bm.getHeight(),matrix, true);
        return bm;
    }

    public static void savePicture(Bitmap bm,String fileName)
    {
        if (fileName == null) return;
        // 创建一个位于SD卡上的文件
        File file = new File(fileName);
        FileOutputStream outStream = null;
        try {
            outStream = new FileOutputStream(file);
            bm.compress(Bitmap.CompressFormat.JPEG, 100, outStream);
            outStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public static void writePicture(Context context, Bitmap bitmap,String fileName)
    {
        try{
            OutputStream outputStream =context.openFileOutput(fileName, Context.MODE_PRIVATE);
            bitmap.compress(Bitmap.CompressFormat.PNG,100,outputStream);
            outputStream.close();
        }catch (FileNotFoundException e)
        {
            e.printStackTrace();
        }catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    public static Bitmap readPicture(Context context,String fileName)
    {
        try{
            InputStream inputStream =context.openFileInput(fileName);
            return BitmapFactory.decodeStream(inputStream);
        }catch (FileNotFoundException e){
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 返回摄取照片的文件名
     *
     * @return 文件名
     */
    private String getFileNmae() {
        String fileName=null;
        if (!Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            Toast.makeText(this, "监测到你的手机没有插入SD卡，请插入SD卡后再试", Toast.LENGTH_LONG).show();
            return null;
        }else{
            fileName = Environment.getExternalStorageDirectory().getAbsolutePath();
        }
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss", Locale.getDefault());
        Log.d("CameraActivity", fileName);
        if(isFolderExists(fileName+"/kanjian/image/")) {
            fileName = fileName + "/kanjian/image/" + sdf.format(new Date()) + ".jpg";
        }
        return fileName;
    }

    private String getLastPictureFileName()
    {
        return "last_picture";
    }

    boolean isFolderExists(String strFolder)
    {
        File file = new File(strFolder);

        if (!file.exists())
        {
            if (file.mkdirs())
            {
                return true;
            }
            else return false;
        }
        return true;
    }

}
