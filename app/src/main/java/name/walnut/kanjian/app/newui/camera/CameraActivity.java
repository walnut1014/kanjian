package name.walnut.kanjian.app.newui.camera;

import android.app.Activity;
import android.content.Intent;
import android.graphics.ImageFormat;
import android.hardware.Camera;
import android.hardware.Camera.AutoFocusCallback;
import android.hardware.Camera.PictureCallback;
import android.hardware.Camera.ShutterCallback;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import name.walnut.kanjian.app.R;
import name.walnut.kanjian.app.newui.main.PhotoDetailsActivity;
import name.walnut.kanjian.app.newui.photopage.PhotoPageActivity;
import name.walnut.kanjian.app.newui.upload.UploadActivity;

import static name.walnut.kanjian.app.newui.camera.ButtonAnimation.OnClickActionUpListener;


public class CameraActivity extends Activity {

    public static final int MEDIA_TYPE_IMAGE = 100;
    public static final int MEDIA_TYPE_VIDEO = 101;
    public static final int MSG_TAKE_PICTURE = 201;
    private Camera mCamera;
    private SurfaceView sView;
    private SurfaceHolder surfaceHolder;
    private int screenWidth, screenHeight;
    boolean isPreview = false;
    private byte[] pictureBtye;


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

        sView = (SurfaceView) findViewById(R.id.surfaceView);
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
        RelativeLayout layout = (RelativeLayout) findViewById(R.id.pb_take_photo_layout);

        ImageView BtnCameraPersonal = (ImageView) findViewById(R.id.camera_button_personal);
        ImageView BtnCameraPhoto = (ImageView) findViewById(R.id.camera_button_photo);
        ImageView BtnCameraMessage = (ImageView) findViewById(R.id.camera_button_message);

        BtnCameraPersonal.setOnTouchListener(new ButtonAnimation(this, BtnCameraPersonal, new OnClickActionUpListener() {
            @Override
            public void onActionUp() {
                Log.d("CameraActivity", "action up");
                Intent _intent_more =new Intent(CameraActivity.this, PhotoDetailsActivity.class);
                startActivity(_intent_more);
            }
        }));
        BtnCameraPhoto.setOnTouchListener(new ButtonAnimation(this, BtnCameraPhoto, new OnClickActionUpListener() {
            @Override
            public void onActionUp() {
                Log.d("CameraActivity", "action up");
                startActivity(new Intent(CameraActivity.this, PhotoPageActivity.class));
            }
        }));
        BtnCameraMessage.setOnTouchListener(new ButtonAnimation(this, BtnCameraMessage, new OnClickActionUpListener() {
            @Override
            public void onActionUp() {
                Log.d("CameraActivity", "action up");
            }
        }));

        layout.setOnTouchListener(new ButtonAnimation(this, layout, new OnClickActionUpListener() {
            @Override
            public void onActionUp() {
                Log.d("CameraActivity", "success");
                capture();

            }
        }));
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
                parameters.setPreviewSize(screenHeight, screenWidth);
                parameters.setPreviewFpsRange(4, 10);
                parameters.setPictureFormat(ImageFormat.JPEG);
                parameters.set("jpeg-quality", 85);
                parameters.setPictureSize(screenHeight, screenWidth);
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
            pictureBtye = data;
            Intent _intent_upload = new Intent(CameraActivity.this, UploadActivity.class);
            _intent_upload.putExtra("pictureBtye",pictureBtye);
            startActivity(_intent_upload);
        }
    };


    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }


}
