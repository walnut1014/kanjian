package name.walnut.kanjian.app.ui.upload;


import android.app.Activity;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.hardware.Camera;
import android.net.Uri;
import android.opengl.GLSurfaceView;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

import name.walnut.kanjian.app.R;
import name.walnut.kanjian.app.ui.upload.GPUimageutil.GPUImage;
import name.walnut.kanjian.app.ui.upload.GPUimageutil.GPUImageFilter;
import name.walnut.kanjian.app.ui.upload.GPUimageutil.GPUImageGaussianBlurFilter;
import name.walnut.kanjian.app.ui.upload.camera.ButtonAnimation;
import name.walnut.kanjian.app.ui.upload.camera.CameraHelper;
import name.walnut.kanjian.app.ui.upload.camera.RoundProgressBar;

import static name.walnut.kanjian.app.ui.upload.camera.ButtonAnimation.*;


public class CameraActivity extends Activity{

    public static final int MEDIA_TYPE_IMAGE = 100;
    public static final int MEDIA_TYPE_VIDEO = 101;
    public static final int MSG_TAKE_PICTURE = 201;

    //Filter and Camera
    private GPUImage mGPUImage;
    private GPUImageFilter mGPUImageFilter;
    /**
     * sdlfjsldjlfs
     */
    private CameraHelper mCameraHelper;
    private CameraLoader mCamera;

    //UI
    private RoundProgressBar mRoundProgressBar;
    private ImageView mRingCapture,mWhiteFilter;
    private Button mBtnCapture;

    //ProgressBar
    Timer mPhotoTimer;
    private int mPictureSum = 10;
    private int mPictureCount = 0;

    @Override
    public void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera);

        mGPUImage = new GPUImage(this);
        mGPUImage.setGLSurfaceView((GLSurfaceView) findViewById(R.id.surfaceView));
        mCameraHelper = new CameraHelper(this);
        mCamera = new CameraLoader();
        //setGPUImageGaussianBlurFilter();

        //mRoundProgressBar = (RoundProgressBar) findViewById(R.id.round_progress_bar);
        //mBtnCapture = (Button)findViewById(R.id.btn_capture);
        //mWhiteFilter = (ImageView)findViewById(R.id.white_filter);
        //mRingCapture= (ImageView)findViewById(R.id.ring_capture);
        //setGPUImageNormalFilter();
        /*mBtnCapture.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch(event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        setGPUImageNormalFilter();
                        mRingCapture.setVisibility(View.GONE);
                        mWhiteFilter.setVisibility(View.GONE);
                        mBtnCapture.setBackgroundResource(R.drawable.btn_camera_press);
                        mPhotoTimer = new java.util.Timer(true);
                        mPhotoTimer.schedule(new TimerTask() {
                            @Override
                            public void run() {
                                // TODO Auto-generated method stub
                                Log.i("mPhotoTimer",String.valueOf(mPictureCount));
                                if(mPictureCount<=100) {
                                    mPictureCount += 2;
                                    mRoundProgressBar.setProgress(mPictureCount);
                                    mBtnCapture.setScaleX(1.1f);
                                    mBtnCapture.setScaleY(1.1f);
                                }}
                        }, 0,200);
                        break;
                    case MotionEvent.ACTION_UP:
                        mPhotoTimer.cancel();
                        break;
                }
                return false;
            }
        });/**/

        /**
         * author:ggh time:2015-08-19
         */
        RelativeLayout layout = (RelativeLayout) findViewById(R.id.layout);
        layout.setOnTouchListener(new ButtonAnimation(this,layout));
    }


    @Override
    protected void onResume() {
        super.onResume();
        mCamera.onResume();
    }

    @Override
    protected void onPause() {
        mCamera.onPause();
        super.onPause();
    }

    /**
     * author:ggh time:2015-08-20
     */
    public void moreClick(View view){
        view.setOnTouchListener(new ButtonAnimation(this, view, new OnClickActionUpListener() {
            @Override
            public void onActionUp() {
                Log.d("CameraActivity","action up");
            }
        }));
    }

    /**
     * author:ggh time:2015-08-20
     */
    public void photoClick(View view){
        view.setOnTouchListener(new ButtonAnimation(this, view, new OnClickActionUpListener() {
            @Override
            public void onActionUp() {
                Log.d("CameraActivity","action up");
            }
        }));
    }

    /**
     * author:ggh time:2015-08-20
     */
    public void messageClick(View view){
        view.setOnTouchListener(new ButtonAnimation(this, view, new OnClickActionUpListener() {
            @Override
            public void onActionUp() {
                Log.d("CameraActivity","action up");
            }
        }));
    }

    /**
     * 高斯模糊滤镜
     */
    public void setGPUImageGaussianBlurFilter() {
        //高斯模糊滤镜
        mGPUImageFilter = new GPUImageGaussianBlurFilter(20.0f);

        //快速均值模糊滤镜
        //mGPUImageFilter = new GPUImageBoxBlurFilter(5.0f);
        mGPUImage.setFilter(mGPUImageFilter);
    }

    /**
     * 正常滤镜
     */
    public void setGPUImageNormalFilter() {
        //正常滤镜
        mGPUImageFilter = new GPUImageFilter();
        mGPUImage.setFilter(mGPUImageFilter);
    }

    private Handler mHandler = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case CameraActivity.MSG_TAKE_PICTURE:
                    mPictureCount+=10;
                    mRoundProgressBar.setProgress(mPictureCount/mPictureCount);
                    break;
            }
        }

    };


//    public void onClick(final View v) {
//        switch (v.getId()) {
//            case R.id.btn_capture:
//                new Thread(new Runnable() {
//
//                    @Override
//                    public void run() {
//                        int progress = 0;
//
//                        while (progress <= 100) {
//                            progress += 3;
//
//                            mRoundProgressBar.setProgress(progress);
//
//                            try {
//                                Thread.sleep(100);
//                            } catch (InterruptedException e) {
//                                e.printStackTrace();
//                            }
//                        }
//
//                    }
//                }).start();
//                if (mCamera.mCameraInstance.getParameters().getFocusMode().equals(Camera.Parameters.FOCUS_MODE_CONTINUOUS_PICTURE)) {
//                    takePicture();
//                } else {
//                    mCamera.mCameraInstance.autoFocus(new Camera.AutoFocusCallback() {
//
//                        @Override
//                        public void onAutoFocus(final boolean success, final Camera camera) {
//                            takePicture();
//                        }
//                    });
//                }
//                break;
//        }
//    }

    private void takePicture() {
        // TODO get a size that is about the size of the screen
        Camera.Parameters params = mCamera.mCameraInstance.getParameters();
        params.setRotation(90);
        mCamera.mCameraInstance.setParameters(params);
        for (Camera.Size size : params.getSupportedPictureSizes()) {
            Log.i("ASDF", "Supported: " + size.width + "x" + size.height);
        }
        mCamera.mCameraInstance.takePicture(null, null,
                new Camera.PictureCallback() {

                    @Override
                    public void onPictureTaken(byte[] data, final Camera camera) {

                        final File pictureFile = getOutputMediaFile(MEDIA_TYPE_IMAGE);
                        if (pictureFile == null) {
                            Log.d("ASDF","Error creating media file, check storage permissions");
                            return;
                        }

                        try {
                            FileOutputStream fos = new FileOutputStream(pictureFile);
                            fos.write(data);
                            fos.close();
                        } catch (FileNotFoundException e) {
                            Log.d("ASDF", "File not found: " + e.getMessage());
                        } catch (IOException e) {
                            Log.d("ASDF", "Error accessing file: " + e.getMessage());
                        }
                        data = null;
                        Bitmap bitmap = BitmapFactory.decodeFile(pictureFile.getAbsolutePath());
                        // mGPUImage.setImage(bitmap);
                        final GLSurfaceView view = (GLSurfaceView) findViewById(R.id.surfaceView);
                        view.setRenderMode(GLSurfaceView.RENDERMODE_WHEN_DIRTY);
                        mGPUImage.saveToPictures(bitmap, "GPUImage",
                                System.currentTimeMillis() + ".jpg",
                                new GPUImage.OnPictureSavedListener() {

                                    @Override
                                    public void onPictureSaved(final Uri uri) {
                                        pictureFile.delete();
                                        camera.startPreview();
                                        view.setRenderMode(GLSurfaceView.RENDERMODE_CONTINUOUSLY);
                                    }
                                });
                    }
                });
    }

    private static File getOutputMediaFile(final int type) {

        File mediaStorageDir = new File(Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_PICTURES), "MyCameraApp");
        if (!mediaStorageDir.exists()) {
            if (!mediaStorageDir.mkdirs()) {
                Log.d("MyCameraApp", "failed to create directory");
                return null;
            }
        }

        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        File mediaFile;
        if (type == MEDIA_TYPE_IMAGE) {
            mediaFile = new File(mediaStorageDir.getPath() + File.separator +
                    "IMG_" + timeStamp + ".jpg");
        } else if (type == MEDIA_TYPE_VIDEO) {
            mediaFile = new File(mediaStorageDir.getPath() + File.separator +
                    "VID_" + timeStamp + ".mp4");
        } else {
            return null;
        }

        return mediaFile;
    }


    private class CameraLoader {

        private int mCurrentCameraId = 0;
        private Camera mCameraInstance;

        public void onResume() {
            setUpCamera(mCurrentCameraId);
        }

        public void onPause() {
            releaseCamera();
        }

        public void switchCamera() {
            releaseCamera();
            mCurrentCameraId = (mCurrentCameraId + 1) % mCameraHelper.getNumberOfCameras();
            setUpCamera(mCurrentCameraId);
        }

        private void setUpCamera(final int id) {
            mCameraInstance = getCameraInstance(id);
            Camera.Parameters parameters = mCameraInstance.getParameters();
            // TODO adjust by getting supportedPreviewSizes and then choosing
            // the best one for screen size (best fill screen)
            if (parameters.getSupportedFocusModes().contains(
                    Camera.Parameters.FOCUS_MODE_CONTINUOUS_PICTURE)) {
                parameters.setFocusMode(Camera.Parameters.FOCUS_MODE_CONTINUOUS_PICTURE);
            }
            mCameraInstance.setParameters(parameters);

            int orientation = mCameraHelper.getCameraDisplayOrientation(
                    CameraActivity.this, mCurrentCameraId);
            CameraHelper.CameraInfo2 cameraInfo = new CameraHelper.CameraInfo2();
            mCameraHelper.getCameraInfo(mCurrentCameraId, cameraInfo);
            boolean flipHorizontal = cameraInfo.facing == Camera.CameraInfo.CAMERA_FACING_FRONT;
            mGPUImage.setUpCamera(mCameraInstance, orientation, flipHorizontal, false);
        }

        private Camera getCameraInstance(final int id) {
            Camera c = null;
            try {
                c = mCameraHelper.openCamera(id);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return c;
        }

        private void releaseCamera() {
            mCameraInstance.setPreviewCallback(null);
            mCameraInstance.release();
            mCameraInstance = null;
        }
    }
}
