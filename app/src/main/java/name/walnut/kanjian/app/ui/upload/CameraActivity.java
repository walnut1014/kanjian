package name.walnut.kanjian.app.ui.upload;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.ImageFormat;
import android.hardware.Camera;
import android.hardware.Camera.AutoFocusCallback;
import android.hardware.Camera.PictureCallback;
import android.hardware.Camera.ShutterCallback;
import android.os.Bundle;
import android.os.Environment;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.RelativeLayout;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import name.walnut.kanjian.app.R;

import static name.walnut.kanjian.app.ui.upload.ButtonAnimation.OnClickActionUpListener;


public class CameraActivity extends Activity {

    public static final int MEDIA_TYPE_IMAGE = 100;
    public static final int MEDIA_TYPE_VIDEO = 101;
    public static final int MSG_TAKE_PICTURE = 201;
    private Camera mCamera;
    private SurfaceView sView;
    private SurfaceHolder surfaceHolder;
    private int screenWidth, screenHeight;
    boolean isPreview = false;


    public void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_camera);
        WindowManager wm = getWindowManager();
        Display display = wm.getDefaultDisplay();
        DisplayMetrics metrics = new DisplayMetrics();
        display.getMetrics(metrics);
        screenWidth = metrics.widthPixels;
        screenHeight = metrics.heightPixels;
        // 获取界面中SurfaceView组件
        sView = (SurfaceView) findViewById(R.id.surfaceView);
        surfaceHolder = sView.getHolder();
        // 为surfaceHolder添加一个回调监听器
        surfaceHolder.addCallback(new SurfaceHolder.Callback() {
            public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
            }
            @Override
            public void surfaceCreated(SurfaceHolder holder) {
                initCamera();
            }
            @Override
            public void surfaceDestroyed(SurfaceHolder holder) {
                // 如果camera不为null ,释放摄像头
                if (mCamera != null) {
                    if (isPreview) {
                        mCamera.stopPreview();
                    }
                    mCamera.release();
                    mCamera = null;
                }
            }
        });
        RelativeLayout layout = (RelativeLayout) findViewById(R.id.pb_take_photo_layout);
        layout.setOnTouchListener(new ButtonAnimation(this, layout));
    }

    private void initCamera() {
        if (!isPreview) {
            mCamera = Camera.open(0);
            mCamera.setDisplayOrientation(90);
        }
        if (mCamera != null && !isPreview) {
            try {
                Camera.Parameters parameters = mCamera.getParameters();
                parameters.setPreviewSize(screenWidth, screenHeight);
                parameters.setPreviewFpsRange(4, 10);
                parameters.setPictureFormat(ImageFormat.JPEG);
                parameters.set("jpeg-quality", 85);
                parameters.setPictureSize(screenWidth, screenHeight);
                mCamera.setPreviewDisplay(surfaceHolder);
                mCamera.startPreview();
            } catch (Exception e) {
                e.printStackTrace();
            }
            isPreview = true;
        }
    }

    /**
     * 此方法在布局文件中调用
     */
    public void capture(View source) {
        if (mCamera != null) {
            mCamera.autoFocus(autoFocusCallback);  //④
        }
    }

AutoFocusCallback autoFocusCallback = new AutoFocusCallback() {
    // 当自动对焦时激发该方法
    @Override
    public void onAutoFocus(boolean success, Camera camera) {
        if (success) {
            // takePicture()方法需要传入3个监听器参数
            // 第1个监听器：当用户按下快门时激发该监听器
            // 第2个监听器：当相机获取原始照片时激发该监听器
            // 第3个监听器：当相机获取JPG照片时激发该监听器
            camera.takePicture(new ShutterCallback() {
                public void onShutter() {
                    // 按下快门瞬间会执行此处代码
                }
            }, new PictureCallback() {
                public void onPictureTaken(byte[] data, Camera c) {
                    // 此处代码可以决定是否需要保存原始照片信息
                }
            }, myJpegCallback);  //⑤
        }
    }
};

PictureCallback myJpegCallback = new PictureCallback() {
    @Override
    public void onPictureTaken(byte[] data, Camera camera) {
        // 根据拍照所得的数据创建位图
        final Bitmap bm = BitmapFactory.decodeByteArray(data, 0,
                data.length);
        String fileName = getFileNmae();
        if (fileName == null) return;
        // 创建一个位于SD卡上的文件
        File file = new File(fileName);
        FileOutputStream outStream = null;
        try {
            outStream = new FileOutputStream(file);
            bm.compress(Bitmap.CompressFormat.JPEG, 100, outStream);
            outStream.close();
            Toast.makeText(CameraActivity.this, "照片以保存到" + fileName,
                    Toast.LENGTH_SHORT).show();
        } catch (IOException e) {
            e.printStackTrace();
        }

        // 重新浏览
        camera.stopPreview();
        camera.startPreview();
        isPreview = true;
    }
};

    /**
     * 返回摄取照片的文件名
     *
     * @return 文件名
     */
    protected String getFileNmae() {
        String fileName;
        if (!Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            Toast.makeText(this, "监测到你的手机没有插入SD卡，请插入SD卡后再试",Toast.LENGTH_LONG).show();
            return null;
        }
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH.mm.ss", Locale.getDefault());
        fileName = Environment.getExternalStorageDirectory() + File.separator+ sdf.format(new Date()) + ".JPG";
        return fileName;
    }



    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    /**
     * author:ggh time:2015-08-20
     */
    public void moreClick(View view) {
        view.setOnTouchListener(new ButtonAnimation(this, view, new OnClickActionUpListener() {
            @Override
            public void onActionUp() {
                Log.d("CameraActivity", "action up");
            }
        }));
    }

    /**
     * author:ggh time:2015-08-20
     */
    public void photoClick(View view) {
        view.setOnTouchListener(new ButtonAnimation(this, view, new OnClickActionUpListener() {
            @Override
            public void onActionUp() {
                Log.d("CameraActivity", "action up");
            }
        }));
    }

    /**
     * author:ggh time:2015-08-20
     */
    public void messageClick(View view) {
        view.setOnTouchListener(new ButtonAnimation(this, view, new OnClickActionUpListener() {
            public void onActionUp() {
                Log.d("CameraActivity", "action up");
            }
        }));
    }


}
