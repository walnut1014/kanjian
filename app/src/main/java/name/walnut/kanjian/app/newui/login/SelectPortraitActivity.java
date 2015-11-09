package name.walnut.kanjian.app.newui.login;

import android.app.Activity;
import android.graphics.ImageFormat;
import android.hardware.Camera;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.WindowManager;
import android.widget.Button;

import name.walnut.kanjian.app.R;

/**
 * Created by linxunjian on 15/10/31.
 */
public class SelectPortraitActivity extends Activity {


    private Button mBtnTakePhoto,mBtnSelectFromLocal,mBtnBack;
    private Camera mCamera;
    private SurfaceView sView;
    private SurfaceHolder surfaceHolder;
    private int screenWidth,PreviewSize;
    boolean isPreview = false;
    private byte[] pictureBtye;

    public void onCreate(final Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_portrait);

        initUI();

        surfaceHolder = sView.getHolder();
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
                        isPreview=false;
                    }
                    mCamera.release();
                    mCamera = null;
                }
            }
        });

    }

    private void initUI()
    {
        WindowManager wm = getWindowManager();
        Display display = wm.getDefaultDisplay();
        DisplayMetrics metrics = new DisplayMetrics();
        display.getMetrics(metrics);
        screenWidth = metrics.widthPixels;
        PreviewSize = screenWidth/6*5;

        sView = (SurfaceView) findViewById(R.id.sf_photo_portrait);
        sView.getLayoutParams().width = PreviewSize;
        sView.getLayoutParams().height = PreviewSize;


    }

    private void initCamera() {
        if (!isPreview) {
            if(Camera.getNumberOfCameras()==2){
                mCamera = Camera.open(1);
            }else {
                mCamera = Camera.open(0);
            }
            mCamera.setDisplayOrientation(90);
        }
        if (mCamera != null && !isPreview) {
            try {
                Camera.Parameters parameters = mCamera.getParameters();
                parameters.setPreviewSize(PreviewSize, PreviewSize);
                parameters.setPreviewFpsRange(4, 10);
                parameters.setPictureFormat(ImageFormat.JPEG);
                parameters.set("jpeg-quality", 100);
                parameters.setPictureSize(PreviewSize, PreviewSize);
                mCamera.setPreviewDisplay(surfaceHolder);
                mCamera.setParameters(parameters);
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
    public void capture() {
        if (mCamera != null) {
            mCamera.autoFocus(autoFocusCallback);  //④
        }
    }

    Camera.AutoFocusCallback autoFocusCallback = new Camera.AutoFocusCallback() {
        // 当自动对焦时激发该方法
        @Override
        public void onAutoFocus(boolean success, Camera camera) {
            if (success) {

                camera.takePicture(new Camera.ShutterCallback() {
                    public void onShutter() {
                        // 按下快门瞬间会执行此处代码
                    }
                }, new Camera.PictureCallback() {
                    public void onPictureTaken(byte[] data, Camera c) {
                        // 此处代码可以决定是否需要保存原始照片信息
                    }
                }, myJpegCallback);  //⑤
            }
        }
    };

    Camera.PictureCallback myJpegCallback = new Camera.PictureCallback() {
        @Override
        public void onPictureTaken(byte[] data, Camera camera) {
            // 根据拍照所得的数据创建位图
            pictureBtye = data;
        }
    };



}
