package name.walnut.kanjian.app.ui.upload;

import android.annotation.TargetApi;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.ImageFormat;
import android.graphics.Rect;
import android.graphics.YuvImage;
import android.graphics.drawable.ColorDrawable;
import android.hardware.Camera;
import android.os.Build;
import android.os.Bundle;
import android.renderscript.Allocation;
import android.renderscript.Element;
import android.renderscript.RenderScript;
import android.renderscript.ScriptIntrinsicBlur;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import name.walnut.kanjian.app.R;
import name.walnut.kanjian.app.support.ActionBarFragment;

/**
 * Created by linxunjian on 15/8/8.
 */
public class CameraFragment extends ActionBarFragment implements SurfaceHolder.Callback{

    private Camera mCamera;// Camera对象
    private ImageView viewOfMarking;
    private Button mButton;// 右侧条框，点击出发保存图像（拍照）的事件
    private SurfaceView mSurfaceView;// 显示图像的surfaceView
    private SurfaceHolder mSurfaceHolder;// SurfaceView的控制器
    private View viewOfCamera;
    private boolean isPreview = false;
    //private AutoFocusCallback mAutoFocusCallback = new Camera.AutoFocusCallback();// AutoFocusCallback自动对焦的回调对象

    public static CameraFragment newInstance() {
        return new CameraFragment();
    }

    @SuppressWarnings("ResourceType")
    @Override

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        viewOfCamera = inflater.inflate(R.layout.activity_camera, null);
        initUIAndListener(viewOfCamera);
        return viewOfCamera;
    }

    //可以通过TextureView或者SurfaceView
    private void initUIAndListener(View v) {
        //mSurfaceView = (SurfaceView) v.findViewById(R.id.surface_camera);
        //viewOfMarking = (ImageView)v.findViewById(R.id.view_marking);
        mSurfaceView.setAlpha(1f);
        viewOfMarking.setImageDrawable(new ColorDrawable(0x00ffffff));
        mSurfaceHolder = mSurfaceView.getHolder();
        mSurfaceHolder.addCallback(this);
    }

    protected String getTitle() {
        return "拍照";
    }


    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        try {
            mCamera = null;
            try {
                if(Camera.getNumberOfCameras()==2){
                    mCamera = Camera.open(1);
                }else {
                    mCamera = Camera.open(0);
                }
                mCamera.setDisplayOrientation(90);
            } catch (Exception e) {
                Log.e("CamergFragment", "摄像头被占用");
            }
            if (mCamera == null) {
                Log.e("CamergFragment", "摄像机为空");
                System.exit(0);
            }
            mCamera.setPreviewDisplay(holder);//设置显示面板控制器
            priviewCallBack pre = new priviewCallBack();//建立预览回调对象
            mCamera.setPreviewCallback(pre); //设置预览回调对象
            mCamera.startPreview();//开始预览，这步操作很重要
        } catch (IOException exception) {
            mCamera.release();
            mCamera = null;
        }
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
        //initCamera();
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        stopCamera();
    }


    private void initCamera() {
        if (mCamera != null) {
            try {
                Camera.Parameters parameters = mCamera.getParameters();
                parameters.setPictureFormat(ImageFormat.JPEG);
                parameters.set("jpeg-quality", 100);
                parameters.setPictureSize(viewOfCamera.getWidth(), viewOfCamera.getHeight());
                mCamera.setParameters(parameters);
                /* 打开预览画面 */
                mCamera.startPreview();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private void stopCamera() {
        if (mCamera != null) {
                try {
                    /* 停止预览 */
                    mCamera.stopPreview();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            mCamera.release();
            mCamera = null;

        }
    }


    @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
    public Bitmap blurBitmap(Bitmap bitmap){
        Bitmap outBitmap = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getHeight(), Bitmap.Config.ARGB_8888);
        RenderScript rs = RenderScript.create(viewOfCamera.getContext().getApplicationContext());
        ScriptIntrinsicBlur blurScript = ScriptIntrinsicBlur.create(rs, Element.U8_4(rs));
        Allocation allIn = Allocation.createFromBitmap(rs, bitmap);
        Allocation allOut = Allocation.createFromBitmap(rs, outBitmap);
        blurScript.setRadius(25.f);
        blurScript.setInput(allIn);
        blurScript.forEach(allOut);
        allOut.copyTo(outBitmap);
        bitmap.recycle();
        rs.destroy();
        return outBitmap;
    }

    class priviewCallBack implements Camera.PreviewCallback {
        @Override
        public void onPreviewFrame(byte[] data, Camera camera) {
            decodeToBitMap(data, camera);
        }
    }

    public void decodeToBitMap(byte[] data, Camera _camera) {
        Camera.Size size = mCamera.getParameters().getPreviewSize();
        try {
            YuvImage image = new YuvImage(data, ImageFormat.NV21, size.width, size.height, null);
            if (image != null) {
                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                image.compressToJpeg(new Rect(0, 0, size.width, size.height), 100, stream);
                Bitmap bmp = blurBitmap(BitmapFactory.decodeByteArray(stream.toByteArray(), 0, stream.size()));
                //saveBitmap(bmp);

                stream.close();
            }
        } catch (Exception ex) {
            Log.e("Sys", "Error:" + ex.getMessage());
        }
    }

    public void saveBitmap(Bitmap bitmap) {
        int n = 0;
        File f = new File("/storage/sdcard0/Pictures/", "linxunjian"+n+++".png");
        if (f.exists()) {
            f.delete();
        }
        try {
            FileOutputStream out = new FileOutputStream(f);
            bitmap.compress(Bitmap.CompressFormat.PNG, 90, out);
            out.flush();
            out.close();
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }

}